/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.form.SysLoginForm;
import io.renren.modules.sys.service.SysCaptchaService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.service.SysUserTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	private static final String FAIL_COUNTER = "sys_user_login_fail_counter";
	private static final String FAIL_LOCK = "sys_user_login_fail_lock";

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	public Map<String, Object> login(@RequestBody SysLoginForm form) throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return R.error("验证码不正确");
		}

//		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//
//		ops.set("hi", "world_" + UUID.randomUUID().toString());
//
//		String hi = ops.get("hi");
//		System.out.println("之前保存的数据是：" + hi);
//		return R.error("之前保存的数据是：" + hi);

		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());
		Long userId = user.getUserId();

		// 1.验证用户是否被登录锁定
		boolean lock = isLock(userId);
		if (lock) {
			String key = String.join(":", FAIL_LOCK, Long.toString(userId));
			// 获取过期时间
			long t = unlockTime(userId);
			long second = stringRedisTemplate.opsForValue().getOperations().getExpire(key, TimeUnit.SECONDS);
			if(t>0){
				long sec=second-t*60;
				if(sec==0){
					return R.error("登录验证失败次数过多，请" + t + "分钟后再试！");
				} else{
					return R.error("登录验证失败次数过多，请" + t + "分"+sec+"秒后再试！");
				}
			} else {
				return R.error("登录验证失败次数过多，请" + second + "秒后再试！");
			}
		}

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			setCheckFailCounter(userId);
			if(isLock(userId)){
				return R.error("账号或密码不正确！由于短期内您登录验证失败次数过多，请15分钟后再尝试登录！");
			}
			int remain = 5 - getLoginFailCounter(userId); // 得到剩余可尝试次数
			return R.error("账号或密码不正确，再错误输入" + remain + "次后将暂停登录15分钟");
		}
		// 登录成功 移除失败计数器
		deleteLoginFailCounter(userId);

//		//账号锁定
//		if(user.getStatus() == 0){
//			return R.error("账号已被锁定,请联系管理员");
//		}

		//生成token，并保存到数据库
		R r = sysUserTokenService.createToken(user.getUserId());
		return r;
	}

	/**
	 * 登录失败计数器
	 *
	 * @param userId
	 */
	public void setCheckFailCounter(Long userId) {
		String key = String.join(":", FAIL_COUNTER, Long.toString(userId));
		String value = stringRedisTemplate.opsForValue().get(key);
		// 如果是第一次输入错误
		if (value == null) {
			stringRedisTemplate.opsForValue().set(key, "1");
			stringRedisTemplate.expire(key, 300, TimeUnit.SECONDS);
		} else {
			// 非第一次
			//获取key的当前生存时间和对应的登录失败次数
			Long expiration = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
			Integer count = Integer.valueOf(value);
			if (expiration > 0) {
				// System.out.println("The TTL of the key is: " + expiration);
				stringRedisTemplate.opsForValue().set(key, Integer.toString(count + 1));
				stringRedisTemplate.expire(key, expiration, TimeUnit.SECONDS);
			}
			if ((count.intValue()+1) == 5) {
				// 失败达到五次 设置锁定缓存
				lock(userId);
			}
		}
	}

	/**
	 * 得到计数器的值
	 *
	 * @param userId
	 */
	public Integer getLoginFailCounter(Long userId) {
		String key = String.join(":", FAIL_COUNTER, Long.toString(userId));
		String value = stringRedisTemplate.opsForValue().get(key);
		if (value == null) return 0;
		else return Integer.valueOf(value);
	}

	/**
	 * 移除计数器
	 *
	 * @param userId
	 */
	public void deleteLoginFailCounter(Long userId) {
		stringRedisTemplate.delete(String.join(":", FAIL_COUNTER, Long.toString(userId)));
	}


	/**
	 * 失败达到一定一定次数 锁定15分钟
	 *
	 * @param userId
	 */
	public void lock(Long userId) {
		String key = String.join(":", FAIL_LOCK, Long.toString(userId));
		stringRedisTemplate.opsForValue().set(key, "lock", 15, TimeUnit.MINUTES);
	}

	/**
	 * 是否被登录锁定
	 *
	 * @param userId
	 * @return
	 */
	public boolean isLock(Long userId) {
		return stringRedisTemplate.hasKey(String.join(":", FAIL_LOCK, Long.toString(userId)));
	}

	/**
	 * 获取解锁的时间
	 *
	 * @param userId
	 * @return
	 */
	public long unlockTime(Long userId) {
		String key = String.join(":", FAIL_LOCK, Long.toString(userId));
		return stringRedisTemplate.opsForValue().getOperations().getExpire(key, TimeUnit.MINUTES);
	}


	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public R logout() {
		sysUserTokenService.logout(getUserId());
		return R.ok();
	}

}
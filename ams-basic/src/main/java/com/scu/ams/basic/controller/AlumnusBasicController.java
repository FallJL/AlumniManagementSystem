package com.scu.ams.basic.controller;


import java.text.ParseException;
import java.util.*;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.scu.ams.basic.vo.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import com.scu.common.valid.AddGroup;
import com.scu.common.valid.UpdateGroup;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.scu.common.exception.BizCodeEnum;
import com.scu.common.valid.AddGroup;
import com.scu.common.valid.UpdateGroup;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 校友的常用基本信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@RestController
@RequestMapping("basic/alumnusbasic")
public class AlumnusBasicController {
    @Autowired
    private AlumnusBasicService alumnusBasicService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private static final String FAIL_COUNTER = "basic_user_login_fail_counter";
    private static final String FAIL_LOCK = "basic_user_login_fail_lock";

    /**
     * 通过shiro安全框架进行登录，因为是登录，所以不要求必须是登录状态，不用写@RequiresRoles("alumnus")
     */
    @PostMapping("/login")
    public R login(@RequestBody AlumnusLoginVo vo){
        // 1.获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 2.封装请求数据到token，这里的vo的loginAccount（即学号aluId）就是username
        UsernamePasswordToken token = new UsernamePasswordToken(vo.getLoginAccount(), vo.getPassword());
        String aluId = token.getPrincipal().toString();
        // 3.验证用户是否被登录锁定
        boolean lock = isLock(aluId);
        if (lock) {
            String key = String.join(":", FAIL_LOCK, aluId);
            // 获取过期时间
            long t = unlockTime(aluId);
            long second = stringRedisTemplate.opsForValue().getOperations().getExpire(key, TimeUnit.SECONDS);
            if(t>0){
                long sec=second-t*60;
                if(sec==0){
                    return R.error("登录验证失败次数过多，请" + t + "分钟后再试！");
                }
                else{
                    return R.error("登录验证失败次数过多，请" + t + "分"+sec+"秒后再试！");
                }
            }
            else {
                return R.error("登录验证失败次数过多，请" + second + "秒后再试！");
            }
        }

        // 4.判断是否被禁用
        Integer aluStatus = alumnusBasicService.getStatusById(aluId);
        if (aluStatus == null) return R.error("该账户不存在");
        if (aluStatus == 0) return R.error("该账号被禁用，请联系管理员");

        // 5.调用login方法进行登录认证
        try {
            subject.login(token);
            // 上一步login时没抛出异常，说明登录成功

            // 登录成功 移除失败计数器
            deleteLoginFailCounter(aluId);
            return R.ok().put("aluId", aluId);
        } catch (AuthenticationException e) {
            // 登录失败，抛出异常
            // e.printStackTrace();
            setCheckFailCounter(aluId);
            if(isLock(aluId)){
                return R.error("账号或密码不正确！由于短期内您登录验证失败次数过多，请15分钟后再尝试登录！");
            }
            int remain = 5 - getLoginFailCounter(aluId); // 得到剩余可尝试次数
            return R.error("账号或密码不正确，再错误输入" + remain + "次后将暂停登录15分钟");
//            return R.error(BizCodeEnum.LOGINACCOUNT_PASSWORD_INVALID_EXCEPTION.getCode(),
//                    BizCodeEnum.LOGINACCOUNT_PASSWORD_INVALID_EXCEPTION.getMsg());
        }
    }

    /**
     * 登录失败计数器
     *
     * @param aluId
     */
    public void setCheckFailCounter(String aluId) {
        String key = String.join(":", FAIL_COUNTER, aluId);
        String value = stringRedisTemplate.opsForValue().get(key);
        // 如果是第一次输入错误
        if (value == null) {
            stringRedisTemplate.opsForValue().set(key, "1");
            stringRedisTemplate.expire(key, 300, TimeUnit.SECONDS);
        } else {
            // 非第一次
            // 获取key的当前生存时间和对应的登录失败次数
            Long expiration = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            Integer count = Integer.valueOf(value);
            if (expiration > 0) {
            // System.out.println("The TTL of the key is: " + expiration);
                stringRedisTemplate.opsForValue().set(key, Integer.toString(count + 1));
                stringRedisTemplate.expire(key, expiration, TimeUnit.SECONDS);
            }
            if ((count.intValue()+1) == 5) {
                // 失败达到五次 设置锁定缓存
                lock(aluId);
            }
        }
    }

    /**
     * 得到计数器的值
     *
     * @param aluId
     */
    public Integer getLoginFailCounter(String aluId) {
        String key = String.join(":", FAIL_COUNTER, aluId);
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value == null) return 0;
        else return Integer.valueOf(value);
    }

    /**
     * 移除计数器
     *
     * @param aluId
     */
    public void deleteLoginFailCounter(String aluId) {
        stringRedisTemplate.delete(String.join(":", FAIL_COUNTER, aluId));
    }


    /**
     * 失败达到一定一定次数 锁定15分钟
     *
     * @param aluId
     */
    public void lock(String aluId) {
        String key = String.join(":", FAIL_LOCK, aluId);
        stringRedisTemplate.opsForValue().set(key, "lock", 15, TimeUnit.MINUTES);
    }

    /**
     * 是否被登录锁定
     *
     * @param aluId
     * @return
     */
    public boolean isLock(String aluId) {
        return stringRedisTemplate.hasKey(String.join(":", FAIL_LOCK, aluId));
    }

    /**
     * 获取解锁的时间
     *
     * @param aluId
     * @return
     */
    public long unlockTime(String aluId) {
        String key = String.join(":", FAIL_LOCK, aluId);
        return stringRedisTemplate.opsForValue().getOperations().getExpire(key, TimeUnit.MINUTES);
    }

    /**
     * 通过shiro安全框架进行登出
     */
    @RequiresRoles("alumnus")
    @PostMapping("/logout")
    public R logout(){
        // 使用shiro提供的logout登出
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return R.ok("登出成功");
    }

    /**
     * 获取当前登录用户的信息
     */
    @RequiresRoles("alumnus")
    @GetMapping("/info")
    public R info(){
        // 获取学号
        String aluId = SecurityUtils.getSubject().getPrincipal().toString();
        // 根据学号返回信息
        AlumnusBasicEntity entity = alumnusBasicService.getByAluId(aluId);
        entity.setPassword(null); // 不能返回密码

        return R.ok().put("user", entity);
    }

    /**
     * 修改密码
     */
    @RequiresRoles("alumnus")
    @PostMapping("/update-password")
    public R updatePassword(@RequestBody UpdatePasswordVo updatePasswordVo){
        // 获取学号
        String aluId = SecurityUtils.getSubject().getPrincipal().toString();
        // 根据学号、vo修改密码
        return alumnusBasicService.updatePassword(aluId, updatePasswordVo);
    }

    /**
     * 登录认证验证 角色 的test demo
     */
    @RequiresRoles("alumnus")
    @GetMapping("/role-shiro-test")
    public R roleTest(){
        return R.ok("登录认证验证角色成功");
    }

    /**
     * 登录认证验证 权限 的test demo
     */
    @RequiresPermissions("alumnus:info")
    @GetMapping("/permission-shiro-test")
    public R permissionTest(){
        return R.ok("登录认证验证权限成功");
    }

//    /**
//     * 没有用shiro的登录
//     */
//    @PostMapping("/login")
//    public R login(@RequestBody AlumnusLoginVo vo){
//        AlumnusBasicEntity entity = alumnusBasicService.login(vo);
//
//        if(entity != null){ // 登录成功
//            return R.ok().put("user", entity);
//        } else { // 登录失败
//            return R.error(BizCodeEnum.LOGINACCOUNT_PASSWORD_INVALID_EXCEPTION.getCode(),
//                    BizCodeEnum.LOGINACCOUNT_PASSWORD_INVALID_EXCEPTION.getMsg());
//        }
//    }

//    /**
//     * 校友抽样（根据给定条件，随机抽样）
//     */
//    @RequestMapping("/list/random")
//    //@RequiresPermissions("basic:alumnusbasic:list")
//    public R listRandom(@RequestParam Map<String, Object> params){
//        PageUtils page = alumnusBasicService.listRandom(params);
//
//        return R.ok().put("page", page);
//    }

//    /**
//     * 上传校友的头像
//     *  把图片保存在本地文件夹中
//     */
//    @PostMapping("/uploadPortrait")
//    public R uploadPortrait(@RequestParam("file") MultipartFile file) throws IOException {
//        if (file.isEmpty()) {
//            return R.error("上传失败，请选择文件");
//        }
//
//        String url = alumnusBasicService.uploadPortrait(file);
//
//        return R.ok().put("url", url);
//    }
//
//    /**
//     * 删除头像
//     *  1）删除本地图片
//     *  2）将数据库中该校友的portrait_url属性置为null
//     */
//    @PostMapping("/deletePortrait")
//    public R deletePortrait(@RequestBody AlumnusBasicEntity alumnusBasic){
//        R r = alumnusBasicService.deletePortrait(alumnusBasic);
//
//        return r;
//    }
//
//    @PostMapping("/portraitImg")
//    public void portraitImg(@RequestBody AlumnusBasicEntity alumnusBasic, HttpServletResponse response) throws IOException {
//        System.out.println("调用img");
//        FileInputStream fis = alumnusBasicService.portraitImg(alumnusBasic);
//
//        byte[] bytes = new byte[fis.available()];
//        fis.read(bytes);
//        response.getOutputStream().write(bytes);
//        fis.close();
//    }

//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    //@RequiresPermissions("basic:alumnusbasic:save")
//    public R save(@Validated({AddGroup.class}) @RequestBody AlumnusBasicEntity alumnusBasic /*, BindingResult result */){
////		if(result.hasErrors()){
////            Map<String, String> map = new HashMap<>();
////            // FieldError
////            result.getFieldErrors().forEach((item) -> {
////                // 1.获取错误信息
////                String message = item.getDefaultMessage();
////                // 2.获取错误的属性名
////                String field = item.getField();
////                map.put(field, message);
////            });
////            return R.error(400, "提交的数据不合法").put("data", map);
////        } else {
////            alumnusBasicService.save(alumnusBasic);
////        }
//
//        alumnusBasicService.save(alumnusBasic);
//
//        return R.ok();
//    }

//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{id}")
//    //@RequiresPermissions("basic:alumnusbasic:info")
//    public R info(@PathVariable("id") Long id){
//        AlumnusBasicVo vo = alumnusBasicService.info(id);
//
//        return R.ok().put("alumnusBasic", vo);
//    }

//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    //@RequiresPermissions("basic:alumnusbasic:save")
//    public R save(@Validated({AddGroup.class}) @RequestBody AlumnusBasicEntity alumnusBasic /*, BindingResult result */){
////		if(result.hasErrors()){
////            Map<String, String> map = new HashMap<>();
////            // FieldError
////            result.getFieldErrors().forEach((item) -> {
////                // 1.获取错误信息
////                String message = item.getDefaultMessage();
////                // 2.获取错误的属性名
////                String field = item.getField();
////                map.put(field, message);
////            });
////            return R.error(400, "提交的数据不合法").put("data", map);
////        } else {
////            alumnusBasicService.save(alumnusBasic);
////        }
//
//        alumnusBasicService.save(alumnusBasic);
//
//        return R.ok();
//    }

//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    //@RequiresPermissions("basic:alumnusbasic:update")
//    public R update(@Validated({UpdateGroup.class}) @RequestBody AlumnusBasicEntity alumnusBasic){
//        alumnusBasicService.updateById(alumnusBasic);
//
//        return R.ok();
//    }

//    //导出
//    @RequestMapping ("/export")
//    public R export(@RequestBody AlumnusBasicEntity alumnusBasicEntity, HttpServletResponse response){
//        alumnusBasicService.export(alumnusBasicEntity,response);
//        return R.ok();
//    }
//
//    @RequestMapping ("/test")
//    public R test(){
//        PageUtils page = alumnusBasicService.test();
//        return R.ok().put("page",page);
//    }
}

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.jws.HandlerChain;
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

    /**
     * 通过shiro安全框架进行登录
     */
    @PostMapping("/login")
    public R login(@RequestBody AlumnusLoginVo vo){
        // 1.获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 2.封装请求数据到token，这里的vo的loginAccount（学号或手机号）就是username
        UsernamePasswordToken token = new UsernamePasswordToken(vo.getLoginAccount(), vo.getPassword());
        // 3.调用login方法进行登录认证
        try {
            subject.login(token);
            // 上一步login时没抛出异常，说明登录成功
            return R.ok().put("aluId", token.getPrincipal().toString());
        } catch (AuthenticationException e) {
            // 登录失败，抛出异常
            // e.printStackTrace();
            return R.error(BizCodeEnum.LOGINACCOUNT_PASSWORD_INVALID_EXCEPTION.getCode(),
                    BizCodeEnum.LOGINACCOUNT_PASSWORD_INVALID_EXCEPTION.getMsg());
        }
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

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:alumnusbasic:info")
    public R info(@PathVariable("id") Long id){
        AlumnusBasicVo vo = alumnusBasicService.info(id);

        return R.ok().put("alumnusBasic", vo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:alumnusbasic:save")
    public R save(@Validated({AddGroup.class}) @RequestBody AlumnusBasicEntity alumnusBasic /*, BindingResult result */){
//		if(result.hasErrors()){
//            Map<String, String> map = new HashMap<>();
//            // FieldError
//            result.getFieldErrors().forEach((item) -> {
//                // 1.获取错误信息
//                String message = item.getDefaultMessage();
//                // 2.获取错误的属性名
//                String field = item.getField();
//                map.put(field, message);
//            });
//            return R.error(400, "提交的数据不合法").put("data", map);
//        } else {
//            alumnusBasicService.save(alumnusBasic);
//        }

        alumnusBasicService.save(alumnusBasic);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:alumnusbasic:update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody AlumnusBasicEntity alumnusBasic){

        alumnusBasicService.updateById(alumnusBasic);
        return R.ok();
    }
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

//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    //@RequiresPermissions("basic:alumnusbasic:delete")
//    public R delete(@RequestBody Long[] ids){
//        alumnusBasicService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }
    /*
    * 多条件查询
    **/

    @RequestMapping("/alumniData")
    public R list(@RequestBody AlumusQueryVO alumusQueryVO) {
        PageUtils page = alumnusBasicService.queryPageWrapper(alumusQueryVO);
        return R.ok().put("page", page);
    }
    //导出
    @RequestMapping ("/export")
    public R export(@RequestBody AlumnusBasicEntity alumnusBasicEntity, HttpServletResponse response){
        alumnusBasicService.export(alumnusBasicEntity,response);
        return R.ok();
    }
    //导入数据库
    @RequestMapping ("/inport")
    public R inport(@RequestBody AlumnusBasicEntity alumnusBasicEntity){
        alumnusBasicService.inport(alumnusBasicEntity);
        return R.ok();
    }
    //企业性质统计图
    @RequestMapping ("/enterpriseChart")
    public R enterpriseChart(){
        List<EnterprisePropertyVO> enterprisePropertyVO = alumnusBasicService.enterpriseChart();
        return R.ok().put("enterprisePropertyVO",enterprisePropertyVO);
    }
    //民族统计图
    @RequestMapping ("/nationality")
    public R nationality(){
        List<NationalityVO> nationalityVO = alumnusBasicService.nationalityChart();
        return R.ok().put("nationalityVO",nationalityVO);
    }
    //专业统计图
    @RequestMapping ("/major")
    public R major(){
        List<MajorVO> majorVo = alumnusBasicService.majorChart();
        return R.ok().put("majorVo",majorVo);
    }
    //毕业时间统计图
    @RequestMapping ("/graduation")
    public R graduation(){
        List<GraduationVO> graduationVO = alumnusBasicService.graduationChart();
        return R.ok().put("graduationVO",graduationVO);
    }
    //籍贯统计图
    @RequestMapping ("/nativePlace")
    public R nativePlace(){
        List<NativePlaceVO> nativePlaceVO = alumnusBasicService.nativePlaceChart();
        return R.ok().put("nativePlaceVO",nativePlaceVO);
    }
    //阶段统计图
    @RequestMapping ("/degreeStage")
    public R degreeStage(){
        List<DegreeStageVO> degreeStageVO = alumnusBasicService.degreeStageChart();
        return R.ok().put("degreeStageVO",degreeStageVO);
    }
    //所在城市统计图
    @RequestMapping ("/city")
    public R city(){
        List<CityVO> cityVO = alumnusBasicService.cityChart();
        return R.ok().put("cityVO",cityVO);
    }
    @RequestMapping ("/test")
    public R test(){
        PageUtils page = alumnusBasicService.test();
        return R.ok().put("page",page);
    }
}

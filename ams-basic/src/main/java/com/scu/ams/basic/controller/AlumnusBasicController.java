package com.scu.ams.basic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import com.scu.ams.basic.vo.AlumnusBasicVo;
import com.scu.common.valid.AddGroup;
import com.scu.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

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
@RefreshScope
@RestController
@RequestMapping("basic/alumnusbasic")
public class AlumnusBasicController {
    @Autowired
    private AlumnusBasicService alumnusBasicService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:alumnusbasic:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = alumnusBasicService.queryPage(params);

        return R.ok().put("page", page);
    }

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

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:alumnusbasic:delete")
    public R delete(@RequestBody Long[] ids){
        alumnusBasicService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

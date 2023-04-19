package com.scu.ams.basic.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scu.ams.basic.entity.HomeImgEntity;
import com.scu.ams.basic.service.HomeImgService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;



/**
 * 首页图片的信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@RestController
@RequestMapping("basic/homeimg")
public class HomeImgController {
    @Autowired
    private HomeImgService homeImgService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:homeimg:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = homeImgService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:homeimg:info")
    public R info(@PathVariable("id") Long id){
		HomeImgEntity homeImg = homeImgService.getById(id);

        return R.ok().put("homeImg", homeImg);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:homeimg:save")
    public R save(@RequestBody HomeImgEntity homeImg){
		homeImgService.save(homeImg);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:homeimg:update")
    public R update(@RequestBody HomeImgEntity homeImg){
		homeImgService.updateById(homeImg);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:homeimg:delete")
    public R delete(@RequestBody Long[] ids){
		homeImgService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

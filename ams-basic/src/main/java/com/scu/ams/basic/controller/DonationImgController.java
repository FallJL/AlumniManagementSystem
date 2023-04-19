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

import com.scu.ams.basic.entity.DonationImgEntity;
import com.scu.ams.basic.service.DonationImgService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;



/**
 * 一个捐赠新闻对应多张图片
一张图片对应一个新闻
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@RestController
@RequestMapping("basic/donationimg")
public class DonationImgController {
    @Autowired
    private DonationImgService donationImgService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:donationimg:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = donationImgService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:donationimg:info")
    public R info(@PathVariable("id") Long id){
		DonationImgEntity donationImg = donationImgService.getById(id);

        return R.ok().put("donationImg", donationImg);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:donationimg:save")
    public R save(@RequestBody DonationImgEntity donationImg){
		donationImgService.save(donationImg);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:donationimg:update")
    public R update(@RequestBody DonationImgEntity donationImg){
		donationImgService.updateById(donationImg);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:donationimg:delete")
    public R delete(@RequestBody Long[] ids){
		donationImgService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

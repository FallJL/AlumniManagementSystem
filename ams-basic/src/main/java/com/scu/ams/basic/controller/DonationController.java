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

import com.scu.ams.basic.entity.DonationEntity;
import com.scu.ams.basic.service.DonationService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;



/**
 * 捐赠的新闻信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@RestController
@RequestMapping("basic/donation")
public class DonationController {
    @Autowired
    private DonationService donationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:donation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = donationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:donation:info")
    public R info(@PathVariable("id") Long id){
		DonationEntity donation = donationService.getById(id);

        return R.ok().put("donation", donation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:donation:save")
    public R save(@RequestBody DonationEntity donation){
		donationService.save(donation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:donation:update")
    public R update(@RequestBody DonationEntity donation){
		donationService.updateById(donation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:donation:delete")
    public R delete(@RequestBody Long[] ids){
		donationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

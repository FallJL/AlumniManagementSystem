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

import com.scu.ams.basic.entity.AdminLogEntity;
import com.scu.ams.basic.service.AdminLogService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;



/**
 * 
 *
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-06-30 16:09:06
 */
@RestController
@RequestMapping("basic/adminlog")
public class AdminLogController {
    @Autowired
    private AdminLogService adminLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:adminlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = adminLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:adminlog:info")
    public R info(@PathVariable("id") Long id){
		AdminLogEntity adminLog = adminLogService.getById(id);

        return R.ok().put("adminLog", adminLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:adminlog:save")
    public R save(@RequestBody AdminLogEntity adminLog){
		adminLogService.save(adminLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:adminlog:update")
    public R update(@RequestBody AdminLogEntity adminLog){
		adminLogService.updateById(adminLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:adminlog:delete")
    public R delete(@RequestBody Long[] ids){
		adminLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

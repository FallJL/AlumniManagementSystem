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

import com.scu.ams.basic.entity.AdminEntity;
import com.scu.ams.basic.service.AdminService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;



/**
 * 管理员信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@RestController
@RequestMapping("basic/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:admin:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = adminService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:admin:info")
    public R info(@PathVariable("id") Long id){
		AdminEntity admin = adminService.getById(id);

        return R.ok().put("admin", admin);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:admin:save")
    public R save(@RequestBody AdminEntity admin){
		adminService.save(admin);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:admin:update")
    public R update(@RequestBody AdminEntity admin){
		adminService.updateById(admin);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:admin:delete")
    public R delete(@RequestBody Long[] ids){
		adminService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

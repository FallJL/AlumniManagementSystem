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

import com.scu.ams.basic.entity.AuditItemEntity;
import com.scu.ams.basic.service.AuditItemService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;



/**
 * 审核项的基本信息
 *
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-06-08 17:52:38
 */
@RestController
@RequestMapping("basic/audititem")
public class AuditItemController {
    @Autowired
    private AuditItemService auditItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:audititem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = auditItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:audititem:info")
    public R info(@PathVariable("id") Long id){
		AuditItemEntity auditItem = auditItemService.getById(id);

        return R.ok().put("auditItem", auditItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:audititem:save")
    public R save(@RequestBody AuditItemEntity auditItem){
		auditItemService.save(auditItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:audititem:update")
    public R update(@RequestBody AuditItemEntity auditItem){
		auditItemService.updateById(auditItem);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:audititem:delete")
    public R delete(@RequestBody Long[] ids){
		auditItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

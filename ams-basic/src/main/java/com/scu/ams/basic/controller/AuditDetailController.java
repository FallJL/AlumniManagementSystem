package com.scu.ams.basic.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.scu.ams.basic.entity.AuditItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scu.ams.basic.entity.AuditDetailEntity;
import com.scu.ams.basic.service.AuditDetailService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;



/**
 * 审核项的详细信息
 *
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-06-08 17:52:38
 */
@RestController
@RequestMapping("basic/auditdetail")
public class AuditDetailController {
    @Autowired
    private AuditDetailService auditDetailService;

    /**
     * 申请进行审核
     */
    @RequestMapping("/apply")
    public R apply(@RequestBody AuditDetailEntity auditDetail){
        auditDetailService.apply(auditDetail);

        return R.ok();
    }

    /**
     * 审核通过
     */
    @RequestMapping("/auditPass")
    public R auditPass(@RequestBody AuditDetailEntity auditDetail){
        auditDetailService.auditPass(auditDetail);

        return R.ok();
    }

    /**
     * 根据auditId（审核项的id）查出详情
     */
    @RequestMapping("/infoByAuditId/{auditId}")
    public R infoByAuditId(@PathVariable("auditId") Long auditId){
        AuditDetailEntity auditDetail = auditDetailService.infoByAuditId(auditId);

        return R.ok().put("auditDetail", auditDetail);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:auditdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = auditDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:auditdetail:info")
    public R info(@PathVariable("id") Long id){
		AuditDetailEntity auditDetail = auditDetailService.getById(id);

        return R.ok().put("auditDetail", auditDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:auditdetail:save")
    public R save(@RequestBody AuditDetailEntity auditDetail){
		auditDetailService.save(auditDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:auditdetail:update")
    public R update(@RequestBody AuditDetailEntity auditDetail){
		auditDetailService.updateById(auditDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:auditdetail:delete")
    public R delete(@RequestBody Long[] ids){
		auditDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

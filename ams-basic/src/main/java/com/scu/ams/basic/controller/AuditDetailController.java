package com.scu.ams.basic.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private AlumnusBasicService alumnusBasicService;

    /**
     * 申请进行审核
     */
    @RequiresRoles("alumnus")
    @RequestMapping("/apply")
    public R apply(@RequestBody AuditDetailEntity auditDetail){
        // 获取学号
        String aluId = SecurityUtils.getSubject().getPrincipal().toString();

        // 获取该学号的所有审核项，如果其中有“待审核“，则不允许进行新的申请
        List<AuditDetailEntity> auditList = auditDetailService.getListByAluId(aluId);
        for(AuditDetailEntity entity : auditList){
            if(entity.getStatus() == 0){
                return R.error("已有待审核，暂不能进行新的申请");
            }
        }

        // 可以进行新的申请，根据学号返回信息
        AlumnusBasicEntity entity = alumnusBasicService.getByAluId(aluId);
        auditDetail.setAlumnusBasicId(entity.getId());
        auditDetail.setAluId(aluId);
        auditDetail.setAluStatus(null); // 这个字段暂时没用
        auditDetailService.apply(auditDetail);

        return R.ok();
    }

    /**
     * 校友主动进行审核撤销
     */
    @RequiresRoles("alumnus")
    @PostMapping("/audit-repeal")
    public R auditRepeal(@RequestBody Long[] ids){
        // 获取学号
        String aluId = SecurityUtils.getSubject().getPrincipal().toString();

        // 只有本人的“待审核”状态的审核项才能被撤销
        List<AuditDetailEntity> auditList = auditDetailService.getListByAluId(aluId);
        Long notReviewedId = null;
        for(AuditDetailEntity entity : auditList){
            if(entity.getStatus() == 0){
                notReviewedId = entity.getId();
                break;
            }
        }
        for(Long id : ids){
            if(!id.equals(notReviewedId)){
                return R.error("要撤销的审核不属于本人或不是待审核状态！");
            }
        }

        auditDetailService.auditRepeal(Arrays.asList(ids));

        return R.ok();
    }

//    /**
//     * 根据auditId（审核项的id）查出详情
//     */
//    @RequiresRoles("alumnus")
//    @RequestMapping("/infoByAuditId/{auditId}")
//    public R infoByAuditId(@PathVariable("auditId") Long auditId){
//        AuditDetailEntity auditDetail = auditDetailService.infoByAuditId(auditId);
//
//        return R.ok().put("auditDetail", auditDetail);
//    }


    /**
     * 返回某个校友的全部审核项列表
     */
    @RequiresRoles("alumnus")
    @RequestMapping("/info")
    public R info(){
        // 获取学号
        String aluId = SecurityUtils.getSubject().getPrincipal().toString();
        List<AuditDetailEntity> auditList = auditDetailService.getListByAluId(aluId);

        return R.ok().put("auditList", auditList);
    }

    /**
     * 获取审核信息和alumnusbasic表中对应的信息
     */
    @RequiresRoles("alumnus")
    @GetMapping("/info-and-basic/{id}")
    public R infoAndBasic(@PathVariable("id") Long id){
        // 获取学号
        String aluId = SecurityUtils.getSubject().getPrincipal().toString();

        // 只能查看本人的审核项，没有权限看其他人的
        List<AuditDetailEntity> auditList = auditDetailService.getListByAluId(aluId);
        boolean permission = false;
        for(AuditDetailEntity auditDetail: auditList){
            if (auditDetail.getId().equals(id)) {
                permission = true;
                break;
            }
        }
        if (!permission){
            return R.error("要获取的审核项不属于本人！");
        }

        Map<String, Object> map = auditDetailService.infoAndBasic(id);

        return R.ok().put("auditDetail", map.get("auditDetail")).put("alumnusBasic", map.get("alumnusBasic"));
    }

//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    //@RequiresPermissions("basic:auditdetail:save")
//    public R save(@RequestBody AuditDetailEntity auditDetail){
//		auditDetailService.save(auditDetail);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    //@RequiresPermissions("basic:auditdetail:update")
//    public R update(@RequestBody AuditDetailEntity auditDetail){
//		auditDetailService.updateById(auditDetail);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    //@RequiresPermissions("basic:auditdetail:delete")
//    public R delete(@RequestBody Long[] ids){
//		auditDetailService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

}

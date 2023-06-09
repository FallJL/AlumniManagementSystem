package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.AuditDetailEntity;

import java.util.Map;

/**
 * 审核项的详细信息
 *
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-06-08 17:52:38
 */
public interface AuditDetailService extends IService<AuditDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void apply(AuditDetailEntity auditDetail);

    AuditDetailEntity infoByAuditId(Long auditId);

    void auditPass(AuditDetailEntity auditDetail);
}


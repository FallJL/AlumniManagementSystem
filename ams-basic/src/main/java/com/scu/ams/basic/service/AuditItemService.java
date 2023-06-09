package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.AuditItemEntity;

import java.util.Map;

/**
 * 审核项的基本信息
 *
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-06-08 17:52:38
 */
public interface AuditItemService extends IService<AuditItemEntity> {

    PageUtils queryPage(Map<String, Object> params);

}


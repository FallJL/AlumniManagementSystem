package com.scu.ams.basic.dao;

import com.scu.ams.basic.entity.AuditItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 审核项的基本信息
 * 
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-06-08 17:52:38
 */
@Mapper
public interface AuditItemDao extends BaseMapper<AuditItemEntity> {
	
}

package com.scu.ams.basic.dao;

import com.scu.ams.basic.entity.NotificationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知信息
 * 
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Mapper
public interface NotificationDao extends BaseMapper<NotificationEntity> {
	
}

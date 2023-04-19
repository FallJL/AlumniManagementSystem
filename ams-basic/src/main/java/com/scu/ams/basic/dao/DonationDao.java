package com.scu.ams.basic.dao;

import com.scu.ams.basic.entity.DonationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 捐赠的新闻信息
 * 
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Mapper
public interface DonationDao extends BaseMapper<DonationEntity> {
	
}

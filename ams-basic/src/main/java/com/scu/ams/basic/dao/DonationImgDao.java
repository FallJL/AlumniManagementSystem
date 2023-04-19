package com.scu.ams.basic.dao;

import com.scu.ams.basic.entity.DonationImgEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 一个捐赠新闻对应多张图片
一张图片对应一个新闻
 * 
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Mapper
public interface DonationImgDao extends BaseMapper<DonationImgEntity> {
	
}

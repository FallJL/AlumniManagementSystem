package com.scu.ams.basic.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 校友的常用基本信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Mapper
public interface AlumnusBasicDao extends BaseMapper<AlumnusBasicEntity> {

    AlumnusBasicEntity alumniDataView(@Param("alumnusBasicEntity") AlumnusBasicEntity alumnusBasicEntity);

}

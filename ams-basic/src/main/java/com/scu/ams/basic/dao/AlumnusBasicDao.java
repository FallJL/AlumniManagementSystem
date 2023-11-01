package com.scu.ams.basic.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 校友的常用基本信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Mapper
public interface AlumnusBasicDao extends BaseMapper<AlumnusBasicEntity> {


    @MapKey("key")
    List<Map<String, Object>> enterpriseCount();

    @MapKey("key")
    List<Map<String, Object>> nationalityCount();
    @MapKey("key")
    List<Map<String, Object>> majorCount();

    @MapKey("key")
    List<Map<Integer, Object>> graduationCount();

    @MapKey("key")
    List<Map<String, Object>> nativePlaceCount();
    @MapKey("key")
    List<Map<Integer, Object>> degreeStageCount();

    @MapKey("key")
    List<Map<String, Object>> cityCount();

    void updateByAluId(@Param("alu_id")String AluId, @Param("alumnusBasic") AlumnusBasicEntity alumnusBasic);


//    @MapKey("key")
//    List<Map<String, Integer>> test(@Param("xx") String condition);
}

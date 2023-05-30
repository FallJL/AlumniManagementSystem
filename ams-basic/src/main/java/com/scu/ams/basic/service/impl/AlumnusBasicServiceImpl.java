package com.scu.ams.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.AlumnusBasicDao;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import org.springframework.util.StringUtils;


@Service("alumnusBasicService")
public class AlumnusBasicServiceImpl extends ServiceImpl<AlumnusBasicDao, AlumnusBasicEntity> implements AlumnusBasicService {

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<AlumnusBasicEntity> page = this.page(
//                new Query<AlumnusBasicEntity>().getPage(params),
//                new QueryWrapper<AlumnusBasicEntity>()
//        );
//
//        return new PageUtils(page);
//    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 如果key不为空，则需要进行模糊匹配，用wrapper即可
        String key = (String) params.get("key");
        LambdaQueryWrapper<AlumnusBasicEntity> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            wrapper.like(AlumnusBasicEntity::getAluId, key).or().like(AlumnusBasicEntity::getAluName, key);
        }

        IPage<AlumnusBasicEntity> page = this.page(
                new Query<AlumnusBasicEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listRandom(Map<String, Object> params) {
        QueryWrapper<AlumnusBasicEntity> wrapper = new QueryWrapper<>();
        // wrapper.like("aluId", params.get("aluId") + "%");
        wrapper.orderByAsc("RAND()");
        // wrapper.last("LIMIT " + params.get("number"));
        // wrapper.last("LIMIT 10");
        // List<AlumnusBasicEntity> list = baseMapper.selectList(wrapper);
        IPage<AlumnusBasicEntity> page = this.page(
                new Query<AlumnusBasicEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}
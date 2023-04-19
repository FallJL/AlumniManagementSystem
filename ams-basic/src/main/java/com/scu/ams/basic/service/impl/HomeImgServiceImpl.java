package com.scu.ams.basic.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.HomeImgDao;
import com.scu.ams.basic.entity.HomeImgEntity;
import com.scu.ams.basic.service.HomeImgService;


@Service("homeImgService")
public class HomeImgServiceImpl extends ServiceImpl<HomeImgDao, HomeImgEntity> implements HomeImgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeImgEntity> page = this.page(
                new Query<HomeImgEntity>().getPage(params),
                new QueryWrapper<HomeImgEntity>()
        );

        return new PageUtils(page);
    }

}
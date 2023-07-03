package com.scu.ams.basic.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.AdminLogDao;
import com.scu.ams.basic.entity.AdminLogEntity;
import com.scu.ams.basic.service.AdminLogService;


@Service("adminLogService")
public class AdminLogServiceImpl extends ServiceImpl<AdminLogDao, AdminLogEntity> implements AdminLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdminLogEntity> page = this.page(
                new Query<AdminLogEntity>().getPage(params),
                new QueryWrapper<AdminLogEntity>()
        );

        return new PageUtils(page);
    }

}
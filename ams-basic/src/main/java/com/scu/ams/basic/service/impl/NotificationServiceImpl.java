package com.scu.ams.basic.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.NotificationDao;
import com.scu.ams.basic.entity.NotificationEntity;
import com.scu.ams.basic.service.NotificationService;


@Service("notificationService")
public class NotificationServiceImpl extends ServiceImpl<NotificationDao, NotificationEntity> implements NotificationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<NotificationEntity> page = this.page(
                new Query<NotificationEntity>().getPage(params),
                new QueryWrapper<NotificationEntity>()
        );

        return new PageUtils(page);
    }

}
package com.scu.ams.basic.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.AuditItemDao;
import com.scu.ams.basic.entity.AuditItemEntity;
import com.scu.ams.basic.service.AuditItemService;


@Service("auditItemService")
public class AuditItemServiceImpl extends ServiceImpl<AuditItemDao, AuditItemEntity> implements AuditItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AuditItemEntity> page = this.page(
                new Query<AuditItemEntity>().getPage(params),
                new QueryWrapper<AuditItemEntity>()
        );

        return new PageUtils(page);
    }

}
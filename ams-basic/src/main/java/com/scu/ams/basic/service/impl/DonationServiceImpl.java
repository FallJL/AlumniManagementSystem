package com.scu.ams.basic.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.DonationDao;
import com.scu.ams.basic.entity.DonationEntity;
import com.scu.ams.basic.service.DonationService;


@Service("donationService")
public class DonationServiceImpl extends ServiceImpl<DonationDao, DonationEntity> implements DonationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DonationEntity> page = this.page(
                new Query<DonationEntity>().getPage(params),
                new QueryWrapper<DonationEntity>()
        );

        return new PageUtils(page);
    }

}
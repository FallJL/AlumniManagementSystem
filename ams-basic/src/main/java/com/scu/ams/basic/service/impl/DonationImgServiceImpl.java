package com.scu.ams.basic.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.DonationImgDao;
import com.scu.ams.basic.entity.DonationImgEntity;
import com.scu.ams.basic.service.DonationImgService;


@Service("donationImgService")
public class DonationImgServiceImpl extends ServiceImpl<DonationImgDao, DonationImgEntity> implements DonationImgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DonationImgEntity> page = this.page(
                new Query<DonationImgEntity>().getPage(params),
                new QueryWrapper<DonationImgEntity>()
        );

        return new PageUtils(page);
    }

}
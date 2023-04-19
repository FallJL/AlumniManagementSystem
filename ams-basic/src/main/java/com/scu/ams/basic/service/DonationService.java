package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.DonationEntity;

import java.util.Map;

/**
 * 捐赠的新闻信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
public interface DonationService extends IService<DonationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


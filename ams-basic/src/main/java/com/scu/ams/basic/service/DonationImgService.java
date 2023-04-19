package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.DonationImgEntity;

import java.util.Map;

/**
 * 一个捐赠新闻对应多张图片
一张图片对应一个新闻
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
public interface DonationImgService extends IService<DonationImgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


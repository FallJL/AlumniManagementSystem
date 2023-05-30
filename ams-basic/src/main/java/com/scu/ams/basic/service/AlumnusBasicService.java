package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.AlumnusBasicEntity;

import java.util.List;
import java.util.Map;

/**
 * 校友的常用基本信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
public interface AlumnusBasicService extends IService<AlumnusBasicEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listRandom(Map<String, Object> params);
}


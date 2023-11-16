package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.LoginLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-11-15 10:54:38
 */
public interface LoginLogService extends IService<LoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


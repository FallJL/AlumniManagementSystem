package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.AdminLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-06-30 16:09:06
 */
public interface AdminLogService extends IService<AdminLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


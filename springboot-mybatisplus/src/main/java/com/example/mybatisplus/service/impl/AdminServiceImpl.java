package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Admin;
import com.example.mybatisplus.mapper.AdminMapper;
import com.example.mybatisplus.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lxp
 * @since 2021-06-17
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}

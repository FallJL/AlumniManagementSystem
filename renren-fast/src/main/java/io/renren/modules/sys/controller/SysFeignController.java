package io.renren.modules.sys.controller;

import io.renren.common.utils.R;
import io.renren.feign.BasicFeignService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RefreshScope // 动态获取nacos的配置
@RestController
@RequestMapping("/sys/feign")
public class SysFeignController {
    @Autowired
    BasicFeignService basicFeignService;
    @Value("${ams.basic.token}")
    private String feignToken; // openfeign调用的token密钥

    /**
     * 校友列表
     */
    @GetMapping("/alumnus-list")
    @RequiresPermissions("sys:user:list")
    public R alumnusList(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有校友列表
        params.put("feignToken", feignToken); // 能到这一步，说明通过了管理员权限认证，加上token密钥

        return basicFeignService.alumnusList(params);
    }

    /**
     * 审核列表
     */
    @GetMapping("/audit-list")
    @RequiresPermissions("sys:user:list")
    public R auditList(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有审核列表
        params.put("feignToken", feignToken); // 能到这一步，说明通过了管理员权限认证，加上token密钥

        return basicFeignService.auditList(params);
    }

    /**
     * 获取审核信息和alumnusbasic表中对应的信息
     */
    @GetMapping("/info-and-basic")
    @RequiresPermissions("sys:user:list")
    public R infoAndBasic(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有审核列表
        params.put("feignToken", feignToken); // 能到这一步，说明通过了管理员权限认证，加上token密钥

        return basicFeignService.infoAndBasic(params);
    }

}

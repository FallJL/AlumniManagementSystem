package com.scu.ams.basic.controller;

import com.scu.ams.basic.service.AlumnusBasicService;
import com.scu.ams.basic.service.AuditDetailService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 专门用来接受openfeign调用的controller，主要是管理员访问校友信息的请求
 */
@RefreshScope // 动态获取nacos的配置
@RestController
@RequestMapping("basic/feign")
public class FeignController {
    @Autowired
    private AlumnusBasicService alumnusBasicService;
    @Autowired
    private AuditDetailService auditDetailService;
    @Value("${ams.basic.token}")
    private String feignToken; // openfeign调用的token密钥

    /**
     * 校友列表
     */
    @GetMapping ("/alumnus-list")
    public R alumnusList(@RequestParam Map<String, Object> params){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = (String) params.get("feignToken");
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }
        PageUtils page = alumnusBasicService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 审核列表
     */
    @GetMapping ("/audit-list")
    public R auditList(@RequestParam Map<String, Object> params){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = (String) params.get("feignToken");
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }
        PageUtils page = auditDetailService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取审核信息和alumnusbasic表中对应的信息
     */
    @GetMapping("/info-and-basic")
    public R infoAndBasic(@RequestParam Map<String, Object> params){
        String token = (String) params.get("feignToken");
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        String id = (String) params.get("id");
        Map<String, Object> map = auditDetailService.infoAndBasic(Long.parseLong(id.trim()));

        return R.ok().put("auditDetail", map.get("auditDetail")).put("alumnusBasic", map.get("alumnusBasic"));
    }
}

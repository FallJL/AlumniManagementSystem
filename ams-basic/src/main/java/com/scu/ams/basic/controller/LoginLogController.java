package com.scu.ams.basic.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scu.ams.basic.entity.LoginLogEntity;
import com.scu.ams.basic.service.LoginLogService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;



/**
 * 
 *
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-11-15 10:54:38
 */
@RestController
@RequestMapping("basic/loginlog")
public class LoginLogController {
    @Autowired
    private LoginLogService loginLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:loginlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = loginLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:loginlog:info")
    public R info(@PathVariable("id") Integer id){
		LoginLogEntity loginLog = loginLogService.getById(id);

        return R.ok().put("loginLog", loginLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:loginlog:save")
    public R save(@RequestBody LoginLogEntity loginLog){
		loginLogService.save(loginLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:loginlog:update")
    public R update(@RequestBody LoginLogEntity loginLog){
		loginLogService.updateById(loginLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:loginlog:delete")
    public R delete(@RequestBody Integer[] ids){
		loginLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

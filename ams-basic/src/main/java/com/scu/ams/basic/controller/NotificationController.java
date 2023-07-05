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

import com.scu.ams.basic.entity.NotificationEntity;
import com.scu.ams.basic.service.NotificationService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;



/**
 * 通知信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@RestController
@RequestMapping("basic/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

//    /**
//     * 列表
//     */
//    @RequestMapping("/list")
//    //@RequiresPermissions("basic:notification:list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = notificationService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }
//
//
//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{id}")
//    //@RequiresPermissions("basic:notification:info")
//    public R info(@PathVariable("id") Long id){
//		//Retrieve the notification entity by the given ID
//		NotificationEntity notification = notificationService.getById(id);
//
//        //Return a successful response with the notification entity as a payload
//        return R.ok().put("notification", notification);
//    }
//
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    //@RequiresPermissions("basic:notification:save")
//    public R save(@RequestBody NotificationEntity notification){
//		notificationService.save(notification);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    //@RequiresPermissions("basic:notification:update")
//    public R update(@RequestBody NotificationEntity notification){
//		notificationService.updateById(notification);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    //@RequiresPermissions("basic:notification:delete")
//    public R delete(@RequestBody Long[] ids){
//		notificationService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

}

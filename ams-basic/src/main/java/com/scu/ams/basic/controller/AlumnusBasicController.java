package com.scu.ams.basic.controller;

import java.util.*;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.scu.ams.basic.vo.EnterprisePropertyVO;
import com.scu.ams.basic.vo.GraduationVO;
import com.scu.ams.basic.vo.MajorVO;
import com.scu.ams.basic.vo.NationalityVO;
import com.scu.common.valid.AddGroup;
import com.scu.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;

import javax.servlet.http.HttpServletResponse;


/**
 * 校友的常用基本信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@RefreshScope
@RestController
@RequestMapping("basic/alumnusbasic")
public class AlumnusBasicController {
    @Autowired
    private AlumnusBasicService alumnusBasicService;
    /*
    * 测试nacos配置
    **/
//    @Value("${basic.name}")
//    private String name;
//    @Value("${basic.age}")
//    private Integer age;
//    @RequestMapping("/test")
//    public R test(){
//        return R.ok().put("name",name).put("age",age);
//    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("basic:alumnusbasic:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = alumnusBasicService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 校友抽样（根据给定条件，随机抽样）
     */
    @RequestMapping("/list/random")
    //@RequiresPermissions("basic:alumnusbasic:list")
    public R listRandom(@RequestParam Map<String, Object> params){
        PageUtils page = alumnusBasicService.listRandom(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("basic:alumnusbasic:info")
    public R info(@PathVariable("id") Long id){
		AlumnusBasicEntity alumnusBasic = alumnusBasicService.getById(id);

        return R.ok().put("alumnusBasic", alumnusBasic);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("basic:alumnusbasic:save")
    public R save(@Validated({AddGroup.class}) @RequestBody AlumnusBasicEntity alumnusBasic /*, BindingResult result */){
//		if(result.hasErrors()){
//            Map<String, String> map = new HashMap<>();
//            // FieldError
//            result.getFieldErrors().forEach((item) -> {
//                // 1.获取错误信息
//                String message = item.getDefaultMessage();
//                // 2.获取错误的属性名
//                String field = item.getField();
//                map.put(field, message);
//            });
//            return R.error(400, "提交的数据不合法").put("data", map);
//        } else {
//            alumnusBasicService.save(alumnusBasic);
//        }

        alumnusBasicService.save(alumnusBasic);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("basic:alumnusbasic:update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody AlumnusBasicEntity alumnusBasic){
		alumnusBasicService.updateById(alumnusBasic);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("basic:alumnusbasic:delete")
    public R delete(@RequestBody Long[] ids){
		alumnusBasicService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /*
    * 校友数据看板
    **/

    @RequestMapping("/alumniData")
    public R list(@RequestBody AlumnusBasicEntity alumnusBasicEntity) {
        PageUtils page = alumnusBasicService.queryPageWrapper(alumnusBasicEntity);
        return R.ok().put("page", page);
    }
    //导出
    @RequestMapping ("/export")
    public R export(@RequestBody AlumnusBasicEntity alumnusBasicEntity, HttpServletResponse response){
        alumnusBasicService.export(alumnusBasicEntity,response);
        return R.ok();
    }
    //企业性质统计图
    @RequestMapping ("/enterpriseChart")
    public R enterpriseChart(){
        List<EnterprisePropertyVO> enterprisePropertyVO = alumnusBasicService.enterpriseChart();
        return R.ok().put("enterprisePropertyVO",enterprisePropertyVO);
    }
    //民族统计图
    @RequestMapping ("/nationality")
    public R nationality(){
        List<NationalityVO> nationalityVO = alumnusBasicService.nationalityChart();
        return R.ok().put("nationalityVO",nationalityVO);
    }
    //专业统计图
    @RequestMapping ("/major")
    public R major(){
        List<MajorVO> majorVo = alumnusBasicService.majorChart();
        return R.ok().put("majorVo",majorVo);
    }
    //毕业时间统计图
    @RequestMapping ("/graduation")
    public R graduation(){
        List<GraduationVO> graduationVO = alumnusBasicService.graduationChart();
        return R.ok().put("graduationVO",graduationVO);
    }
    @RequestMapping ("/test")
    public R test(){
        PageUtils page = alumnusBasicService.test();
        return R.ok().put("page",page);
    }
}

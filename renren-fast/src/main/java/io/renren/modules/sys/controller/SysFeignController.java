package io.renren.modules.sys.controller;

import io.renren.common.utils.R;
import io.renren.feign.BasicFeignService;
import io.renren.modules.sys.dto.*;
import io.renren.modules.sys.vo.AlumusQueryVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

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
    @RequiresPermissions("sys:user:list") // 这个sys:user:list是登录后从数据库sys_menu表的管理员页面读取出来的，请勿删除
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

    /**
     * 管理员查看某个校友的详细信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:user:list")
    public R info(@PathVariable("id") Long id){
        AlumnusInfoDTO alumnusInfoDTO = new AlumnusInfoDTO();
        alumnusInfoDTO.setId(id);
        alumnusInfoDTO.setFeignToken(feignToken);

        return basicFeignService.info(alumnusInfoDTO);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:user:list")
    public R update(@RequestBody AlumnusBasicDTO alumnusBasicDTO){
        alumnusBasicDTO.setFeignToken(feignToken);

        return basicFeignService.update(alumnusBasicDTO);
    }

    /**
     * 多条件查询
     **/
    @PostMapping("/alumniData")
    @RequiresPermissions("sys:user:list")
    public R alumniData(@RequestBody AlumusQueryVO alumusQueryVO) {
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        alumusQueryVO.setFeignToken(feignToken);

        return basicFeignService.alumniData(alumusQueryVO);
    }

    /**
     * 管理员重置校友的密码
     */
    @PostMapping("/reset-alumnus-password")
    @RequiresPermissions("sys:user:list")
    public R resetAlumnusPassword(@RequestBody AlumnusIdsDTO alumnusIdsDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        alumnusIdsDTO.setFeignToken(feignToken);

        return basicFeignService.resetAlumnusPassword(alumnusIdsDTO);
    }

    /**
     * 删除某个校友数据（彻底删除）
     */
    @PostMapping("/delete-alumnus")
    @RequiresPermissions("sys:user:list")
    public R deleteAlumnus(@RequestBody AlumnusIdsDTO alumnusIdsDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        alumnusIdsDTO.setFeignToken(feignToken);

        return basicFeignService.deleteAlumnus(alumnusIdsDTO);
    }

    /**
     * 导入数据库
     */
    @PostMapping ("/inport")
    @RequiresPermissions("sys:user:list")
    public R inport(@RequestBody AlumnusImportDTO alumnusImportDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        alumnusImportDTO.setFeignToken(feignToken);

        return basicFeignService.inport(alumnusImportDTO);
    }

    /**
     * 专业统计图
     */
    @GetMapping ("/major") // 这里是Get
    @RequiresPermissions("sys:user:list")
    public R major(){
        return basicFeignService.major(feignToken);
    }

    /**
     * 所在城市统计图
     */
    @GetMapping ("/city")
    @RequiresPermissions("sys:user:list")
    public R city(){
        return basicFeignService.city(feignToken);
    }

    /**
     * 毕业时间统计图
     */
    @GetMapping ("/graduation")
    @RequiresPermissions("sys:user:list")
    public R graduation(){
        return basicFeignService.graduation(feignToken);
    }

    /**
     * 民族统计图
     */
    @GetMapping ("/nationality")
    @RequiresPermissions("sys:user:list")
    public R nationality(){
        return basicFeignService.nationality(feignToken);
    }

    /**
     * 学历阶段统计图
     */
    @GetMapping ("/degreeStage")
    @RequiresPermissions("sys:user:list")
    public R degreeStage(){
        return basicFeignService.degreeStage(feignToken);
    }

    /**
     * 籍贯统计图
     */
    @GetMapping ("/nativePlace")
    @RequiresPermissions("sys:user:list")
    public R nativePlace(){
        return basicFeignService.nativePlace(feignToken);
    }

    /**
     * 企业性质统计图
     */
    @GetMapping ("/enterpriseChart")
    @RequiresPermissions("sys:user:list")
    public R enterpriseChart(){
        return basicFeignService.enterpriseChart(feignToken);
    }

    /**
     * 审核通过
     */
    @PostMapping("/audit-pass")
    @RequiresPermissions("sys:user:list")
    public R auditPass(@RequestBody AuditOperationDTO auditOperationDTO){
        auditOperationDTO.setFeignToken(feignToken);

        return basicFeignService.auditPass(auditOperationDTO);
    }

    /**
     * 审核不通过
     */
    @PostMapping("/audit-not-pass")
    @RequiresPermissions("sys:user:list")
    public R auditNotPass(@RequestBody AuditOperationDTO auditOperationDTO){
        auditOperationDTO.setFeignToken(feignToken);

        return basicFeignService.auditNotPass(auditOperationDTO);
    }

    /**
     * 发送通知邮件
     */
    @PostMapping("/sendInformMail")
    @RequiresPermissions("sys:user:list")
    public R sendInformMail(@RequestBody InformMailRequestDTO dto){
        dto.setFeignToken(feignToken);

        return basicFeignService.sendInformMail(dto);
    }
}

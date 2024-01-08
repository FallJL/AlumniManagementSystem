package com.scu.ams.basic.controller;

import com.scu.ams.basic.dto.*;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import com.scu.ams.basic.service.AuditDetailService;
import com.scu.ams.basic.vo.*;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 专门用来接受openfeign调用的controller，主要是 管理员 访问校友信息的请求
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
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = (String) params.get("feignToken");
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        String id = (String) params.get("id");
        Map<String, Object> map = auditDetailService.infoAndBasic(Long.parseLong(id.trim()));

        return R.ok().put("auditDetail", map.get("auditDetail")).put("alumnusBasic", map.get("alumnusBasic"));
    }

    /**
     * 管理员查看某个校友的详细信息
     */
    @PostMapping("/info")
    public R info(@RequestBody AlumnusInfoDTO alumnusInfoDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = alumnusInfoDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        AlumnusBasicVo vo = alumnusBasicService.info(alumnusInfoDTO.getId());

        return R.ok().put("alumnusBasic", vo);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody AlumnusBasicDTO alumnusBasicDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = alumnusBasicDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        AlumnusBasicEntity alumnusBasic = new AlumnusBasicEntity();
        BeanUtils.copyProperties(alumnusBasicDTO, alumnusBasic);
        alumnusBasic.setAluId(null); // 学号不能改，所以置为null不允许更新
        alumnusBasic.setAluStatus(null); // 这个字段暂时没用，所以也置为null不允许更新
        alumnusBasicService.updateById(alumnusBasic);
        return R.ok();
    }

    /**
     * 多条件查询
     **/
    @PostMapping("/alumniData")
    public R alumniData(@RequestBody AlumusQueryVO alumusQueryVO) {
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = alumusQueryVO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        PageUtils page = alumnusBasicService.queryPageWrapper(alumusQueryVO);
        return R.ok().put("page", page);
    }

    /**
     * 管理员重置校友的密码
     */
    @PostMapping("/reset-alumnus-password")
    public R resetAlumnusPassword(@RequestBody AlumnusIdsDTO alumnusIdsDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = alumnusIdsDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        Long[] ids = alumnusIdsDTO.getIds();
        alumnusBasicService.resetAlumnusPassword(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 删除某个校友数据（彻底删除）
     */
    @PostMapping("/delete-alumnus")
    public R deleteAlumnus(@RequestBody AlumnusIdsDTO alumnusIdsDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = alumnusIdsDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        Long[] ids = alumnusIdsDTO.getIds();
        alumnusBasicService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 禁用或启用某个校友数据
     */
    @PostMapping("/disable-or-enable")
    public R disableOrEnable(@RequestBody AlumnusInfoDTO alumnusInfoDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = alumnusInfoDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        Long id = alumnusInfoDTO.getId();
        int aluStatus = alumnusBasicService.selectStatusById(id);
        if (aluStatus == 0) aluStatus = 1;
        else aluStatus = 0;

        AlumnusBasicEntity entity = new AlumnusBasicEntity();
        entity.setId(id);
        entity.setAluStatus(aluStatus);
        alumnusBasicService.updateById(entity);

        return R.ok();
    }


    /**
     * 导入数据库
     */
    @PostMapping ("/inport")
    public R inport(@RequestBody AlumnusImportDTO alumnusImportDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = alumnusImportDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        AlumnusBasicEntity alumnusBasicEntity = new AlumnusBasicEntity();
        BeanUtils.copyProperties(alumnusImportDTO, alumnusBasicEntity);
        alumnusBasicService.inport(alumnusBasicEntity);
        return R.ok();
    }
    /**
     * 导入数据库前查看是否存在相同学号
     */
    @PostMapping ("/selectById")
    public R selectById(@RequestBody AlumnusImportDTO alumnusImportDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = alumnusImportDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        AlumnusBasicEntity alumnusBasicEntity = new AlumnusBasicEntity();
        int exist;
        if(alumnusImportDTO.getAluId() == null ||  alumnusImportDTO.getAluId().equals("")){
            exist = 0;
        }else{
            BeanUtils.copyProperties(alumnusImportDTO, alumnusBasicEntity);
            exist = alumnusBasicService.selectById(alumnusBasicEntity);
        }
        return R.ok().put("exist",exist);
    }

    /**
     * 覆盖数据
     */
    @PostMapping("/cover")
    public R cover(@RequestBody AlumnusBasicDTO alumnusBasicDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = alumnusBasicDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        AlumnusBasicEntity alumnusBasic = new AlumnusBasicEntity();
        BeanUtils.copyProperties(alumnusBasicDTO, alumnusBasic);
        alumnusBasicService.cover(alumnusBasic);
        return R.ok();
    }

    /**
     * 专业统计图
     */
    @PostMapping ("/major") // 这里是Post
    public R major(@RequestBody String token){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        List<MajorVO> majorVo = alumnusBasicService.majorChart();
        return R.ok().put("majorVo",majorVo);
    }

    /**
     * 所在城市统计图
     */
    @PostMapping ("/city")
    public R city(@RequestBody String token){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        List<CityVO> cityVO = alumnusBasicService.cityChart();
        return R.ok().put("cityVO",cityVO);
    }

    /**
     * 毕业时间统计图
     */
    @PostMapping ("/graduation")
    public R graduation(@RequestBody String token){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        List<GraduationVO> graduationVO = alumnusBasicService.graduationChart();
        return R.ok().put("graduationVO",graduationVO);
    }

    /**
     * 民族统计图
     */
    @PostMapping ("/nationality")
    public R nationality(@RequestBody String token){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        List<NationalityVO> nationalityVO = alumnusBasicService.nationalityChart();
        return R.ok().put("nationalityVO",nationalityVO);
    }

    /**
     * 学历阶段统计图
     */
    @PostMapping ("/degreeStage")
    public R degreeStage(@RequestBody String token){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        List<DegreeStageVO> degreeStageVO = alumnusBasicService.degreeStageChart();
        return R.ok().put("degreeStageVO",degreeStageVO);
    }

    /**
     * 籍贯统计图
     */
    @PostMapping ("/nativePlace")
    public R nativePlace(@RequestBody String token){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        List<NativePlaceVO> nativePlaceVO = alumnusBasicService.nativePlaceChart();
        return R.ok().put("nativePlaceVO",nativePlaceVO);
    }

    /**
     * 企业性质统计图
     */
    @PostMapping ("/enterpriseChart")
    public R enterpriseChart(@RequestBody String token){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        List<EnterprisePropertyVO> enterprisePropertyVO = alumnusBasicService.enterpriseChart();
        return R.ok().put("enterprisePropertyVO",enterprisePropertyVO);
    }

    /**
     * 审核通过
     */
    @PostMapping("/audit-pass")
    public R auditPass(@RequestBody AuditOperationDTO auditOperationDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = auditOperationDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        Long[] ids = auditOperationDTO.getIds();
        auditDetailService.auditPass(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 审核不通过
     */
    @PostMapping("/audit-not-pass")
    public R auditNotPass(@RequestBody AuditOperationDTO auditOperationDTO){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = auditOperationDTO.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        Long[] ids = auditOperationDTO.getIds();
        auditDetailService.auditNotPass(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 发送通知邮件
     */
    @PostMapping("/sendInformMail")
    public R sendInformMail(@RequestBody InformMailRequestDTO dto){
        // 判断有无token密钥，若有：说明是openfeign调用的，合法
        String token = dto.getFeignToken();
        if(token == null || !token.equals(feignToken)){
            return R.error(402, "feignToken权限不正确");
        }

        Long[] ids = dto.getIds();
        String information = dto.getInformation();
        alumnusBasicService.sendInformMail(ids, information);
        return R.ok();
    }
}

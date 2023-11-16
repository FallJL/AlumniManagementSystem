package io.renren.feign;

import io.renren.common.utils.R;
import io.renren.modules.sys.dto.*;
import io.renren.modules.sys.vo.AlumusQueryVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "ams-basic")
public interface BasicFeignService {

    @GetMapping("/basic/feign/alumnus-list")
    R alumnusList(@RequestParam Map<String, Object> params);

    @GetMapping("/basic/feign/audit-list")
    R auditList(@RequestParam Map<String, Object> params);

    @GetMapping("/basic/feign/info-and-basic")
    R infoAndBasic(@RequestParam Map<String, Object> params);

    @PostMapping("/basic/feign/info")
    R info(@RequestBody AlumnusInfoDTO alumnusInfoDTO);

    @PostMapping("/basic/feign/update")
    R update(@RequestBody AlumnusBasicDTO alumnusBasicDTO);

    @PostMapping("/basic/feign/alumniData")
    R alumniData(@RequestBody AlumusQueryVO alumusQueryVO);

    @PostMapping("/basic/feign/reset-alumnus-password")
    R resetAlumnusPassword(@RequestBody AlumnusIdsDTO alumnusIdsDTO);

    @PostMapping("/basic/feign/delete-alumnus")
    R deleteAlumnus(@RequestBody AlumnusIdsDTO alumnusIdsDTO);

    @PostMapping("/basic/feign/disable-or-enable")
    R disableOrEnable(@RequestBody AlumnusInfoDTO alumnusInfoDTO);

    @PostMapping ("/basic/feign/inport")
    public R inport(@RequestBody AlumnusImportDTO alumnusImportDTO);

    @PostMapping ("/basic/feign/major") // 这里是Post
    R major(@RequestBody String token);

    @PostMapping ("/basic/feign/city")
    R city(@RequestBody String token);

    @PostMapping ("/basic/feign/graduation")
    R graduation(@RequestBody String token);

    @PostMapping ("/basic/feign/nationality")
    R nationality(@RequestBody String token);

    @PostMapping ("/basic/feign/degreeStage")
    R degreeStage(@RequestBody String token);

    @PostMapping ("/basic/feign/nativePlace")
    R nativePlace(@RequestBody String token);

    @PostMapping ("/basic/feign/enterpriseChart")
    R enterpriseChart(@RequestBody String token);

    @PostMapping("/basic/feign/audit-pass")
    R auditPass(@RequestBody AuditOperationDTO auditOperationDTO);

    @PostMapping("/basic/feign/audit-not-pass")
    R auditNotPass(@RequestBody AuditOperationDTO auditOperationDTO);

    @PostMapping("/basic/feign/sendInformMail")
    R sendInformMail(@RequestBody InformMailRequestDTO dto);

    @PostMapping ("/basic/feign/selectById")
    R selectById(@RequestBody AlumnusImportDTO alumnusImportDTO);

    @PostMapping ("/basic/feign/cover")
    R cover(AlumnusBasicDTO alumnusBasicDTO);
}

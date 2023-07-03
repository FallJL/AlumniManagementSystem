package io.renren.feign;

import io.renren.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "ams-basic")
public interface BasicFeignService {

    @GetMapping("/basic/feign/alumnus-list")
    R alumnusList(@RequestParam Map<String, Object> params);

    @GetMapping("/basic/feign/audit-list")
    R auditList(@RequestParam Map<String, Object> params);

    @GetMapping("/basic/feign/info-and-basic")
    R infoAndBasic(@RequestParam Map<String, Object> params);
}

package io.renren.modules.sys.dto;

import lombok.Data;

@Data
public class AuditOperationDTO {
    private Long[] ids;

    private String feignToken;
}

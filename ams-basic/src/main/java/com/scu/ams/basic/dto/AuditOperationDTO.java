package com.scu.ams.basic.dto;

import lombok.Data;

@Data
public class AuditOperationDTO {
    private Long[] ids;

    private String feignToken;
}

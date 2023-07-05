package com.scu.ams.basic.dto;

import lombok.Data;

@Data
public class InformMailRequestDTO {
    private Long[] ids;

    private String information;

    private String feignToken;
}

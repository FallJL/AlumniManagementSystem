package io.renren.modules.sys.dto;

import lombok.Data;

@Data
public class InformMailRequestDTO {
    private Long[] ids;

    private String information;

    private String feignToken;
}

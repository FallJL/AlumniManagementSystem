package com.scu.ams.basic.dto;

import lombok.Data;

@Data
public class PageDTO {
    private Integer pageNo=1;
    private Integer pageSize=10;

    public PageDTO(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}

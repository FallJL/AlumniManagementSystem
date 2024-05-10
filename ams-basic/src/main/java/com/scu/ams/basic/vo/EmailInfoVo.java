package com.scu.ams.basic.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailInfoVo implements Serializable {
    private Long[] ids;
    private String information;

//    public EmailInfoVo(Long[] ids, String information) {
//        this.ids = ids;
//        this.information = information;
//    }
}

package com.scu.ams.basic.vo;

import lombok.Data;

@Data
public class NationalityVO {
    private Long count;
    private String nationality;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

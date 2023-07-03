package com.scu.ams.basic.vo;

import lombok.Data;

@Data
public class CityVO {
    private Long count;
    private String city;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

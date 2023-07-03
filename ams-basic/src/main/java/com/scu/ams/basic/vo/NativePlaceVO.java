package com.scu.ams.basic.vo;

import lombok.Data;

@Data
public class NativePlaceVO {
    private Long count;
    private String nativePlace;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }
}

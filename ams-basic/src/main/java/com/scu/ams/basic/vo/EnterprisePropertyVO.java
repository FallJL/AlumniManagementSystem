package com.scu.ams.basic.vo;

import lombok.Data;

@Data
public class EnterprisePropertyVO {
    private Long count;
    private String enterpriseProperty;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getEnterpriseProperty() {
        return enterpriseProperty;
    }

    public void setEnterpriseProperty(String enterpriseProperty) {
        this.enterpriseProperty = enterpriseProperty;
    }
}

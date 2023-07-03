package com.scu.ams.basic.vo;

import lombok.Data;

@Data
public class DegreeStageVO {
    private Long count;
    private Integer degreeStage;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getDegreeStage() {
        return degreeStage;
    }

    public void setDegreeStage(Integer degreeStage) {
        this.degreeStage = degreeStage;
    }
}

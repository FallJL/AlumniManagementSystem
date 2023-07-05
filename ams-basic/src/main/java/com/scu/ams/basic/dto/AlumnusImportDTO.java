package com.scu.ams.basic.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AlumnusImportDTO {

    private String feignToken;

    private Long id;
    /**
     * 姓名
     */
    private String aluName;
    /**
     * 学号
     */
    private String aluId;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 民族
     */
    private String nationality;
    /**
     * 政治面貌
     */
    private String politicalStatus;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 班级
     */
    private String clazz;
    /**
     * 入学时间
     */
//	private Date admissionTime;
    private String admissionTime;
    /**
     * 毕业时间
     */
//	private Date graduationTime;
    private String graduationTime;
    /**
     * 专业
     */
    private String major;
    /**
     * 阶段
     */
    private Integer degreeStage;
    /**
     * 手机
     */
    private String phoneNum;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 工作单位
     */
    private String workUnit;
    /**
     * 担任职务
     */
    private String jobTitle;
    /**
     * 企业性质
     */
    private String enterpriseProperty;
    /**
     * 备注
     */
    private String note;
    /**
     * 状态
     */
    private Integer aluStatus;
    /**
     * 密码
     */
    private String password;
    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}

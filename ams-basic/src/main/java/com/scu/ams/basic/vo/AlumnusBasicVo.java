package com.scu.ams.basic.vo;

import lombok.Data;

import java.util.Date;

/**
 * 校友的常用基本信息vo
 * 相比于实体，没有一些敏感字段：密码
 *
 */
@Data
public class AlumnusBasicVo {
    /**
     * id
     */
    private Long id;
    /**
     * 姓名
     */
    private String aluName;
    /**
     * 学号
     */
    // @NotBlank(message = "学号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String aluId;
    /**
     * 性别
     */
    // @ListValue(vals={0, 1}, groups = {AddGroup.class, UpdateGroup.class}) // 自定义的校验注解，0表示男，1表示女
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
    private Date admissionTime;
    /**
     * 毕业时间
     */
    private Date graduationTime;
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
     * 注册时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}

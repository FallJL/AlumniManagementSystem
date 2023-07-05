package io.renren.modules.sys.dto;

import lombok.Data;

/**
 * 用于openfeign调用，管理员修改校友数据时传输的dto
 */
@Data
public class AlumnusBasicDTO {
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
    private String admissionTime;
    /**
     * 毕业时间
     */
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

    private String feignToken;
}

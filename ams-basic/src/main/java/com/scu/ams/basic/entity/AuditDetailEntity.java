package com.scu.ams.basic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 审核项的详细信息
 * 
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-06-08 17:52:38
 */
@Data
@TableName("audit_detail")
public class AuditDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 所属审核项的id
	 */
	private Long auditId;
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

}

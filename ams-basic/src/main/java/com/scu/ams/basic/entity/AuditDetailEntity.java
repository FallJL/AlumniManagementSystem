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
	 * 校友基本信息表的id
	 */
	private Long alumnusBasicId;
	/**
	 * 姓名
	 */
	private String aluName;
	/**
	 * 学号
	 */
	private String aluId;
	/**
	 * 审核状态【0待审核，1审核通过，2审核未通过，3已撤销】
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
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
	/**
	 * 状态
	 */
	private Integer aluStatus;

}

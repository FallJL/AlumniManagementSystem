package com.scu.ams.basic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 校友的常用基本信息
 * 
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Data
@TableName("alumnus_basic")
public class AlumnusBasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
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
	 * 曾用名
	 */
	private String aluFormerName;
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
	 * 院系
	 */
	private String department;
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
	 * 通信地址
	 */
	private String mailingAddress;
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
	 * 行业类别
	 */
	private String industryCategory;
	/**
	 * 企业性质
	 */
	private String enterpriseProperty;
	/**
	 * 出生日期
	 */
	private Date birthDate;
	/**
	 * 工作电话
	 */
	private String workPhone;
	/**
	 * 工作地址
	 */
	private String workAddress;
	/**
	 * QQ
	 */
	private String qq;
	/**
	 * 微博
	 */
	private String weibo;
	/**
	 * 个人网站
	 */
	private String personalWebsite;
	/**
	 * 微信
	 */
	private String wechat;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * vip
	 */
	private String vip;
	/**
	 * 在校期间荣誉
	 */
	private String honorsDuringSchool;
	/**
	 * 头像地址
	 */
	private String portraitUrl;
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

}

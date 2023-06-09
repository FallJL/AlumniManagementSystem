package com.scu.ams.basic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.mysql.cj.protocol.x.XMessage;
import com.scu.common.valid.AddGroup;
import com.scu.common.valid.ListValue;
import com.scu.common.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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
	@NotNull(message = "修改时必须指定id", groups = {UpdateGroup.class})
	@Null(message = "新增时不能指定id", groups = {AddGroup.class})
	@TableId
	private Long id;
	/**
	 * 姓名
	 */
	@NotBlank(message = "姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
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

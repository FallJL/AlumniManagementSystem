package com.scu.ams.basic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 管理员信息
 * 
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Data
@TableName("admin")
public class AdminEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 管理员名
	 */
	private String adminName;
	/**
	 * 密码
	 */
	private String adminPassword;
	/**
	 * 权限
	 */
	private Integer adminAuthority;
	/**
	 * 注册时间
	 */
	private Date adminCreateTime;

}

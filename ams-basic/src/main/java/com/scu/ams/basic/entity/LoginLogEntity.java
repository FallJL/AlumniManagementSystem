package com.scu.ams.basic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author rjl
 * @email 190276434@qq.com
 * @date 2023-11-15 10:54:38
 */
@Data
@TableName("login_log")
public class LoginLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 登陆人姓名
	 */
	private String name;
	/**
	 * 登录ip
	 */
	private String ip;
	/**
	 * ip归属地信息
	 */
	private String ipAttribution;
	/**
	 * 创建时间
	 */
	private String createTime;

}

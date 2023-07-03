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
 * @date 2023-06-30 16:09:06
 */
@Data
@TableName("admin_log")
public class AdminLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 操作事件
	 */
	private String operation;
	/**
	 * 操作时间
	 */
	private String time;

}

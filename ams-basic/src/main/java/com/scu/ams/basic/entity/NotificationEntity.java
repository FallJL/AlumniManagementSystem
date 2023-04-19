package com.scu.ams.basic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 通知信息
 * 
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Data
@TableName("notification")
public class NotificationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 标题
	 */
	private String notifTitle;
	/**
	 * 通知内容
	 */
	private String notifContent;
	/**
	 * 状态
	 */
	private Integer notifStatus;
	/**
	 * 发布时间
	 */
	private Date notifCreateTime;
	/**
	 * 更新时间
	 */
	private Date notifUpdateTime;

}

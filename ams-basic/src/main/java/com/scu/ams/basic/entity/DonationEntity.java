package com.scu.ams.basic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 捐赠的新闻信息
 * 
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Data
@TableName("donation")
public class DonationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 标题
	 */
	private String donationTitle;
	/**
	 * 新闻内容
	 */
	private String donationContent;
	/**
	 * 捐赠需求
	 */
	private String donationDemand;
	/**
	 * 状态
	 */
	private Integer donationStatus;
	/**
	 * 发布时间
	 */
	private Date donationReleaseTime;
	/**
	 * 更新时间
	 */
	private Date donationUpdateTime;

}

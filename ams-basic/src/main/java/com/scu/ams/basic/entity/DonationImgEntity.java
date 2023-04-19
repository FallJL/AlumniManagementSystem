package com.scu.ams.basic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 一个捐赠新闻对应多张图片
一张图片对应一个新闻
 * 
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Data
@TableName("donation_img")
public class DonationImgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 外键-捐赠新闻表
	 */
	private Long donationId;
	/**
	 * 图片名
	 */
	private String donationImgName;
	/**
	 * 图片地址
	 */
	private String donationImgUrl;
	/**
	 * 顺序
	 */
	private Integer donationImgSort;
	/**
	 * 是否默认图（0为不默认，1为默认）
	 */
	private Integer donationImgDefault;

}

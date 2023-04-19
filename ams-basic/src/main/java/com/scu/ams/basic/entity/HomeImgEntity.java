package com.scu.ams.basic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 首页图片的信息
 * 
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
@Data
@TableName("home_img")
public class HomeImgEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 图片名
	 */
	private String homeImgName;
	/**
	 * 图片地址
	 */
	private String homeImgUrl;
	/**
	 * 发布时间
	 */
	private Date homeImgCreateTime;
	/**
	 * 是否默认图（0为不默认，1为默认）
	 */
	private Integer homeImgDefault;

}

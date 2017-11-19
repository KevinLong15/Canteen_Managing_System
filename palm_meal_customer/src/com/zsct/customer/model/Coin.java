/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.model;

public class Coin {
	private String id;
	private String shibi;
	private String user_id;
	private String create_time;
	private String type;

	public Coin(String id, String shibi, String user_id, String create_time,
			String type) {
		super();
		this.id = id;
		this.shibi = shibi;
		this.user_id = user_id;
		this.create_time = create_time;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShibi() {
		return shibi;
	}

	public void setShibi(String shibi) {
		this.shibi = shibi;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Coin [id=" + id + ", shibi=" + shibi + ", user_id=" + user_id
				+ ", create_time=" + create_time + ", type=" + type + "]";
	}

}

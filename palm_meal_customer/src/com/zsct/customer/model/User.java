/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.model;

public class User {
	private String id;
	private String name;
	private String phone;
	private String sex;
	private String password;
	private String create_time;
	private String image_thumb;
	private String image;
	public User(String id, String name, String phone, String sex,
			String password, String create_time, String image_thumb,
			String image) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.sex = sex;
		this.password = password;
		this.create_time = create_time;
		this.image_thumb = image_thumb;
		this.image = image;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getImage_thumb() {
		return image_thumb;
	}
	public void setImage_thumb(String image_thumb) {
		this.image_thumb = image_thumb;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone
				+ ", sex=" + sex + ", password=" + password + ", create_time="
				+ create_time + ", image_thumb=" + image_thumb + ", image="
				+ image + "]";
	}
	
}

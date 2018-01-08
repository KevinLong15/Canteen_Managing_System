/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.model;

import java.io.Serializable;

public class Comment implements Serializable {
	private String id;
	private String order_id;
	private String user_id;
	private String content;
	private String sorce;
	private String store_id;
	private String comment_time;
	private String people;
	private String is_room;
	private String order_time;
	private String user_name;
	private String phone;
	private String order_type;
	private String total_price;

	public Comment(String id, String order_id, String user_id, String content,
			String sorce, String store_id, String comment_time, String people,
			String is_room, String order_time, String user_name, String phone,
			String order_type, String total_price) {
		super();
		this.id = id;
		this.order_id = order_id;
		this.user_id = user_id;
		this.content = content;
		this.sorce = sorce;
		this.store_id = store_id;
		this.comment_time = comment_time;
		this.people = people;
		this.is_room = is_room;
		this.order_time = order_time;
		this.user_name = user_name;
		this.phone = phone;
		this.order_type = order_type;
		this.total_price = total_price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSorce() {
		return sorce;
	}

	public void setSorce(String sorce) {
		this.sorce = sorce;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getComment_time() {
		return comment_time;
	}

	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getIs_room() {
		return is_room;
	}

	public void setIs_room(String is_room) {
		this.is_room = is_room;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", order_id=" + order_id + ", user_id="
				+ user_id + ", content=" + content + ", sorce=" + sorce
				+ ", store_id=" + store_id + ", comment_time=" + comment_time
				+ ", people=" + people + ", is_room=" + is_room
				+ ", order_time=" + order_time + ", user_name=" + user_name
				+ ", phone=" + phone + ", order_type=" + order_type
				+ ", total_price=" + total_price + "]";
	}

}

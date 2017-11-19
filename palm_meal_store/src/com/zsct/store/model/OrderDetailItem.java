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

public class OrderDetailItem implements Serializable {
	private String order_id;
	private String count;
	private String dishes_id;
	private String price;
	private String dishes_name;

	public OrderDetailItem(String order_id, String count, String dishes_id,
			String price, String dishes_name) {
		super();
		this.order_id = order_id;
		this.count = count;
		this.dishes_id = dishes_id;
		this.price = price;
		this.dishes_name = dishes_name;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getDishes_id() {
		return dishes_id;
	}

	public void setDishes_id(String dishes_id) {
		this.dishes_id = dishes_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDishes_name() {
		return dishes_name;
	}

	public void setDishes_name(String dishes_name) {
		this.dishes_name = dishes_name;
	}

	@Override
	public String toString() {
		return "OrderDetailItem [order_id=" + order_id + ", count=" + count
				+ ", dishes_id=" + dishes_id + ", price=" + price
				+ ", dishes_name=" + dishes_name + "]";
	}
}

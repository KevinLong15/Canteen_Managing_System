/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * json解析的的工具类
 * 
 * @author 黄家强
 */
public class JsonUtil {
	// 传入的参数
	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String PASSWORD = "password";
	public static final String STORE_ID = "store_id";
	public static final String TYPE = "type";
	public static final String STATUS = "status";
	public static final String PAY_TYPE = "pay_type";
	public static final String COMMENT_ID = "comment_id";
	public static final String ADMIN_ID = "admin_id";
	public static final String ADMIN_USERNAME = "admin_Username";
	public static final String CONTENT = "content";
	public static final String REG_ID = "reg_id";
	public static final String ALIAS = "alias";
	// 传出的参数
	public static final String CODE = "code";
	public static final String MSG = "msg";
	public static final String ID = "id";
	public static final String ORDER_ID = "order_id";
	public static final String USER_ID = "user_id";
	public static final String CREATE_TIME = "create_time";
	public static final String PEOPLE = "people";
	public static final String IS_ROOM = "is_room";
	public static final String ORDER_TIME = "order_time";
	public static final String CHECKGROUP = "checkgroup";
	public static final String GROUP_COUNT = "group_count";
	public static final String GROUP_ID = "group_id";
	public static final String USERNAME = "userName";
	public static final String TOTAL_PRICE = "total_price";
	public static final String PAY_TIME = "pay_time";
	public static final String ORDER = "order";
	public static final String DETAIL = "detail";
	public static final String COUNT = "count";
	public static final String DISHES_ID = "dishes_id";
	public static final String PRICE = "price";
	public static final String DISHES_NAME = "dishes_name";
	public static final String CHECK_CODE = "check_code";
	public static final String SORCE = "sorce";
	public static final String COMMEMT_TIME = "comment_time";
	public static final String SCHEDULE_MONEY = "schedule_money";

	/**
	 * 从json对象中得到，对应的String数据
	 * 
	 * @param json
	 *            json对象
	 * @param keyname
	 *            对应的键名
	 * @return 对应的String类型的键值
	 */
	public static String getStr(JSONObject json, String keyname) {
		String result = "";
		try {
			result = json.getString(keyname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 从json对象中得到，对应的integer数据
	 * 
	 * @param json
	 *            json对象
	 * @param keyname
	 *            对应的键名
	 * @return 对应的integer类型的键值
	 */
	public static int getInt(JSONObject json, String keyname) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			result = json.getInt(keyname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 从json对象中得到，对应的long数据
	 * 
	 * @param json
	 *            json对象
	 * @param keyname
	 *            对应的键名
	 * @return 对应的long类型的键值
	 */
	public static Long getLong(JSONObject json, String keyname) {
		// TODO Auto-generated method stub
		Long result = null;
		try {
			result = json.getLong(keyname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}

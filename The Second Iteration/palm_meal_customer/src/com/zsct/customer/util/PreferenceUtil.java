/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * 记录用户名，密码之类的首选项
 * 
 */
public class PreferenceUtil {
	private static PreferenceUtil preference = null;
	private SharedPreferences sharedPreference;
	private String packageName = "";
	public static final String USERNAME = "username"; // 登录名
	public static final String PASSWORD = "password"; // 密码
	public static final String REMINDWORD = "remindword"; // 是否保留密码
	public static final String AUTOLOGIN = "autologin";
	public static final String TIMES = "times";
	public static final String UID = "uid";
	public static final String CITY = "city";
	public static final String CITYID = "cityid";
	public static final String LON = "lon";
	public static final String LAT = "lat";
	public static final String SHIBI = "shibi";
	public static final String PHONE = "phone";

	public static synchronized PreferenceUtil getInstance(Context context) {
		if (preference == null)
			preference = new PreferenceUtil(context);
		return preference;
	}

	public PreferenceUtil(Context context) {
		packageName = context.getPackageName() + "_preferences";
		sharedPreference = context.getSharedPreferences(packageName,
				Context.MODE_PRIVATE);
	}

	public String getUid() {
		String value = sharedPreference.getString(UID, "");
		return value;
	}
	public void setUid(String value) {
		Editor edit = sharedPreference.edit();
		edit.putString(UID, value);
		edit.commit();
	}
	public String getString(String name,String defValue) {
		String value = sharedPreference.getString(name, defValue);
		return value;
	}
	public void setString(String name,String value) {
		Editor edit = sharedPreference.edit();
		edit.putString(name, value);
		edit.commit();
	}
	public int getInt(String name,int defValue) {
		int value = sharedPreference.getInt(name,defValue );
		return value;
	}
	public void setInt(String name,int value) {
		Editor edit = sharedPreference.edit();
		edit.putInt(name, value);
		edit.commit();
	}
	public float getFloat(String name,float defValue) {
		float value = sharedPreference.getFloat(name, defValue);
		return value;
	}
	public void setFloat(String name,float value) {
		Editor edit = sharedPreference.edit();
		edit.putFloat(name, value);
		edit.commit();
	}

}

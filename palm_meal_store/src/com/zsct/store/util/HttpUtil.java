/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * 访问网络的工具类
 * 
 * @author 黄家强
 */
public class HttpUtil {
	public static String IP = "114.215.180.179:9000";
	public static String SERVER = "http://" + IP + "/server/Business/";
	public static String URL_LOGIN = SERVER + "login";
	/**
	 * 预定，点餐订单
	 */
	public static String URL_GETORDER = SERVER + "getOrderByStoreIdAndType";
	/**
	 * 团购
	 */
	public static String URL_GETGROUPORDER = SERVER
			+ "getGroupOrderByStoreIdAndStatus";
	/**
	 * 更新订单状态
	 */
	// public static String URL_UPDATEORDERSTATUS = "http://" + IP +
	// "/server/Order/"
	// + "updateOrderStatus";
	public static String URL_UPDATEORDERSTATUSBYADMIN = SERVER
			+ "updateOrderStatusByAdmin";
	public static String URL_MEMBERLISTBYSTORE = SERVER + "memberListByStore";
	public static String URL_GETADMINLOGBYSTOREID = SERVER
			+ "GetAdminLogByStoreId";
	/**
	 * 评价管理
	 */
//	public static String URL_GETCOMMENT = SERVER + "getCommentByStoreId";
	public static String URL_STORECOMMENT = "http://"+IP+"/server/User/storeComment";
	/**
	 * 商家举报
	 */
	public static String URL_REPORT = SERVER + "report";
	/**
	 * 订单的菜品详情
	 */
	public static String URL_ORDERDETAIL = "http://" + IP
			+ "/server/Store/orderDetail";

	public static String URL_GETGROUPBYORDERID = "http://" + IP
			+ "/server/Order/getGroupByOrderId";
	public static String URL_CHECKGROUP = SERVER
			+ "checkGroupByOrderIdAndCheckCode";
	public static String URL_GETREGIDANDUSERID = "http://" + IP
			+ "/server/Push/getRegIdAndAdmin";

	/**
	 * 用post方式来访问网络
	 * 
	 * @param url
	 *            要访问的网址
	 * @param nameValuePairs
	 *            需要的参数
	 * @return 网络返回的结果数据
	 */
	public static String post(String url, NameValuePair... nameValuePairs) {
		HttpClient httpClient = new DefaultHttpClient();
		String msg = "";
		HttpPost post = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (int i = 0; i < nameValuePairs.length; i++) {
			params.add(nameValuePairs[i]);
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				msg = EntityUtils.toString(response.getEntity());
			} else {
				Log.e("hjq", "网络请求失败");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}

	/**
	 * 用get方式来访问网络
	 * 
	 * @param url
	 *            要访问的网址
	 * @return 网络返回的结果数据
	 */
	public static String get(String url) {
		HttpClient httpClient = new DefaultHttpClient();

		String msg = "";
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {
			response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (entity != null) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(entity.getContent()));
					String line = null;
					while ((line = br.readLine()) != null) {
						msg += line;
					}
				}
			} else {
				Log.e("hjq", "网络请求失败");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	private static String getCookie(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		String cookie = cookieManager.getCookie("cookie");
		Log.e("hjq", "getCookie=" + cookie);
		return cookie;
	}

	public static String getURlStr(String url, NameValuePair... namevalues) {
		StringBuilder result = new StringBuilder(url + "?");
		for (int i = 0; i < namevalues.length; i++) {
			if (i != 0) {
				result.append("&" + namevalues[i].getName() + "="
						+ namevalues[i].getValue());
			} else {
				result.append(namevalues[i].getName() + "="
						+ namevalues[i].getValue());
			}

		}
		return result.toString();

	}
}

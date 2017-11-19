/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class CommonUtil {
	public static String DouToStr1(double dd) {
		DecimalFormat df = new DecimalFormat(".0");
		return df.format(dd);
	}

	public static String DouToStr2(double dd) {
		DecimalFormat df = new DecimalFormat(".00");
		return df.format(dd);
	}

	public static int getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return w;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double EARTH_RADIUS = 6378.137;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static String getTimestr(String create_time) {
		if (create_time.length() < 17) {
			return create_time;
		}
		String daString = create_time.substring(0, 17);
		int weekid = Integer.parseInt(create_time.substring(create_time
				.length() - 1));
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		return daString.substring(0, 11) + weekDays[weekid] + " "
				+ daString.substring(11, 17);
	}

	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		String version = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	public static String getOrderStatus(String status) {
		// 获取packagemanager的实例
		if (status.trim().equals("submit")) {
			return "已提交";
		} else if (status.trim().equals("ensure")) {
			return "已确认";
		} else if (status.trim().equals("pay")) {
			return "已支付";
		} else if (status.trim().equals("cancel")) {
			return "已取消";
		} else if (status.trim().equals("finish")) {
			return "已完成";
		} else if (status.trim().equals("drawback")) {
			return "退款中";
		} else if (status.trim().equals("payback")) {
			return "退款完成";
		}

		return "";
	}

	public static void saveMyBitmap(Bitmap mBitmap, String bitName)
			throws IOException {
		File f = new File(Environment.getExternalStorageDirectory()
				+ "/peal_meal/" + bitName + ".png");
		File dirFile = new File(Environment.getExternalStorageDirectory()
				+ "/peal_meal/");
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap getImagePath(String bitName) {
		String path = "";
		File f = new File(Environment.getExternalStorageDirectory()
				+ "/peal_meal/" + bitName + ".png");
		try {
			if (f.exists()) {
				path = f.getAbsolutePath();
				FileInputStream fis = new FileInputStream(path);
				return BitmapFactory.decodeStream(fis);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

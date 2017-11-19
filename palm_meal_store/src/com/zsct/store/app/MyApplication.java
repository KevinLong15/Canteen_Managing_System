/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.app;

import java.util.LinkedList;

import android.app.Activity;
import android.app.Application;

import com.zsct.store.util.ImageLoaderUtil;

public class MyApplication extends Application {

	public int time = 0;
	public int type = 1;
	private LinkedList<Activity> activityList = new LinkedList<Activity>();
	private static MyApplication instance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		ImageLoaderUtil.stopload(instance);
		super.onTerminate();
	}

}

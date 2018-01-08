/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.fdcz.zsct.R;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class FirstActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_first);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Log.i("hjq", "postDelayed");
				finish();
				startActivity(new Intent(FirstActivity.this, MainActivity.class));
			}
		}, 3000);
	ThreadPoolManager.getInstance().addTask(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			HttpUtil.get(HttpUtil.URL_INITAPP);
		}
	});
	}

	
}

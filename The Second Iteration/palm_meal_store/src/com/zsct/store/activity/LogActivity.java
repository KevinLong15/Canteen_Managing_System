/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.activity;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.zsct.store.R;
import com.zsct.store.pulltorefresh.PullToRefreshLayout;
import com.zsct.store.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.zsct.store.pulltorefresh.PullableListView;
import com.zsct.store.util.HttpUtil;
import com.zsct.store.util.JsonUtil;
import com.zsct.store.util.PreferenceUtil;
import com.zsct.store.util.ThreadPoolManager;

public class LogActivity extends BaseActivity implements OnClickListener,
		OnRefreshListener {

	private PullableListView listView;
	private ArrayList<String> strArr = new ArrayList<String>();
	private ArrayAdapter<String> mAdapter;
	private PullableListView orderList;
	private PullToRefreshLayout pullToRefreshLayout;
	private Runnable logRun;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			closeLoadingDialog();
			Log.e("hjq", result);
			try {
				JSONArray array = new JSONArray(result);
				strArr.clear();
				for (int i = 0; i < array.length(); i++) {
					String admin_username = array.getJSONObject(i).getString(
							JsonUtil.ADMIN_USERNAME);
					String order_id = array.getJSONObject(i).getString(
							JsonUtil.ORDER_ID);
					String status = array.getJSONObject(i).getString(
							JsonUtil.STATUS);
					String create_time = array.getJSONObject(i).getString(
							JsonUtil.CREATE_TIME);
					strArr.add("管理员" + admin_username + "于" + create_time + "修改了"
							+ order_id + "的状态为" + status);
				}
				mAdapter = new ArrayAdapter<String>(LogActivity.this,
						android.R.layout.simple_list_item_1, strArr);
				listView.setAdapter(mAdapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (pullToRefreshLayout != null) {
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		listView = (PullableListView) findViewById(R.id.orderlist);
		((ImageView) findViewById(R.id.back)).setOnClickListener(this);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
				.setOnRefreshListener(this);
		logRun = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("hjq", HttpUtil.getURlStr(
						HttpUtil.URL_GETADMINLOGBYSTOREID,
						new BasicNameValuePair(JsonUtil.STORE_ID,
								PreferenceUtil.getInstance(LogActivity.this)
										.getSid())));
				String result = HttpUtil.post(
						HttpUtil.URL_GETADMINLOGBYSTOREID,
						new BasicNameValuePair(JsonUtil.STORE_ID,
								PreferenceUtil.getInstance(LogActivity.this)
										.getSid()));
				Message msg = new Message();
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		ThreadPoolManager.getInstance().addTask(logRun);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		}

	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		this.pullToRefreshLayout = pullToRefreshLayout;
		ThreadPoolManager.getInstance().addTask(logRun);
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub

	}
}

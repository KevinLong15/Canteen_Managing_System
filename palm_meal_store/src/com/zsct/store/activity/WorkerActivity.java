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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsct.store.R;
import com.zsct.store.pulltorefresh.PullToRefreshLayout;
import com.zsct.store.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.zsct.store.pulltorefresh.PullableListView;
import com.zsct.store.util.HttpUtil;
import com.zsct.store.util.JsonUtil;
import com.zsct.store.util.PreferenceUtil;
import com.zsct.store.util.ThreadPoolManager;

public class WorkerActivity extends BaseActivity implements OnClickListener,
		OnRefreshListener {

	private ArrayList<User> userArr = new ArrayList<User>();
	private UserAdapter mAdapter;
	private PullableListView listView;
	private PullToRefreshLayout pullToRefreshLayout;
	private Runnable workRun;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			closeLoadingDialog();
			Log.e("hjq", result);
			try {
				JSONArray array = new JSONArray(result);
				userArr.clear();
				for (int i = 0; i < array.length(); i++) {
					String username = array.getJSONObject(i).getString(
							"username");
					String phone = array.getJSONObject(i).getString(
							JsonUtil.PHONE);
					userArr.add(new User(username, phone));
				}
				mAdapter = new UserAdapter();
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
		setContentView(R.layout.activity_worker);
		listView = (PullableListView) findViewById(R.id.orderlist);
		((ImageView) findViewById(R.id.back)).setOnClickListener(this);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
				.setOnRefreshListener(this);
		workRun = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				String result = HttpUtil.post(HttpUtil.URL_MEMBERLISTBYSTORE,
						new BasicNameValuePair(JsonUtil.STORE_ID,
								PreferenceUtil.getInstance(WorkerActivity.this)
										.getSid()));
				Message msg = new Message();
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		ThreadPoolManager.getInstance().addTask(workRun);
		showLoadingDialog();
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

	class UserAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return userArr.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return userArr.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = getLayoutInflater().inflate(R.layout.user_item, null);
			TextView usernametv = (TextView) convertView
					.findViewById(R.id.username);
			TextView phonetv = (TextView) convertView.findViewById(R.id.phone);
			usernametv.setText(userArr.get(position).getUsername());
			phonetv.setText(userArr.get(position).getPhone());
			return convertView;
		}

	}

	class User {
		private String username;
		private String phone;

		public User(String username, String phone) {
			super();
			this.username = username;
			this.phone = phone;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		this.pullToRefreshLayout = pullToRefreshLayout;
		ThreadPoolManager.getInstance().addTask(workRun);
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub

	}
}

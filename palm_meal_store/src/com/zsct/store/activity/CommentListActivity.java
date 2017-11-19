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
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zsct.store.R;
import com.zsct.store.adapter.CommentAdapter;
import com.zsct.store.model.Comment;
import com.zsct.store.pulltorefresh.PullToRefreshLayout;
import com.zsct.store.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.zsct.store.pulltorefresh.PullableListView;
import com.zsct.store.util.HttpUtil;
import com.zsct.store.util.JsonUtil;
import com.zsct.store.util.PreferenceUtil;
import com.zsct.store.util.ThreadPoolManager;

public class CommentListActivity extends BaseActivity implements
		OnRefreshListener {
	private ArrayList<Comment> listdata = new ArrayList<Comment>();
	private CommentAdapter mAdapter;
	private String uid = "";
	private PullableListView orderList;
	private PullToRefreshLayout pullToRefreshLayout;
	private Runnable commentRun;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			Log.e("hjq", result);
			if (result.trim().equals("null")) {
				showShortToast("没有发现相关数据");
				return;
			} else {
				try {
					listdata = getListData(result);
					mAdapter = new CommentAdapter(CommentListActivity.this,
							listdata);
					orderList.setAdapter(mAdapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pullToRefreshLayout != null) {
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commentlist);
		orderList = (PullableListView) findViewById(R.id.orderlist);
		findViewById(R.id.back).setOnClickListener(this);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
				.setOnRefreshListener(this);
		orderList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(CommentListActivity.this,
						CommentDetailActivity.class);
				mIntent.putExtra("comment", listdata.get(position));
				startActivity(mIntent);
			}
		});
		initRun();
	}

	protected ArrayList<Comment> getListData(String result)
			throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<Comment> comments = new ArrayList<Comment>();
		JSONArray array = new JSONArray(result);
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			String id = object.getString(JsonUtil.ID);
			String order_id = object.getString(JsonUtil.ORDER_ID);
			String user_id = object.getString(JsonUtil.USER_ID);
			String content = object.getString(JsonUtil.CONTENT);
			String sorce = object.getString(JsonUtil.SORCE);
			String store_id = object.getString(JsonUtil.STORE_ID);
			String comment_time = object.getString(JsonUtil.COMMEMT_TIME);
			String people = object.getString(JsonUtil.PEOPLE);
			String is_room = object.getString(JsonUtil.IS_ROOM);
			String order_time = object.getString(JsonUtil.ORDER_TIME);
			String user_name = object.getString("user_name");
			String phone = object.getString("phone");
			String order_type = object.getString("order_type");
			String total_price = object.getString("total_price");
			comments.add(new Comment(id, order_id, user_id, content, sorce,
					store_id, comment_time, people, is_room, order_time,
					user_name, phone, order_type, total_price));
		}

		return comments;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	private void initRun() {
		// TODO Auto-generated method stub
		uid = PreferenceUtil.getInstance(CommentListActivity.this).getSid();
		commentRun = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("hjq", HttpUtil.getURlStr(HttpUtil.URL_STORECOMMENT,
						new BasicNameValuePair(JsonUtil.STORE_ID, uid)));
				String result = HttpUtil.post(HttpUtil.URL_STORECOMMENT,
						new BasicNameValuePair(JsonUtil.STORE_ID, uid));
				Message msg = new Message();
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		ThreadPoolManager.getInstance().addTask(commentRun);
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		this.pullToRefreshLayout = pullToRefreshLayout;
		ThreadPoolManager.getInstance().addTask(commentRun);
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub

	}
}

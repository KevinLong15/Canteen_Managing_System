/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.fdcz.zsct.R;
import com.zsct.customer.adapter.CommentAdapter;
import com.zsct.customer.model.Comment;
import com.zsct.customer.model.Shop;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class ShopCommentActivity extends BaseActivity {
	private Shop mShop;
	private ListView commentlist;
	private CommentAdapter mAdapter;
	private ArrayList<Comment> listData = new ArrayList<Comment>();
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			closeLoadingDialog();
			Log.e("hjq", result);
			if (result.trim().equals("null")) {
				showShortToast("没有发现评价数据");
				return;
			}
			try {
				JSONArray array = new JSONArray(result);
				for (int i = 0; i < array.length(); i++) {
					JSONObject json = array.getJSONObject(i);
					String id = json.getString(JsonUtil.ID);
					String order_id = json.getString(JsonUtil.ORDER_ID);
					String user_id = json.getString(JsonUtil.USER_ID);
					String content = json.getString(JsonUtil.CONTENT);
					String sorce = json.getString(JsonUtil.SORCE);
					String store_id = json.getString(JsonUtil.STORE_ID);
					String comment_time = json.getString(JsonUtil.COMMENT_TIME);
					String people = json.getString(JsonUtil.PEOPLE);
					String is_room = json.getString(JsonUtil.IS_ROOM);
					String order_time = json.getString(JsonUtil.ORDER_TIME);
					;
					String order_type = json.getString(JsonUtil.ORDER_TYPE);
					;
					String phone = json.getString(JsonUtil.PHONE);
					String status = json.getString(JsonUtil.STATUS);
					String total_price = json.getString(JsonUtil.TOTAL_PRICE);
					String user_name = json.getString(JsonUtil.USER_NAME);
					listData.add(new Comment(id, order_id, user_id, content,
							sorce, store_id, comment_time, people, is_room,
							order_time, order_type, phone, status, total_price,
							user_name));
				}
				mAdapter = new CommentAdapter(ShopCommentActivity.this,
						listData);
				commentlist.setAdapter(mAdapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_comment);
		mShop = (Shop) getIntent().getSerializableExtra("object");
		findViewById(R.id.back).setOnClickListener(this);
		commentlist = (ListView) findViewById(R.id.commentlist);
		showLoadingDialog();
		ThreadPoolManager.getInstance().addTask(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil
						.post(HttpUtil.URL_STORECOMMENT,
								new BasicNameValuePair(JsonUtil.STORE_ID, mShop
										.getId()));
				Message msg = new Message();
				msg.obj = result;
				mHandler.sendMessage(msg);

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;

		default:
			break;
		}
	}
}

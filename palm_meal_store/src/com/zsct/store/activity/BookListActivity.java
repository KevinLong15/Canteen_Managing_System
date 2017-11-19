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
import android.widget.ImageView;
import android.widget.TextView;

import com.zsct.store.R;
import com.zsct.store.adapter.OrderListAdapter;
import com.zsct.store.model.Order;
import com.zsct.store.pulltorefresh.PullToRefreshLayout;
import com.zsct.store.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.zsct.store.pulltorefresh.PullableListView;
import com.zsct.store.util.HttpUtil;
import com.zsct.store.util.JsonUtil;
import com.zsct.store.util.PreferenceUtil;
import com.zsct.store.util.ThreadPoolManager;

public class BookListActivity extends BaseActivity implements OnRefreshListener {
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private ImageView trian1;
	private ImageView trian2;
	private ImageView trian3;
	private ImageView trian4;
	private PullableListView orderList;
	private PullToRefreshLayout pullToRefreshLayout;
	private OrderListAdapter adapter;
	private ArrayList<Order> ensuredata = new ArrayList<Order>();
	private ArrayList<Order> unpaydata = new ArrayList<Order>();
	private ArrayList<Order> havepaydata = new ArrayList<Order>();
	private ArrayList<Order> donedata = new ArrayList<Order>();
	private ArrayList<Order> orderlistdata = new ArrayList<Order>();
	private int sectionindex = 0;
	private static final int ensure_what = 1;
	private static final int unpay_what = 2;
	private static final int havepay_what = 3;
	private static final int done_what = 4;
	private String uid = "";
	private Runnable ensureRun, unpayRun, havepayRun, doneRun;
	private final String type = "schedule";
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			Log.e("hjq", result);
			switch (msg.what) {
			case ensure_what:
				try {
					ensuredata = getListData(result);
					if (sectionindex == 0) {
						selectSecCheck(sectionindex);
						orderlistdata = ensuredata;
						Log.e("hjq", "size=" + orderlistdata.size());
						adapter = new OrderListAdapter(BookListActivity.this,
								orderlistdata);
						adapter.notifyDataSetChanged();
						orderList.setAdapter(adapter);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case unpay_what:
				try {
					unpaydata = getListData(result);
					if (sectionindex == 1) {
						selectSecCheck(sectionindex);
						orderlistdata = unpaydata;
						Log.e("hjq", "size=" + orderlistdata.size());
						adapter = new OrderListAdapter(BookListActivity.this,
								orderlistdata);
						adapter.notifyDataSetChanged();
						orderList.setAdapter(adapter);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case havepay_what:
				try {
					havepaydata = getListData(result);
					if (sectionindex == 2) {
						selectSecCheck(sectionindex);
						orderlistdata = havepaydata;
						Log.e("hjq", "size=" + orderlistdata.size());
						adapter = new OrderListAdapter(BookListActivity.this,
								orderlistdata);
						adapter.notifyDataSetChanged();
						orderList.setAdapter(adapter);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case done_what:
				try {
					donedata = getListData(result);
					if (sectionindex == 3) {
						selectSecCheck(sectionindex);
						orderlistdata = donedata;
						Log.e("hjq", "size=" + orderlistdata.size());
						adapter = new OrderListAdapter(BookListActivity.this,
								orderlistdata);
						adapter.notifyDataSetChanged();
						orderList.setAdapter(adapter);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
			selectSecCheck(sectionindex);

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booklist);
		orderList = (PullableListView) findViewById(R.id.orderlist);
		text1 = (TextView) findViewById(R.id.section1);
		text2 = (TextView) findViewById(R.id.section2);
		text3 = (TextView) findViewById(R.id.section3);
		text4 = (TextView) findViewById(R.id.section4);
		trian1 = (ImageView) findViewById(R.id.mark1);
		trian2 = (ImageView) findViewById(R.id.mark2);
		trian3 = (ImageView) findViewById(R.id.mark3);
		trian4 = (ImageView) findViewById(R.id.mark4);
		findViewById(R.id.back).setOnClickListener(this);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
				.setOnRefreshListener(this);
		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		text3.setOnClickListener(this);
		text4.setOnClickListener(this);
		orderList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(BookListActivity.this,
						BookDetailActivity.class);
				mIntent.putExtra("order", orderlistdata.get(position));
				startActivity(mIntent);
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initRun();
		selectSecCheck(sectionindex);
	}

	protected ArrayList<Order> getListData(String result) throws JSONException {
		// TODO Auto-generated method stub
		ArrayList<Order> orders = new ArrayList<Order>();
		if (!result.contains(JsonUtil.CODE)) {
			JSONArray array = new JSONArray(result);

			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				String id = object.getString(JsonUtil.ID);
				String order_id = object.getString(JsonUtil.ORDER_ID);
				String user_id = object.getString(JsonUtil.USER_ID);
				String phone = object.getString(JsonUtil.PHONE);
				String store_id = object.getString(JsonUtil.STORE_ID);
				String create_time = object.getString(JsonUtil.CREATE_TIME);
				String people = object.getString(JsonUtil.PEOPLE);
				String is_room = object.getString(JsonUtil.IS_ROOM);
				String order_time = object.getString(JsonUtil.ORDER_TIME);
				String type = object.getString(JsonUtil.TYPE);
				String status = object.getString(JsonUtil.STATUS);
				String checkgroup = object.getString(JsonUtil.CHECKGROUP);
				String group_count = object.getString(JsonUtil.GROUP_COUNT);
				String group_id = object.getString(JsonUtil.GROUP_ID);
				String userName = object.getString(JsonUtil.USERNAME);
				String total_price = object.getString(JsonUtil.TOTAL_PRICE);
				String pay_type = object.getString(JsonUtil.PAY_TYPE);
				String pay_time = object.getString(JsonUtil.PAY_TIME);
				String schedule_money = object.getString(JsonUtil.SCHEDULE_MONEY);
				orders.add(new Order(id, order_id, user_id, phone, store_id,
						create_time, people, is_room, order_time, type, status,
						checkgroup, group_count, group_id, userName,
						total_price, pay_type, pay_time,schedule_money));
			}
		}
		return orders;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.section1:
			sectionindex = 0;
			selectSecCheck(sectionindex);
			orderlistdata = ensuredata;
			adapter = new OrderListAdapter(this, orderlistdata);
			adapter.notifyDataSetChanged();
			orderList.setAdapter(adapter);
			break;
		case R.id.section2:
			sectionindex = 1;
			selectSecCheck(sectionindex);
			orderlistdata = unpaydata;
			adapter = new OrderListAdapter(this, orderlistdata);
			adapter.notifyDataSetChanged();
			orderList.setAdapter(adapter);
			break;
		case R.id.section3:
			sectionindex = 2;
			selectSecCheck(sectionindex);
			orderlistdata = havepaydata;
			adapter = new OrderListAdapter(this, orderlistdata);
			adapter.notifyDataSetChanged();
			orderList.setAdapter(adapter);
			break;
		case R.id.section4:
			sectionindex = 3;
			selectSecCheck(sectionindex);
			orderlistdata = donedata;
			adapter = new OrderListAdapter(this, orderlistdata);
			adapter.notifyDataSetChanged();
			orderList.setAdapter(adapter);
			break;

		default:
			break;
		}
	}

	protected void selectSecCheck(int index) {
		// TODO Auto-generated method stub
		text1.setTextColor(0xff696969);
		text2.setTextColor(0xff696969);
		text3.setTextColor(0xff696969);
		text4.setTextColor(0xff696969);
		trian1.setImageResource(R.drawable.section_bg_normal);
		trian2.setImageResource(R.drawable.section_bg_normal);
		trian3.setImageResource(R.drawable.section_bg_normal);
		trian4.setImageResource(R.drawable.section_bg_normal);
		switch (index) {
		case 0:
			text1.setTextColor(0xff14a19c);
			trian1.setImageResource(R.drawable.section_bg_selected);
			if (pullToRefreshLayout != null) {
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
			break;
		case 1:
			text2.setTextColor(0xff14a19c);
			trian2.setImageResource(R.drawable.section_bg_selected);
			if (pullToRefreshLayout != null) {
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
			break;
		case 2:
			text3.setTextColor(0xff14a19c);
			trian3.setImageResource(R.drawable.section_bg_selected);
			if (pullToRefreshLayout != null) {
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
			break;
		case 3:
			text4.setTextColor(0xff14a19c);
			trian4.setImageResource(R.drawable.section_bg_selected);
			if (pullToRefreshLayout != null) {
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
			break;
		}
	}

	private void initRun() {
		// TODO Auto-generated method stub
		uid = PreferenceUtil.getInstance(BookListActivity.this).getSid();
		ensureRun = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil.post(HttpUtil.URL_GETORDER,
						new BasicNameValuePair(JsonUtil.STORE_ID, uid),
						new BasicNameValuePair(JsonUtil.TYPE, type),
						new BasicNameValuePair(JsonUtil.STATUS, "submit"));
				Message msg = new Message();
				msg.what = ensure_what;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		unpayRun = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil.post(HttpUtil.URL_GETORDER,
						new BasicNameValuePair(JsonUtil.STORE_ID, uid),
						new BasicNameValuePair(JsonUtil.TYPE, type),
						new BasicNameValuePair(JsonUtil.STATUS, "ensure"));
				Message msg = new Message();
				msg.what = unpay_what;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		havepayRun = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil.post(HttpUtil.URL_GETORDER,
						new BasicNameValuePair(JsonUtil.STORE_ID, uid),
						new BasicNameValuePair(JsonUtil.TYPE, type),
						new BasicNameValuePair(JsonUtil.STATUS, "pay"));
				Message msg = new Message();
				msg.what = havepay_what;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		doneRun = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil.post(HttpUtil.URL_GETORDER,
						new BasicNameValuePair(JsonUtil.STORE_ID, uid),
						new BasicNameValuePair(JsonUtil.TYPE, type),
						new BasicNameValuePair(JsonUtil.STATUS, "consumption"));
				Message msg = new Message();
				msg.what = done_what;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		ThreadPoolManager.getInstance().addTask(ensureRun);
		ThreadPoolManager.getInstance().addTask(unpayRun);
		ThreadPoolManager.getInstance().addTask(havepayRun);
		ThreadPoolManager.getInstance().addTask(doneRun);
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		this.pullToRefreshLayout = pullToRefreshLayout;
		ThreadPoolManager.getInstance().addTask(ensureRun);
		ThreadPoolManager.getInstance().addTask(unpayRun);
		ThreadPoolManager.getInstance().addTask(havepayRun);
		ThreadPoolManager.getInstance().addTask(doneRun);
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub

	}
}

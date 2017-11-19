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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsct.store.R;
import com.zsct.store.model.Menu;
import com.zsct.store.model.Order;
import com.zsct.store.pulltorefresh.PullToRefreshLayout;
import com.zsct.store.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.zsct.store.util.CommonUtil;
import com.zsct.store.util.HttpUtil;
import com.zsct.store.util.JsonUtil;
import com.zsct.store.util.ThreadPoolManager;

public class DealDetailActivity extends BaseActivity implements
		OnClickListener, OnRefreshListener {

	private TextView orderId;
	private TextView orderTime;
	private TextView orderName;
	private TextView orderPhone;
	private TextView orderPeople;
	private TextView orderIsRoom;
	private Button sureBtn;
	private Order mOrder;
	private ArrayList<Menu> menulistdata = new ArrayList<Menu>();
	private final int orderdetail_what = 1;
	private final int sure_what = 2;
	private PullToRefreshLayout pullToRefreshLayout;
	private Runnable detailRun;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			Log.e("hjq", result);
			switch (msg.what) {
			case orderdetail_what:
				try {
					// menulistdata = getOrderData(result);
					JSONObject json = new JSONArray(result).getJSONObject(0);
					String title = json.getString("title");
					String group_price = json.getString("group_price");
					String userName = json.getString("GroupUserName");
					String phone = json.getString("phone");
					orderName.setText(title);
					orderPhone.setText(getString(R.string.rmb) + group_price);
					orderPeople.setText(userName);
					orderIsRoom.setText(phone);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (pullToRefreshLayout != null) {
					pullToRefreshLayout
							.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
				break;
			case sure_what:
				try {
					JSONObject object = new JSONObject(result);
					if (object.getInt(JsonUtil.CODE) == 1) {
						sureBtn.setVisibility(View.GONE);
						showLongToast("操作成功");
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dealdetail);
		mOrder = (Order) getIntent().getSerializableExtra("order");
		orderId = (TextView) findViewById(R.id.order_id);
		orderTime = (TextView) findViewById(R.id.create_time);
		orderName = (TextView) findViewById(R.id.person);
		orderPhone = (TextView) findViewById(R.id.phone);
		orderPeople = (TextView) findViewById(R.id.people);
		orderIsRoom = (TextView) findViewById(R.id.isroom);
		sureBtn = (Button) findViewById(R.id.sure);
		orderId.setText(mOrder.getOrder_id());
		orderTime.setText(CommonUtil.getTimestr(mOrder.getCreate_time()));
		// orderName.setText(mOrder.getUserName());
		// orderPhone.setText(mOrder.getPhone());
		// orderPeople.setText(mOrder.getPeople());
		// orderIsRoom.setText(mOrder.getIs_room());
		sureBtn.setOnClickListener(this);
		((ImageView) findViewById(R.id.back)).setOnClickListener(this);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
				.setOnRefreshListener(this);
		if (mOrder.getStatus().equals("pay")) {
			sureBtn.setVisibility(View.VISIBLE);
		} else if (mOrder.getStatus().equals("finish")) {
			sureBtn.setVisibility(View.GONE);
		}
		detailRun = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("hjq", HttpUtil.getURlStr(
						HttpUtil.URL_GETGROUPBYORDERID,
						new BasicNameValuePair(JsonUtil.ORDER_ID, mOrder
								.getId())));
				String result = HttpUtil.post(
						HttpUtil.URL_GETGROUPBYORDERID,
						new BasicNameValuePair(JsonUtil.ORDER_ID, mOrder
								.getOrder_id()));
				Message msg = new Message();
				msg.what = orderdetail_what;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		ThreadPoolManager.getInstance().addTask(detailRun);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.sure:
			Intent mIntent = new Intent(this, DealSignActivity.class);
			mIntent.putExtra("order", mOrder);
			startActivity(mIntent);
			break;

		default:
			break;
		}
	}

	// protected ArrayList<Menu> getOrderData(String result) throws
	// JSONException {
	// // TODO Auto-generated method stub
	// ArrayList<Menu> menus = new ArrayList<Menu>();
	// JSONArray array = new JSONArray(result);
	// for (int i = 0; i < array.length(); i++) {
	// String order_id = array.getJSONObject(i).getString(
	// JsonUtil.ORDER_ID);
	// String count = array.getJSONObject(i).getString(JsonUtil.COUNT);
	// String dishes_id = array.getJSONObject(i).getString(
	// JsonUtil.DISHES_ID);
	// String price = array.getJSONObject(i).getString(JsonUtil.PRICE);
	// String dishes_name = array.getJSONObject(i).getString(
	// JsonUtil.DISHES_NAME);
	// menus.add(new Menu(dishes_name, Double.parseDouble(price)));
	// }
	// return menus;
	// }

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		this.pullToRefreshLayout = pullToRefreshLayout;
		ThreadPoolManager.getInstance().addTask(detailRun);
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub

	}

}

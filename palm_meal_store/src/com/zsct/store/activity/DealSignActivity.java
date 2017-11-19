/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.activity;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zsct.store.R;
import com.zsct.store.model.Order;
import com.zsct.store.util.HttpUtil;
import com.zsct.store.util.JsonUtil;
import com.zsct.store.util.ThreadPoolManager;

public class DealSignActivity extends BaseActivity implements OnClickListener {

	private EditText orderId;
	private EditText signcode;
	private Button spendBtn;
	private Order mOrder;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			closeLoadingDialog();
			Log.e("hjq", result);
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt(JsonUtil.CODE) == 0) {
					showShortToast(object.getString(JsonUtil.MSG));
				} else {
					showShortToast("已消费");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mOrder = (Order) getIntent().getSerializableExtra("order");
		setContentView(R.layout.activity_dealsign);
		orderId = (EditText) findViewById(R.id.order_id);
		signcode = (EditText) findViewById(R.id.signedit);
		spendBtn = (Button) findViewById(R.id.signcode);
		spendBtn.setOnClickListener(this);
		if (mOrder != null) {
			orderId.setText(mOrder.getOrder_id());
		}
		((ImageView) findViewById(R.id.back)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.signcode:
			final String order_id = orderId.getText().toString().trim();
			final String check_code = signcode.getText().toString().trim();
			if (order_id.equals("")) {
				showShortToast("订单号不能为空");
				return;
			}
			if (check_code.equals("")) {
				showShortToast("验证码不能为空");
				return;
			}
			showLoadingDialog();
			ThreadPoolManager.getInstance().addTask(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String result = HttpUtil
							.post(HttpUtil.URL_CHECKGROUP,
									new BasicNameValuePair(JsonUtil.ORDER_ID,
											order_id), new BasicNameValuePair(
											JsonUtil.CHECK_CODE, check_code));
					Message msg = new Message();
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			});

			break;

		default:
			break;
		}
	}

	protected Order getOrderData(String result) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject(result).getJSONArray(JsonUtil.ORDER)
				.getJSONObject(0);
		String id = json.getString(JsonUtil.ID);
		String order_id = json.getString(JsonUtil.ORDER_ID);
		String user_id = json.getString(JsonUtil.USER_ID);
		String store_id = json.getString(JsonUtil.STORE_ID);
		String create_time = json.getString(JsonUtil.CREATE_TIME);
		String phone = json.getString(JsonUtil.PHONE);
		String people = json.getString(JsonUtil.PEOPLE);
		String is_room = json.getString(JsonUtil.IS_ROOM);
		String order_time = json.getString(JsonUtil.ORDER_TIME);
		String type = json.getString(JsonUtil.TYPE);
		String status = json.getString(JsonUtil.STATUS);
		String checkgroup = json.getString(JsonUtil.CHECKGROUP);
		String group_count = json.getString(JsonUtil.GROUP_COUNT);
		String group_id = json.getString(JsonUtil.GROUP_ID);
		String total_price = json.getString(JsonUtil.TOTAL_PRICE);
		return new Order(id, order_id, user_id, phone, store_id, create_time,
				people, is_room, order_time, type, status, checkgroup,
				group_count, group_id, "", total_price, "", "","");
	}
}

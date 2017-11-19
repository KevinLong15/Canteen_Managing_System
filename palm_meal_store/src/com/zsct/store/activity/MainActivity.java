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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.zsct.store.R;
import com.zsct.store.app.MyApplication;
import com.zsct.store.util.HttpUtil;
import com.zsct.store.util.JsonUtil;
import com.zsct.store.util.PreferenceUtil;
import com.zsct.store.util.ThreadPoolManager;

public class MainActivity extends BaseActivity {
	private LinearLayout lin1, lin2, lin3, lin4, lin5, lin6;
	private EditText orderId;
	private EditText signcode;
	private TextView logout;
	private Button spendBtn;
	public static boolean isForeground = false;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lin1 = (LinearLayout) findViewById(R.id.lin1);
		lin2 = (LinearLayout) findViewById(R.id.lin2);
		lin3 = (LinearLayout) findViewById(R.id.lin3);
		lin4 = (LinearLayout) findViewById(R.id.lin4);
		lin5 = (LinearLayout) findViewById(R.id.lin5);
		lin6 = (LinearLayout) findViewById(R.id.lin6);
		orderId = (EditText) findViewById(R.id.order_id);
		signcode = (EditText) findViewById(R.id.signedit);
		logout = (TextView) findViewById(R.id.logout);
		spendBtn = (Button) findViewById(R.id.signcode);
		spendBtn.setOnClickListener(this);
		lin1.setOnClickListener(this);
		lin2.setOnClickListener(this);
		lin3.setOnClickListener(this);
		lin4.setOnClickListener(this);
		lin5.setOnClickListener(this);
		lin6.setOnClickListener(this);
		logout.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		isForeground = true;
		super.onResume();
		if (PreferenceUtil.getInstance(this).getAdmin_id().equals("")
				|| PreferenceUtil.getInstance(this).getSid().equals("")) {
			finish();
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
		} else {
			JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
			JPushInterface.init(this);
		}
	}

	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.lin1:
			startActivity(new Intent(this, BookListActivity.class));
			break;
		case R.id.lin2:
			startActivity(new Intent(this, MenuListActivity.class));
			break;
		case R.id.lin3:
			startActivity(new Intent(this, DealListActivity.class));
			break;
		case R.id.lin4:
			startActivity(new Intent(this, CommentListActivity.class));
			break;
		case R.id.lin5:
			startActivity(new Intent(this, WorkerActivity.class));
			break;
		case R.id.lin6:
			startActivity(new Intent(this, LogActivity.class));
			break;
		case R.id.logout:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("是否要注销当前账户")
					.setPositiveButton(R.string.sure,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									PreferenceUtil.getInstance(
											MainActivity.this).setSid("");
									PreferenceUtil.getInstance(
											MainActivity.this).setString(
											PreferenceUtil.ADMIN_ID, "");
									startActivity(new Intent(MainActivity.this,
											LoginActivity.class));
								}
							}).setNegativeButton(R.string.cancel, null)
					.create().show();
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

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getRepeatCount() == 0) {
			// 具体的操作代码
			Log.e("hjq", "onBackPressed");
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.sureifexit)
					.setNegativeButton(R.string.sure, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							MyApplication.getInstance().exit();
						}
					}).setPositiveButton(R.string.cancel, null).create().show();

		}
		return super.dispatchKeyEvent(event);
	}
}

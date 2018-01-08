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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.zsct.store.R;
import com.zsct.store.util.HttpUtil;
import com.zsct.store.util.JsonUtil;
import com.zsct.store.util.PreferenceUtil;
import com.zsct.store.util.ThreadPoolManager;

public class LoginActivity extends BaseActivity {
	private EditText username;
	private EditText password;
	private Button loginbtn;
	private TextView forgetword;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			closeLoadingDialog();
			Log.e("hjq", result);
			try {
				JSONObject json = new JSONObject(result);
				if (json.getInt(JsonUtil.CODE) == 1) {
					PreferenceUtil.getInstance(LoginActivity.this).setSid(
							json.getString(JsonUtil.STORE_ID));
					PreferenceUtil.getInstance(LoginActivity.this).setAdmin_id(
							json.getString(JsonUtil.ADMIN_ID));
					showShortToast("登录成功");
					startActivity(new Intent(LoginActivity.this,
							MainActivity.class));
					finish();
					JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
					JPushInterface.init(LoginActivity.this);
				} else {
					showShortToast("登录失败："+json.getString(JsonUtil.MSG));
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
		setContentView(R.layout.activity_login);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		loginbtn = (Button) findViewById(R.id.loginbtn);
		forgetword = (TextView) findViewById(R.id.forgetword);
		loginbtn.setOnClickListener(this);
		forgetword.setOnClickListener(this);
		if (!PreferenceUtil.getInstance(this).getSid().equals("")
				&& !PreferenceUtil.getInstance(this)
						.getString(PreferenceUtil.ADMIN_ID, "").equals("")) {
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.loginbtn:
			final String phone = username.getText().toString().trim();
			final String pass = password.getText().toString().trim();
			if (phone.equals("") || pass.equals("")) {
				showShortToast("用户名和密码不能为空");
				return;
			}
			showLoadingDialog();
			ThreadPoolManager.getInstance().addTask(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String result = HttpUtil.post(HttpUtil.URL_LOGIN,
							new BasicNameValuePair(JsonUtil.PHONE, phone),
							new BasicNameValuePair(JsonUtil.PASSWORD, pass));
					Message msg = new Message();
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			});
			break;
		case R.id.forgetword:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.notice)
					.setMessage(R.string.relate_manager)
					.setNegativeButton(R.string.sure, null).create().show();
			break;

		}
	}

}

/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class AuthRegisterActivity extends BaseActivity {
	private TextView registerbtn;
	private EditText phoneedit;
	private EditText codeedit;
	private EditText nameedit;
	private EditText passwordedit;
	private EditText passwordagainedit;
	private RadioGroup sexgroup;
	private Button getcodebtn;
	private TextView protocoltv;
	private String phone;
	private String code="";
	private String name;
	private String password;
	private String password1;
	private String sex;
	private final int regist_what = 0;
	private final int getcode_what = 1;
	private Timer mTimer;
	private Handler timerHandler = new Handler() {
		public void handleMessage(Message msg) {
			int num = msg.what;
			getcodebtn.setText(num
					+ "秒");
			if (num == -1) {
				mTimer.cancel();
				getcodebtn.setEnabled(true);
				getcodebtn.setText(R.string.register_button_code);
			}
		};
	};
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			closeLoadingDialog();
			String result = msg.obj.toString();
			switch (msg.what) {
			case regist_what:
				try {
					JSONObject json = new JSONObject(result);
					if (JsonUtil.getInt(json, JsonUtil.CODE) == 0) {
						showLongToast(JsonUtil.getStr(json, JsonUtil.MSG));
						getcodebtn.setEnabled(true);
						getcodebtn.setText(R.string.register_button_code);
					} else {
						showLongToast("注册成功");
						finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case getcode_what:
				try {
					JSONObject json = new JSONObject(result);
					if (!JsonUtil.getStr(json, JsonUtil.MSG).equals("success")) {	
						showLongToast(JsonUtil.getStr(json, JsonUtil.MSG));
						getcodebtn.setEnabled(true);
						getcodebtn.setText(R.string.register_button_code);
					} else {
						code=String.valueOf(JsonUtil.getInt(json, JsonUtil.CODE));
						showLongToast("验证码已发送，注意查收");	
						mTimer = new Timer();
						mTimer.schedule(new TimerTask() {
							int num = 90;
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Message msg = new Message();
								msg.what = num;
								timerHandler.sendMessage(msg);
								num--;
							}
						}, new Date(), 1000);
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth_register);
		phoneedit = (EditText) findViewById(R.id.register_phone);
		codeedit = (EditText) findViewById(R.id.register_code);
		nameedit = (EditText) findViewById(R.id.register_name);
		passwordedit = (EditText) findViewById(R.id.register_password);
		passwordagainedit = (EditText) findViewById(R.id.register_password_again);
		sexgroup = (RadioGroup) findViewById(R.id.register_sex);
		registerbtn = (TextView) findViewById(R.id.registerbtn);
		getcodebtn = (Button) findViewById(R.id.register_getcode);
		protocoltv=(TextView) findViewById(R.id.register_textview_protocol);
		findViewById(R.id.back).setOnClickListener(this);
		registerbtn.setOnClickListener(this);
		getcodebtn.setOnClickListener(this);
		protocoltv.setOnClickListener(this);
		phoneedit.setText(getLocalPhoneNumber());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.registerbtn:
			if (checkdata()) {
				ThreadPoolManager.getInstance().addTask(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String result = HttpUtil.post(HttpUtil.URL_REGISTER,
								new BasicNameValuePair(JsonUtil.NAME, name),
								new BasicNameValuePair(JsonUtil.PHONE, phone),
								new BasicNameValuePair(JsonUtil.SEX, sex),
								new BasicNameValuePair(JsonUtil.PASSWORD,password),
								new BasicNameValuePair(JsonUtil.REPASSWORD, password1));
						Message msg = new Message();
						msg.what=regist_what;
						msg.obj = result;
						mHandler.sendMessage(msg);
					}
				});
				showLoadingDialog();
			}
			break;
		case R.id.register_getcode:
			phone = phoneedit.getText().toString().trim();
			if (phone.equals("")) {
				showShortToast("手机号码不能为空");
				return;
			}
			getcodebtn.setText("正在发送");
			getcodebtn.setEnabled(false);
			ThreadPoolManager.getInstance().addTask(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String result = HttpUtil.get(HttpUtil.URL_CHECKMOBILE
							+"?mobile="+phone);
					Log.e("hjq", result);
					Message msg = new Message();
					msg.what=getcode_what;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			});
			break;
		case R.id.register_textview_protocol:
			Intent mIntent2=new Intent(this, StaticPageActivity.class);
			mIntent2.putExtra(JsonUtil.TITLE, getString(R.string.register_textview_protocol));
			startActivity(mIntent2);
			break;
		default:
			break;
		}
	}
	private String getLocalPhoneNumber() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String phoneId = tm.getLine1Number();
		return phoneId;
	}
	private boolean checkdata() {
		phone = phoneedit.getText().toString().trim();
		String inputcode = codeedit.getText().toString().trim();
		name = nameedit.getText().toString().trim();
		password = passwordedit.getText().toString().trim();
		password1 = passwordagainedit.getText().toString().trim();
		int checkid = sexgroup.getCheckedRadioButtonId();
		sex = sexgroup.getCheckedRadioButtonId()==R.id.radioMale?"1":"0";
		if ( phone.equals("")) {
			showLongToast("电话号码不能为空");
			return false;
		} else if (inputcode.equals("")) {
			showLongToast("验证不能为空");
			return false;
		} else if (name.equals("")) {
			showLongToast("姓名不能为空");
			return false;
		} else if (password.equals("")) {
			showLongToast("密码不能为空");
			return false;
		} else if (password1.equals("")) {
			showLongToast("确认密码不能为空");
			return false;
		} else if (phone.length()!=11) {
			showLongToast("手机号码的长度不对");
			return false;
		} else if (!password.equals(password1)) {
			showLongToast("两次输入的密码必须一致");
			return false;
		} else if (password.length() < 6 && password.length() > 16) {
			showLongToast("密码长度为6到16位");
			return false;
		} else if (!code.equals(inputcode)) {
			showLongToast("验证码不正确");
			return false;
		} 
		return true;
	}

}

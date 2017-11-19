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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fdcz.zsct.R;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class FindPasswordActivity extends BaseActivity {
	private EditText phone;
	private EditText code;
	private EditText password;
	private EditText repassword;
	private Button getcode;
	private Button save;
	private Timer mTimer;
	private String phonestr="";
	private String codestr="";
	private String passwordstr="";
	private String repasswordstr="";
	private final int save_what = 0;
	private final int getcode_what = 1;
	private Handler timerHandler = new Handler() {
		public void handleMessage(Message msg) {
			int num = msg.what;
			getcode.setText(num
					+ "秒");
			if (num == -1) {
				mTimer.cancel();
				getcode.setEnabled(true);
				getcode.setText(R.string.register_button_code);
			}
		};
	};
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			closeLoadingDialog();
			String result = msg.obj.toString();
			Log.e("hjq", result);
			switch (msg.what) {
			case save_what:
				try {
					JSONObject json = new JSONObject(result);
					if (JsonUtil.getInt(json, JsonUtil.CODE) == 0) {
						showLongToast(JsonUtil.getStr(json, JsonUtil.MSG));
					} else {
						showLongToast(JsonUtil.getStr(json, JsonUtil.MSG));
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
						getcode.setEnabled(true);
						getcode.setText(R.string.register_button_code);
					} else {
						codestr=String.valueOf(JsonUtil.getInt(json, JsonUtil.CODE));
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
		}
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpassword);
		phone=(EditText)findViewById(R.id.phone);
		code=(EditText)findViewById(R.id.code);
		password=(EditText)findViewById(R.id.password);
		repassword=(EditText)findViewById(R.id.repassword);
		getcode=(Button)findViewById(R.id.getcode);
		save=(Button)findViewById(R.id.save);
		findViewById(R.id.back).setOnClickListener(this);
		getcode.setOnClickListener(this);
		save.setOnClickListener(this);
		phone.setText(getLocalPhoneNumber());
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.getcode:
			phonestr = phone.getText().toString().trim();
			if (phone.equals("")) {
				showShortToast("手机号码不能为空");
				return;
			}
			getcode.setText("正在发送");
			getcode.setEnabled(false);
			ThreadPoolManager.getInstance().addTask(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String result = HttpUtil.get(HttpUtil.URL_FORGETPSDCHECKMOBILE
							+"?mobile="+phonestr);
					Message msg = new Message();
					msg.what=getcode_what;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			});
			break;
		case R.id.save:
			if (checkdata()) {
				ThreadPoolManager.getInstance().addTask(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String result = HttpUtil.post(HttpUtil.URL_FORGETPASSWORD,
								new BasicNameValuePair(JsonUtil.PHONE, phonestr),
								new BasicNameValuePair(JsonUtil.PASSWORD,passwordstr));
						Message msg = new Message();
						msg.what=save_what;
						msg.obj = result;
						mHandler.sendMessage(msg);
					}
				});
				showLoadingDialog();
			}
			break;
		default:
			break;
		}
	}
	private boolean checkdata() {
		phonestr = phone.getText().toString().trim();
		String codestr1 = code.getText().toString().trim();
		passwordstr = password.getText().toString().trim();
		repasswordstr =repassword.getText().toString().trim();
		if (phonestr.equals("")) {
			showLongToast("电话号码不能为空");
			return false;
		} else if (codestr1.equals("")) {
			showLongToast("验证码不能为空");
			return false;
		} else if (passwordstr.equals("")) {
			showLongToast("密码不能为空");
			return false;
		} else if (repasswordstr.equals("")) {
			showLongToast("确认密码不能为空");
			return false;
		} else if (!passwordstr.equals(repasswordstr)) {
			showLongToast("两次输入的密码必须一致");
			return false;
		} else if (passwordstr.length() < 6 && passwordstr.length() > 16) {
			showLongToast("密码长度为6到16位");
			return false;
		} else if (!codestr.equals(codestr1)) {
			showLongToast("验证码不正确");
			return false;
		} 
		return true;
	}
	private String getLocalPhoneNumber() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String phoneId = tm.getLine1Number();
		return phoneId;
	}
}

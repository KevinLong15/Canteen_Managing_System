/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import org.apache.http.message.BasicNameValuePair;
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
import android.widget.EditText;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.fdcz.zsct.R;
import com.zsct.customer.dao.UserDao;
import com.zsct.customer.model.Shop;
import com.zsct.customer.model.User;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.PreferenceUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class AuthLoginActivity extends BaseActivity implements OnClickListener {
	private TextView upatepassbtn, registbtn;
	private EditText phoneedit;
	private EditText passwordedit;
	private Button loginbtn;
	private String phone;
	private String password;
	private Shop mShop;
	private int type=0;
	private final int getmycoin_what=1;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = msg.obj.toString();
			closeLoadingDialog();
			Log.e("hjq", result);
			switch (msg.what) {
			case 0:
				try {
					JSONObject json = new JSONObject(result);
					if (JsonUtil.getInt(json, JsonUtil.CODE) != 1) {
						showLongToast(JsonUtil.getStr(json, JsonUtil.MSG));
					} else {
						JSONObject userobj=json.getJSONObject("user");
						String id=userobj.getString(JsonUtil.ID);
						String name=userobj.getString(JsonUtil.NAME);
						String phone=userobj.getString(JsonUtil.PHONE);
						String sex=userobj.getString(JsonUtil.SEX);
						String password=userobj.getString(JsonUtil.PASSWORD);
						String create_time=userobj.getString(JsonUtil.CREATE_TIME);
						String image_thumb=userobj.getString(JsonUtil.IMAGE_THUMB);
						String image=userobj.getString(JsonUtil.IMAGE);
						User user=new User(id, name, phone, sex, password, create_time, image_thumb, image); 
						new UserDao(AuthLoginActivity.this).insert(user);
						showLongToast("登录成功");	
						PreferenceUtil.getInstance(AuthLoginActivity.this).setUid(user.getId());
						PreferenceUtil.getInstance(AuthLoginActivity.this).getString(PreferenceUtil.PHONE, user.getPhone());
				
						JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
					    JPushInterface.init(AuthLoginActivity.this);     		// 初始化 JPush
					    ThreadPoolManager.getInstance().addTask(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
							String result=HttpUtil.post(HttpUtil.URL_TOTALSHIBIBYUSERID,
										new BasicNameValuePair(JsonUtil.USER_ID, PreferenceUtil.getInstance(AuthLoginActivity.this).getUid()));
							Message msg=new Message();
							msg.obj=result;
							msg.what=getmycoin_what;
							mHandler.sendMessage(msg);
							}
						});
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
            case getmycoin_what:
            	if (result.trim().equals("null")) {
            		PreferenceUtil.getInstance(AuthLoginActivity.this).setString(PreferenceUtil.SHIBI, "0");
            		finish();
            		return ;
				}
				try {
					JSONObject mycoinobj=new JSONObject(result);
					String shibi=mycoinobj.getString(JsonUtil.SHIBI);
					PreferenceUtil.getInstance(AuthLoginActivity.this).setString(PreferenceUtil.SHIBI, shibi);
					finish();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			default:
				break;
			}
			
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth_login);
		type=getIntent().getIntExtra("type", 0);
		upatepassbtn = (TextView) findViewById(R.id.updatepassbtn);
		registbtn = (TextView) findViewById(R.id.registbtn);
		phoneedit = (EditText) findViewById(R.id.login_phone);
		passwordedit = (EditText) findViewById(R.id.login_password);
		loginbtn = (Button) findViewById(R.id.login_btn);
		findViewById(R.id.back).setOnClickListener(this);
		upatepassbtn.setOnClickListener(this);
		registbtn.setOnClickListener(this);
		loginbtn.setOnClickListener(this);
		mShop=(Shop)getIntent().getSerializableExtra("object");
		if (new UserDao(this).queryAll().size()>0) {
			User mUser=new UserDao(this).queryAll().get(0);
			phoneedit.setText(mUser.getPhone());
		}else {
			phoneedit.setText(PreferenceUtil.getInstance(this).getString(PreferenceUtil.PHONE, ""));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.updatepassbtn:
			startActivity(new Intent(this, FindPasswordActivity.class));
			break;
		case R.id.registbtn:
			startActivity(new Intent(this, AuthRegisterActivity.class));
			break;
		case R.id.login_btn:
			if (checkdata()) {
				ThreadPoolManager.getInstance().addTask(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String result = HttpUtil.post(HttpUtil.URL_LOGIN,
								new BasicNameValuePair(JsonUtil.PHONE, phone),
								new BasicNameValuePair(JsonUtil.PASSWORD,
										password));
						Log.e("hjq", result);
						Message msg = new Message();
						msg.obj = result;
						msg.what=0;
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
		boolean isright = false;
		phone = phoneedit.getText().toString().trim();
		password = passwordedit.getText().toString().trim();
		if (phone == null || phone.equals("")) {
			showLongToast("电话号码不能为空");
		} else if (password == null || password.equals("")) {
			showLongToast("密码不能为空");
		} else {
			isright = true;
		}
		Log.i("hjq", isright + "");
		return isright;
	}
}

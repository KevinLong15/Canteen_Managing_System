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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fdcz.zsct.R;
import com.zsct.customer.dao.UserDao;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.PreferenceUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class PersonUpdatePasswordActivity extends BaseActivity {
	private EditText passwordEdit;
	private EditText repasswordEdit;
	private Button savebtn;
	private String uid ="";
	private String oldpassword = "";
	private String passwordstr ;
	private String repasswordstr ;
    private Handler mHandler=new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		String result=msg.obj.toString();
    		Log.e("hjq", result);
				try {
					JSONObject json = new JSONObject(result);
				if (JsonUtil.getInt(json, JsonUtil.CODE) == 1) {
					showLongToast("修改密码成功");
					new UserDao(PersonUpdatePasswordActivity.this).updatePassWordById(passwordstr,uid);
					finish();
				} else {
					showLongToast(JsonUtil.getStr(json, JsonUtil.MSG));
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
		setContentView(R.layout.activity_person_updatepassword);
		passwordEdit = (EditText) findViewById(R.id.password);
		repasswordEdit = (EditText) findViewById(R.id.repassword);
		savebtn = (Button) findViewById(R.id.save);
		savebtn.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		uid = PreferenceUtil.getInstance(this).getUid();
		oldpassword = new UserDao(this).queryById(uid).getPassword();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.save:
			 passwordstr = passwordEdit.getText().toString().trim();
			repasswordstr = repasswordEdit.getText().toString().trim();
			if (passwordstr.equals("")) {
				showLongToast("密码不能为空");
				return;
			}
			if (!passwordstr.equals(repasswordstr)) {
				showLongToast("两次输入的密码必须一致");
				return;
			}
			ThreadPoolManager.getInstance().addTask(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String result=HttpUtil.post(HttpUtil.URL_RESETPASSWORD,
							new BasicNameValuePair(JsonUtil.USER_ID, uid),
							new BasicNameValuePair("oldpassword", oldpassword),
							new BasicNameValuePair("newpassword", passwordstr),
							new BasicNameValuePair("repassword", repasswordstr));
					Message msg=new Message();
					msg.obj=result;
					mHandler.sendMessage(msg);
				}
			});
			break;

		default:
			break;
		}
	}
}

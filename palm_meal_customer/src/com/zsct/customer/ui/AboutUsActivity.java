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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.util.CommonUtil;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class AboutUsActivity extends BaseActivity {
   private TextView titletv;
   private TextView contenttv;
   private TextView versionname;
   private String code="";
   private Handler mHandler=new Handler(){
	   public void handleMessage(android.os.Message msg) {
		   String result=msg.obj.toString();
		   closeLoadingDialog();
		   Log.e("hjq", result);
		try {
			JSONArray array = new JSONArray(result);
		   for (int i = 0; i < array.length(); i++) {
			JSONObject object=array.getJSONObject(i);
				titletv.setText(object.getString(JsonUtil.TITLE));
				contenttv.setText(object.getString(JsonUtil.CONTENT));

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
		setContentView(R.layout.activity_aboutus);
		code=getIntent().getStringExtra(JsonUtil.CODE);
		Log.e("hjq", "code="+code);
		titletv=(TextView)findViewById(R.id.title);
		contenttv=(TextView)findViewById(R.id.content);
		versionname=(TextView)findViewById(R.id.versionname);
		versionname.setText("Version "+CommonUtil.getVersionName(this));
		findViewById(R.id.back).setOnClickListener(this);
		showLoadingDialog();
		ThreadPoolManager.getInstance().addTask(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result=HttpUtil.post(HttpUtil.URL_STATICPAGE, 
						new BasicNameValuePair(JsonUtil.CODE,"aboutus"));
				Message msg=new Message();
				msg.obj=result;
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
			finish();
			break;

		default:
			break;
		}
	}
}

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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.fdcz.zsct.R;
import com.zsct.customer.util.CommonUtil;
import com.zsct.customer.util.DialogUtil;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.ThreadPoolManager;
import com.zsct.customer.util.UpdateManager;

public class PersonSettingActivity extends BaseActivity implements OnClickListener {
	private LinearLayout personAboutus;
	private LinearLayout personClean;
	private LinearLayout personFeedback;
	private LinearLayout personProtocol;
	private LinearLayout personUpdate;
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			closeLoadingDialog();
			String result=msg.obj.toString();
			try {
				final JSONObject json=new JSONObject(result);
				if (json.getInt(JsonUtil.CODE)==1) {
					showLongToast(json.getString(JsonUtil.MSG));
				}else {
					final String path=json.getString(JsonUtil.PATH);
					final String updatemsg=json.getString(JsonUtil.MSG);
					DialogUtil.showDialog(PersonSettingActivity.this,"发现新版本！",
							json.getString(JsonUtil.MSG)+", 是否要更新？",
							getString(R.string.system_sure),
							getString(R.string.system_cancel) , 
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									UpdateManager mUpdateManager=new UpdateManager(
											PersonSettingActivity.this,
											updatemsg,
											HttpUtil.SERVER+path);
									mUpdateManager.showDownloadDialog();
								}
							}, null, true);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_setting);
		personClean = (LinearLayout) findViewById(R.id.personClean);
		personFeedback = (LinearLayout) findViewById(R.id.personmainLogout);
		personProtocol = (LinearLayout) findViewById(R.id.setting_feedback);
		personUpdate = (LinearLayout) findViewById(R.id.personupdate);
		findViewById(R.id.back).setOnClickListener(this);
		personAboutus.setOnClickListener(this);
		personClean.setOnClickListener(this);
		personFeedback.setOnClickListener(this);
		personProtocol.setOnClickListener(this);
		personUpdate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.personClean:
			final AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setMessage("是否清除缓存").setNegativeButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					final AlertDialog.Builder builder2=new AlertDialog.Builder(PersonSettingActivity.this);
					builder2.setMessage("已经成功清理缓存文件").setNeutralButton("确定", null).create().show();
				}
			}).setPositiveButton("取消",null ).create().show();
//			showShortToast("已经清空了");
			break;
		case R.id.setting_feedback:
			startActivity(new Intent(this, PersonFeedbackActivity.class));
			break;
		case R.id.personmainLogout:
			Intent mIntent2=new Intent(this, StaticPageActivity.class);
			mIntent2.putExtra(JsonUtil.TITLE, getString(R.string.person_setting_text_explain));
			startActivity(mIntent2);
			break;
		case R.id.personupdate:
			showLoadingDialog("正在检查版本..");
			ThreadPoolManager.getInstance().addTask(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.e("hjq", "version="+CommonUtil.getVersionName(PersonSettingActivity.this));
					String result=HttpUtil.post(HttpUtil.URL_ANDROIDUPDATE,
					new BasicNameValuePair(JsonUtil.VERSION, CommonUtil.getVersionName(PersonSettingActivity.this)));
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
//	public void cleanCache(){
//	PackageManager pm = getPackageManager();
//	//反射
//	try {
//		Method method = PackageManager.class.getMethod("getPackageSizeInfo", new Class[]{String.class,IPackageStatsObserver.class});
//		method.invoke(pm, new Object[]{"com.wang.clearcache",new IPackageStatsObserver.Stub() {
//			
//			@Override
//			public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
//					throws RemoteException 
//			{
//				long cachesize = pStats.cacheSize;
//				long codesize = pStats.codeSize;
//				long datasize = pStats.dataSize;
//				System.out.println("cachesize:"+ cachesize);
//				System.out.println("codesize:"+ codesize);
//				System.out.println("datasize"+ datasize);
//			}
//		}});
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	}
}

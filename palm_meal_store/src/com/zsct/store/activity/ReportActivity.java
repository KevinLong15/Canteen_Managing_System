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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zsct.store.R;
import com.zsct.store.model.Comment;
import com.zsct.store.util.HttpUtil;
import com.zsct.store.util.JsonUtil;
import com.zsct.store.util.PreferenceUtil;
import com.zsct.store.util.ThreadPoolManager;

public class ReportActivity extends BaseActivity {
	private EditText contentEdit;
	private Button submit;
	private Comment comment;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			Log.e("hjq", result);
			closeLoadingDialog();
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt(JsonUtil.CODE) == 1) {
					showLongToast("操作成功");
					startActivity(new Intent(ReportActivity.this,
							MainActivity.class));
					finish();
				} else {

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
		setContentView(R.layout.activity_report);
		comment = (Comment) getIntent().getSerializableExtra("comment");
		contentEdit = (EditText) findViewById(R.id.feedback_content);
		submit = (Button) findViewById(R.id.submit);
		findViewById(R.id.back).setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.submit:
			final String contentstr = contentEdit.getText().toString().trim();
			if (contentstr.equals("")) {
				showLongToast("反馈内容不能为空");
				return;
			}
			showLoadingDialog();
			ThreadPoolManager.getInstance().addTask(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String admin_id = PreferenceUtil.getInstance(
							ReportActivity.this).getString(
							PreferenceUtil.ADMIN_ID, "");
					Log.e("hjq", HttpUtil
							.getURlStr(HttpUtil.URL_REPORT,
									new BasicNameValuePair(JsonUtil.COMMENT_ID,
											comment.getId()),
									new BasicNameValuePair(JsonUtil.ADMIN_ID,
											admin_id),
									new BasicNameValuePair(JsonUtil.CONTENT,
											comment.getContent()),
									new BasicNameValuePair(JsonUtil.STORE_ID,
											comment.getStore_id())));
					String result = HttpUtil
							.post(HttpUtil.URL_REPORT, new BasicNameValuePair(
									JsonUtil.COMMENT_ID, comment.getId()),
									new BasicNameValuePair(JsonUtil.ADMIN_ID,
											admin_id),
									new BasicNameValuePair(JsonUtil.CONTENT,
											comment.getContent()),
									new BasicNameValuePair(JsonUtil.STORE_ID,
											comment.getStore_id()));
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
}

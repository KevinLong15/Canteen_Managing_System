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
import android.widget.RatingBar;
import android.widget.TextView;
import cn.jpush.android.a;

import com.fdcz.zsct.R;
import com.zsct.customer.app.MyApplication;
import com.zsct.customer.model.OrderDeal;
import com.zsct.customer.model.OrderItem;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.PreferenceUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class ShopMenuCommentActivity extends BaseActivity implements
		OnClickListener {
    private Button submitbtn;
    private TextView codetv;
    private EditText contented;
    private RatingBar mRating;
    private String contentstr=""; 
    private String order_id=""; 
    private String store_id=""; 
    private OrderDeal mdeal;
    private OrderItem mItem;
    private String rating;
    private Handler mHandler=new Handler(){
    	public void handleMessage(Message msg) {
    		String result=msg.obj.toString();
    		closeLoadingDialog();
    		Log.e("hjq", result);
    		try {
				JSONObject json=new JSONObject(result);
				if (json.getInt(JsonUtil.CODE)==1) {
					showLongToast("评价成功");
					finish();
					MyApplication.getInstance().type=1;
					startActivity(new Intent(ShopMenuCommentActivity.this, MainActivity.class));
				}else {
					showLongToast(json.getString(JsonUtil.MSG));
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
		setContentView(R.layout.activity_shop_menu_comment);
		mdeal=(OrderDeal)getIntent().getSerializableExtra("orderdeal");
		submitbtn=(Button)findViewById(R.id.submit);
		codetv=(TextView)findViewById(R.id.code);
		contented=(EditText)findViewById(R.id.feedback_content);
		mRating=(RatingBar)findViewById(R.id.feedback_rating);
		if (mdeal!=null) {
			order_id=mdeal.getOrder_id();
			store_id=mdeal.getStore_id();
		}else {
			mItem=(OrderItem)getIntent().getSerializableExtra("orderitem");
			order_id=mItem.getOrder_id();
			store_id=mItem.getStore_id();
		}
		codetv.setText(order_id);
		findViewById(R.id.back).setOnClickListener(this);
		submitbtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		  case R.id.back:
			  onBackPressed();
			  break;
          case R.id.submit:
        	 if (checkData()) {
        		 showLoadingDialog();
				ThreadPoolManager.getInstance().addTask(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String user_id=PreferenceUtil.getInstance(ShopMenuCommentActivity.this).getUid();
						String content=contentstr;
						String sorce=rating;
						Log.e("hjq", HttpUtil.getURlStr(HttpUtil.URL_USERCOMMENT,
						new BasicNameValuePair(JsonUtil.ORDER_ID,order_id),
						new BasicNameValuePair(JsonUtil.USER_ID, user_id),
						new BasicNameValuePair(JsonUtil.CONTENT, content),
						new BasicNameValuePair(JsonUtil.SORCE, sorce),
						new BasicNameValuePair(JsonUtil.STORE_ID, store_id)));
						String result=HttpUtil.post(HttpUtil.URL_USERCOMMENT,
						new BasicNameValuePair(JsonUtil.ORDER_ID,order_id),
						new BasicNameValuePair(JsonUtil.USER_ID, user_id),
						new BasicNameValuePair(JsonUtil.CONTENT, content),
						new BasicNameValuePair(JsonUtil.SORCE, sorce),
						new BasicNameValuePair(JsonUtil.STORE_ID, store_id));
						Message msg=new Message();
						msg.obj=result;
						mHandler.sendMessage(msg);
					}
				});
        		 
			}
        	  break;
 
		}
	}

	private boolean checkData() {
		// TODO Auto-generated method stub
		contentstr=contented.getText().toString().trim();
//		String relate=relateed.getText().toString().trim();
		rating=String.valueOf(mRating.getRating());
		if (contentstr.equals("")) {
			showShortToast("内容不能为空");
			return false;
		}
		if (rating.equals("")) {
			showShortToast("联系方式不能为空");
			return false;
		}
		if (order_id.equals("")) {
			showShortToast("没有发现订单号");
			return false;
		}
		return true;
	}

}

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
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.model.OrderDeal;
import com.zsct.customer.util.CommonUtil;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.PreferenceUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class ShopDealRefundDetailActivity extends BaseActivity {
	private TextView orderid;
	private TextView totalprice;
	private EditText name;
	private EditText email;
	private EditText relateway;
	private EditText bank;
	private EditText accountbank;
	private EditText accountperson;
	private Button refund_btn;
	private OrderDeal mOrderDeal;
    private Handler mHandler=new Handler(){
    	public void handleMessage(Message msg) {
    		String result=msg.obj.toString();
    		closeLoadingDialog();
    		Log.e("hjq", result);
    		try {
				JSONObject json=new JSONObject(result);
				if (json.getInt(JsonUtil.CODE)==1) {
					showShortToast("提交成功");
					finish();
				}else {
					showShortToast(json.getString(JsonUtil.MSG));
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
		setContentView(R.layout.activity_deal_refund_detail);
		mOrderDeal = (OrderDeal) getIntent().getSerializableExtra("orderdeal");
		orderid = (TextView) findViewById(R.id.orderid);
		totalprice = (TextView) findViewById(R.id.totalprice);
		name = (EditText) findViewById(R.id.refund_name);
		email = (EditText) findViewById(R.id.refund_email);
		relateway = (EditText) findViewById(R.id.refund_relateways);
		bank = (EditText) findViewById(R.id.refund_bank);
		accountbank = (EditText) findViewById(R.id.refund_accountbank);
		accountperson = (EditText) findViewById(R.id.refund_accountbankperson);
		refund_btn = (Button) findViewById(R.id.refund_btn);
		if (mOrderDeal != null) {
			orderid.setText(mOrderDeal.getOrder_id());
			totalprice.setText(getString(R.string.system_rmb)
					+ CommonUtil.DouToStr1(Double.parseDouble(mOrderDeal
							.getGroup_price())
							* Integer.parseInt(mOrderDeal.getGroup_count())));
		}
		findViewById(R.id.back).setOnClickListener(this);
		refund_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.refund_btn:
			final String uid=PreferenceUtil.getInstance(this).getUid();
			final String namestr = name.getText().toString();
			final String emailstr = email.getText().toString();
			final String relatestr = relateway.getText().toString();
			final String bankstr = bank.getText().toString();
			final String accountbankstr = accountbank.getText().toString();
			final String accountpersonstr = accountperson.getText().toString();
			if (namestr.equals("")) {
				showShortToast("姓名不能为空");
				return;
			} else if (emailstr.equals("")) {
				showShortToast("邮箱不能为空");
				return;
			} else if (relatestr.equals("")) {
				showShortToast("联系方式不能为空");
				return;
			} else if (bankstr.equals("")) {
				showShortToast("银行不能为空");
				return;
			} else if (accountbankstr.equals("")) {
				showShortToast("开户行不能为空");
				return;
			} else if (accountpersonstr.equals("")) {
				showShortToast("开户名不能为空");
				return;
			}
			showLoadingDialog();
			ThreadPoolManager.getInstance().addTask(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				String result=HttpUtil.post(HttpUtil.URL_DRAWBACKGROUP, 
							new BasicNameValuePair(JsonUtil.USER_ID,uid),
							new BasicNameValuePair(JsonUtil.ORDER_ID,mOrderDeal.getOrder_id()),
							new BasicNameValuePair(JsonUtil.NAME,namestr),
							new BasicNameValuePair(JsonUtil.PHONE,relatestr),
							new BasicNameValuePair(JsonUtil.BANK,bankstr),
							new BasicNameValuePair(JsonUtil.BANK_NAME,accountbankstr),
							new BasicNameValuePair(JsonUtil.BANK_USER,accountpersonstr),
							new BasicNameValuePair(JsonUtil.MAIL,emailstr));
				Message msg=new Message();
				msg.obj=result;
				mHandler.sendMessage(msg);
				}
			});
			break;
		}
	}

	
}

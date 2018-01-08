/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.model.OrderDeal;
import com.zsct.customer.util.CommonUtil;

public class ShopDealRefundActivity extends BaseActivity{
	private TextView orderid;
	private TextView create_time;
	private TextView dealname;
	private TextView dealprice;
	private TextView dealtotalprice;
	private Button zfbbtn;
	private Button bankcardbtn;
	private Button shibibtn;
    private OrderDeal mOrderDeal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deal_refund);
		mOrderDeal=(OrderDeal)getIntent().getSerializableExtra("orderdeal");
		orderid=(TextView)findViewById(R.id.orderid);
		create_time=(TextView)findViewById(R.id.ordertime);
		dealname=(TextView)findViewById(R.id.dealname);
		dealprice=(TextView)findViewById(R.id.dealprice);
		dealtotalprice=(TextView)findViewById(R.id.dealtotalprice);
		zfbbtn = (Button) findViewById(R.id.zfb_btn);
		bankcardbtn = (Button) findViewById(R.id.bankcard_btn);
		shibibtn = (Button) findViewById(R.id.shibi_btn);
		if (mOrderDeal!=null) {
			orderid.setText(mOrderDeal.getOrder_id());
			create_time.setText(CommonUtil.getTimestr(mOrderDeal.getCreate_time()));
			dealname.setText(mOrderDeal.getTitle());
			dealprice.setText(getString(R.string.system_rmb)+mOrderDeal.getGroup_price());
			dealtotalprice.setText(getString(R.string.system_rmb)
					+CommonUtil.DouToStr1(Double.parseDouble(mOrderDeal.getGroup_price())*Integer.parseInt(mOrderDeal.getGroup_count())));
		}
		findViewById(R.id.back).setOnClickListener(this);
		zfbbtn.setOnClickListener(this);
		bankcardbtn.setOnClickListener(this);
		shibibtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.zfb_btn:
          
			break;
		case R.id.bankcard_btn:
			Intent mIntent=new Intent(this, ShopDealRefundDetailActivity.class);
			mIntent.putExtra("orderdeal", mOrderDeal);
			startActivity(mIntent);
			break;
		case R.id.shibi_btn:

			break;
		}
	}

}

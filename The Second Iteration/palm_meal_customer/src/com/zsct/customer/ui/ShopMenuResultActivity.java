/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.dao.UserDao;
import com.zsct.customer.model.Shop;
import com.zsct.customer.util.CommonUtil;
import com.zsct.customer.util.JsonUtil;

public class ShopMenuResultActivity extends Activity implements OnClickListener {
	private Button mylistbtn;
	private Button commentbtn;
	private TextView order_id;
	private TextView datetv;
	private TextView timetv;
	private TextView personnumtv;
	private TextView phonetv;
	private ImageView orderlist;
    private Shop mShop;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_menu_result);
		mShop=(Shop)getIntent().getSerializableExtra("object");
		mylistbtn = (Button) findViewById(R.id.menuresult_mylist);
		commentbtn = (Button) findViewById(R.id.menuresult_comment);
		order_id = (TextView) findViewById(R.id.order_id);
		datetv = (TextView) findViewById(R.id.datetv);
		timetv = (TextView) findViewById(R.id.timetv);
		personnumtv = (TextView) findViewById(R.id.personnumtv);
		phonetv = (TextView) findViewById(R.id.phonetv);	
		orderlist = (ImageView) findViewById(R.id.orderlist);	
		((TextView)findViewById(R.id.title)).setText(mShop.getName());
		order_id.setText(getIntent().getStringExtra("order_id"));
		String mydate = getIntent().getStringExtra("create_time");
		datetv.setText(CommonUtil.getTimestr(mydate));
	    timetv.setText(mydate.substring(11, 17));
		personnumtv.setText(getIntent().getStringExtra("personnum"));
		phonetv.setText(new UserDao(this).queryAll().get(0).getPhone());
		findViewById(R.id.back).setOnClickListener(this);
		orderlist.setOnClickListener(this);
		mylistbtn.setOnClickListener(this);
		commentbtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.orderlist:
			Intent mIntent=new Intent(MainActivity.ACTION_TAB);
			mIntent.putExtra("index", 2);
			sendBroadcast(mIntent);
            startActivity(new Intent(this, MainActivity.class));
            finish();
			break;
		case R.id.menuresult_mylist:
			Intent maIntent=new Intent(MainActivity.ACTION_TAB);
			maIntent.putExtra("index", 2);
			sendBroadcast(maIntent);
            startActivity(new Intent(this, MainActivity.class));
            finish();
			break;
		case R.id.menuresult_comment:
			Intent commintent=new Intent(this, ShopMenuCommentActivity.class);
			commintent.putExtra(JsonUtil.ORDER,mShop);
			commintent.putExtra(JsonUtil.ORDER_ID,order_id.getText().toString().trim());
			startActivity(commintent);
			break;

		default:
			break;
		}
	}

}

/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.dao.UserDao;
import com.zsct.customer.model.User;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.ImageLoaderUtil;
import com.zsct.customer.util.PreferenceUtil;

public class PersonMainActivity extends BaseActivity implements OnClickListener {
	private LinearLayout personInfo;
	private LinearLayout personShop;
	private LinearLayout personCoin;
	private LinearLayout personSetting;
	private LinearLayout personLogout;
	private ImageView headIv;
    private TextView phonetv;
    private TextView nametv;
    private User mUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_main);
		personInfo = (LinearLayout) findViewById(R.id.personmainInformation);
		personShop = (LinearLayout) findViewById(R.id.personmainShop);
		personCoin = (LinearLayout) findViewById(R.id.personmainMycoin);
		personSetting = (LinearLayout) findViewById(R.id.personmainSetting);
		personLogout = (LinearLayout) findViewById(R.id.personmainLogout);
		headIv=(ImageView)findViewById(R.id.personmainPhoto);
		phonetv=(TextView)findViewById(R.id.personmainTextTelephone);
		nametv=(TextView)findViewById(R.id.personmainTextName);
		personInfo.setOnClickListener(this);
		personShop.setOnClickListener(this);
		personCoin.setOnClickListener(this);
		personSetting.setOnClickListener(this);
		personLogout.setOnClickListener(this);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mUser=new UserDao(this).queryById(PreferenceUtil.getInstance(this).getUid());
		if (mUser!=null) {
			phonetv.setText(mUser.getPhone());
			nametv.setText(mUser.getName());
			if (mUser.getImage().equals("")) {
				headIv.setImageResource(R.drawable.null_user);
			}else {
				ImageLoaderUtil.displayImage(HttpUtil.SERVER+mUser.getImage(), headIv, this);
			}
		}else {
			nametv.setText("");
			phonetv.setText("");
			headIv.setImageResource(R.drawable.null_user);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (PreferenceUtil.getInstance(this).getUid().equals("")) {
			startActivity(new Intent(this, AuthLoginActivity.class));
			return ;
		}
		switch (v.getId()) {
		case R.id.personmainInformation:
			startActivity(new Intent(this, PersonInfoActivity.class));
			break;
		case R.id.personmainShop:
			startActivity(new Intent(this, PersonShopActivity.class));
			break;
		case R.id.personmainMycoin:
			startActivity(new Intent(this, PersonCoinActivity.class));
			break;
		case R.id.personmainSetting:
			startActivity(new Intent(this, PersonSettingActivity.class));
			break;
		case R.id.personmainLogout:
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setMessage("是否要注销当前账户")
			.setPositiveButton(R.string.system_sure, new DialogInterface.OnClickListener() {		
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
//					   new UserDao(PersonMainActivity.this).deleteAll();
			            PreferenceUtil.getInstance(PersonMainActivity.this).setUid("");
			            new UserDao(PersonMainActivity.this).deleteAll();
			            startActivity(new Intent(PersonMainActivity.this, AuthLoginActivity.class));
				}
			}).setNegativeButton(R.string.cancel, null).create().show();
           
			break;
		}
	}
}

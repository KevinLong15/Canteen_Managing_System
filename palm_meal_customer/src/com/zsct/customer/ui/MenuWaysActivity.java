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

import com.fdcz.zsct.R;

public class MenuWaysActivity extends Activity implements OnClickListener{
    private Button bookmenubtn,shopmenubtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menuways);
		bookmenubtn=(Button)findViewById(R.id.bookmenubtn);
		shopmenubtn=(Button)findViewById(R.id.shopmenubtn);
		bookmenubtn.setOnClickListener(this);
		shopmenubtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bookmenubtn:
			startActivity(new Intent(this, ShopMenuActivity.class));
			break;
	    case R.id.shopmenubtn:
			
			break;

		default:
			break;
		}
	}
	
}

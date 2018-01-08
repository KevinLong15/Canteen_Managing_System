/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsct.store.R;
import com.zsct.store.model.Comment;
import com.zsct.store.model.Menu;
import com.zsct.store.util.CommonUtil;

public class CommentDetailActivity extends BaseActivity implements
		OnClickListener {

	private TextView orderId;
	private TextView orderTime;
	private TextView orderName;
	private TextView orderPhone;
	private TextView orderPeople;
	private TextView orderIsRoom;
	private TextView totalprice;
	private Button sureBtn;
	private ArrayList<Menu> menulistdata = new ArrayList<Menu>();
	private final int sure_what = 2;
	private Comment comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commentdetail);
		comment = (Comment) getIntent().getSerializableExtra("comment");
		orderId = (TextView) findViewById(R.id.order_id);
		orderTime = (TextView) findViewById(R.id.create_time);
		orderName = (TextView) findViewById(R.id.person);
		orderPhone = (TextView) findViewById(R.id.phone);
		orderPeople = (TextView) findViewById(R.id.people);
		orderIsRoom = (TextView) findViewById(R.id.isroom);
		totalprice = (TextView) findViewById(R.id.price);
		sureBtn = (Button) findViewById(R.id.sure);
		orderId.setText(comment.getOrder_id());
		orderTime.setText(CommonUtil.getTimestr(comment.getOrder_time()));
		orderName.setText(comment.getUser_name());
		orderPhone.setText(comment.getPhone());
		orderPeople.setText(comment.getPeople());
		totalprice.setText(comment.getTotal_price());
		orderIsRoom.setText(comment.getIs_room().equals("1") ? "包间" : "大厅");
		sureBtn.setOnClickListener(this);
		((ImageView) findViewById(R.id.back)).setOnClickListener(this);
		if (comment.getOrder_type().equals("schedule")) {
			String str1 = comment.getPhone().substring(4);
			orderPhone.setText(comment.getPhone().replace(str1, "********"));
		} else if (comment.getOrder_type().equals("point")) {
			String str1 = comment.getPhone().substring(4);
			orderPhone.setText(comment.getPhone().replace(str1, "********"));
		} else if (comment.getOrder_type().equals("group")) {
			if (!comment.getPhone().contains("")) {
				String str1 = comment.getPhone().substring(4);
				orderPhone
						.setText(comment.getPhone().replace(str1, "********"));
			}
			orderName.setText(comment.getUser_name().replace(
					comment.getUser_name(), "*******"));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.sure:
			Intent mIntent = new Intent(this, ReportActivity.class);
			mIntent.putExtra("comment", comment);
			startActivity(mIntent);
			break;
		default:
			break;
		}
	}

}

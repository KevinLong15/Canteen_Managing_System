/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fdcz.zsct.R;
import com.zsct.customer.adapter.TextAdapter;
import com.zsct.customer.adapter.TextAdapter.onSelectListener;
import com.zsct.customer.dao.UserDao;
import com.zsct.customer.model.Menu;
import com.zsct.customer.model.Shop;
import com.zsct.customer.model.User;
import com.zsct.customer.util.CommonUtil;
import com.zsct.customer.util.DatePickerView;
import com.zsct.customer.util.DatePickerView.onDateSelectedListener;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.PreferenceUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class ShopBookActivity extends BaseActivity {
	private TextView picktime;
	private TextView time_text;
	private TextView personnum_text;
	private TextView roomtype_text;
	private TextView bookbtn;
	private TextView livebtn;
	private TextView submitbtn;
	private RadioGroup mGroup;
	private EditText nameedit;
	private PopupWindow popupWindow;
	private LinearLayout personnumlin;
	private LinearLayout roomtypelin;
	private Shop mShop;
	private Date mDate;
	private String timestr;
	private String personnum;
	private String isroom;
	private String name;
	private User mUser;
	private boolean point;
	private String total_price;
	private String disheslist;
	private ArrayList<Menu> dishArr=new ArrayList<Menu>();
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			String result=msg.obj.toString();
			Log.e("hjq", "result="+result);
			closeLoadingDialog();
		switch (msg.what) {
		case 1:
			try {
			JSONObject object=new JSONObject(result);
				if (object.getString("code").equals("1")) {
					Intent shopIntent=new Intent(ShopBookActivity.this,OrderLocalDetailActivity.class);
					shopIntent.putExtra("object", mShop);
					String order_id=object.getString("order_id");
					shopIntent.putExtra("create_time", timestr);
					shopIntent.putExtra("order_id", order_id);
					shopIntent.putExtra("userName", name);
					shopIntent.putExtra("personnum",personnum_text.getText().toString() );
					startActivity(shopIntent);
					finish();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			break;

		case 2:
			try {
				JSONObject object = new JSONObject(result);
 		 if (object.getString(JsonUtil.CODE).equals("1")) {
 			 Intent shopIntent=new Intent(ShopBookActivity.this,OrderResultActivity.class);
				shopIntent.putExtra("object", mShop);
				String order_id=object.getString("order_id");
			    String  create_time=object.getString("create_time");
				shopIntent.putExtra("create_time", create_time);
				shopIntent.putExtra("order_id", order_id);
				shopIntent.putExtra("personnum",personnum);
				shopIntent.putExtra("type","point");
				shopIntent.putExtra("disheslist",dishArr);
				startActivity(shopIntent);
				finish();
 		 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		};		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_book);
		mShop=(Shop)getIntent().getSerializableExtra("object");
		point=getIntent().getBooleanExtra("point", false);
		View dateview = (View) findViewById(R.id.dateview);
		new DatePickerView(this, dateview, new onDateSelectedListener() {
			@Override
			public void onDateSelect(Date date) {
				// TODO Auto-generated method stub
				mDate=date;
			}
		}).getCurDateView();
		mDate=new Date();
		picktime = (TextView) findViewById(R.id.picktime);
		time_text = (TextView) findViewById(R.id.time_text);
		personnum_text = (TextView) findViewById(R.id.personnum_text);
		roomtype_text = (TextView) findViewById(R.id.room_type);
		bookbtn = (TextView) findViewById(R.id.bookmenubtn);
		livebtn = (TextView) findViewById(R.id.shopmenubtn);
		submitbtn = (TextView) findViewById(R.id.submit);
		mGroup=(RadioGroup)findViewById(R.id.nameafter);
		nameedit=(EditText)findViewById(R.id.nameEdit);
		personnumlin = (LinearLayout) findViewById(R.id.personnum_picker);
		roomtypelin = (LinearLayout) findViewById(R.id.room_id);
		((TextView)findViewById(R.id.title)).setText(mShop.getName());
		findViewById(R.id.back).setOnClickListener(this);
		if (mShop.getIs_point().equals("0")) {
			bookbtn.setEnabled(false);
		}
		if (point) {
			bookbtn.setVisibility(View.GONE);
			livebtn.setVisibility(View.GONE);
			submitbtn.setVisibility(View.VISIBLE);
			total_price=getIntent().getStringExtra("total_price");
			disheslist=getIntent().getStringExtra("disheslist");
			dishArr=(ArrayList<Menu>) getIntent().getSerializableExtra("dishArr");
		}else {
			bookbtn.setVisibility(View.VISIBLE);
			livebtn.setVisibility(View.VISIBLE);
			submitbtn.setVisibility(View.GONE);
		}
		bookbtn.setOnClickListener(this);
		livebtn.setOnClickListener(this);
		submitbtn.setOnClickListener(this);
		picktime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showPickTimePop(findViewById(R.id.lin2));
			}
		});
		personnumlin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPersonNumPop(findViewById(R.id.lin2));
			}
		});
		roomtypelin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showRoomPop(findViewById(R.id.lin2));
			}
		});
		mUser=new UserDao(this).queryById(PreferenceUtil.getInstance(this).getUid());
		if (mUser!=null) {
			nameedit.setText(mUser.getName());
			if (mUser.getSex().equals("1")) {
				mGroup.check(R.id.nameafter_male);
			}else {
				mGroup.check(R.id.nameafter_female);
			}
		}
	}

	private void showPickTimePop(View v) {
		// TODO Auto-generated method stub
		ArrayList<String> listdata = new ArrayList<String>();
		listdata.add("09:30");
		listdata.add("10:00");
		listdata.add("10:30");
		listdata.add("11:00");
		listdata.add("11:30");
		listdata.add("12:00");
		listdata.add("12:30");
		listdata.add("13:00");
		listdata.add("13:30");
		listdata.add("14:00");
		listdata.add("14:30");
		listdata.add("15:00");
		listdata.add("15:30");
		listdata.add("16:00");
		listdata.add("16:30");
		listdata.add("17:00");
		listdata.add("17:30");
		listdata.add("18:00");
		listdata.add("18:30");
		listdata.add("19:00");
		listdata.add("19:30");
		listdata.add("20:00");
		listdata.add("20:30");
		listdata.add("21:00");
		listdata.add("21:30");
		listdata.add("22:00");
		ArrayList<String> temp=new ArrayList<String>();
	    for (int i = 0; i < listdata.size(); i++) {
	   	 SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd ");
		 String a1=dateformat1.format(mDate);
            String str =a1+listdata.get(i);
            Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
				Date mydate= (Date) f.parseObject(str);
				if (new Date().after(mydate)) {
					temp.add(listdata.get(i));
					Log.e("hjq", "temp="+listdata.get(i));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    listdata.removeAll(temp);
		LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundColor(Color.WHITE);
		ListView lv = new ListView(this);
		lv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		TextAdapter timeadapter = new TextAdapter(this, listdata);
		timeadapter.setonSelectListener(new onSelectListener() {
			@Override
			public void select(int position, String content) {
				// TODO Auto-generated method stub
				time_text.setText(content);
				// Toast.makeText(ShopBookActivity.this, content, 1).show();
				popupWindow.dismiss();
				 try {
					 SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd ");
					 String a1=dateformat1.format(mDate);
			            String str =a1+time_text.getText().toString().trim();
			            Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			            mDate= (Date) f.parseObject(str);
			            Log.e("hjq", mDate.toString());
			        } catch (ParseException e) {
			            e.printStackTrace();
			        }
			}
		});
		lv.setAdapter(timeadapter);
		layout.addView(lv);
		popupWindow = new PopupWindow(layout, v.getWidth() / 3,
				v.getHeight()*2 / 3);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(v, Gravity.RIGHT | Gravity.CENTER_VERTICAL,
				0, 0);
	}

	private void showPersonNumPop(View v) {
		// TODO Auto-generated method stub
		ArrayList<String> listdata = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			listdata.add((i+1)+"人");
		}
		LinearLayout layout = (LinearLayout) getLayoutInflater().from(this)
				.inflate(R.layout.popup_personnum, null);
		ListView lv = (ListView) layout.findViewById(R.id.personnum_list);
		TextAdapter timeadapter = new TextAdapter(this, listdata);
		timeadapter.setonSelectListener(new onSelectListener() {
			@Override
			public void select(int position, String content) {
				// TODO Auto-generated method stub
				personnum_text.setText(content);
				if (popupWindow!=null&&popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}
		});
		lv.setAdapter(timeadapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(ShopBookActivity.this, "1212", 1).show();
			}
		});
		popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				v.getHeight() / 2);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(v, Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0);
	}
	private void showRoomPop(View v) {
		// TODO Auto-generated method stub
		ArrayList<String> listdata = new ArrayList<String>();
		listdata.add(getString(R.string.shop_book_booth));
		listdata.add(getString(R.string.shop_book_dating));
		LinearLayout layout = (LinearLayout) getLayoutInflater().from(this)
				.inflate(R.layout.popup_personnum, null);
		ListView lv = (ListView) layout.findViewById(R.id.personnum_list);
		TextAdapter timeadapter = new TextAdapter(this, listdata);
		timeadapter.setonSelectListener(new onSelectListener() {
			@Override
			public void select(int position, String content) {
				// TODO Auto-generated method stub
				roomtype_text.setText(content);
				if (popupWindow!=null&&popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}
		});
		lv.setAdapter(timeadapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			}
		});
		popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				 LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(v, Gravity.CENTER
				| Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.bookmenubtn:
			if (checkdata()) {
			Intent bookIntent=new Intent(ShopBookActivity.this,ShopMenuActivity.class);
			bookIntent.putExtra("object", mShop);
			bookIntent.putExtra("schedule", true);
			bookIntent.putExtra("create_time", timestr);
			bookIntent.putExtra("personnum", personnum);
			bookIntent.putExtra("isroom",isroom);
			bookIntent.putExtra("name",name);
			startActivity(bookIntent);
			}
			break;
		case R.id.shopmenubtn:
			if (checkdata()) {
			if (!PreferenceUtil.getInstance(this).getUid().equals("")) {
				showLoadingDialog();
					ThreadPoolManager.getInstance().addTask(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String uid=PreferenceUtil.getInstance(ShopBookActivity.this).getUid();
							String store_id=mShop.getId();
							String people=personnum;
							String phone=new UserDao(ShopBookActivity.this).queryById(uid).getPhone();
							String order_time=timestr;
							String is_room=isroom;
							String username=name;
							Log.e("hjq", HttpUtil.getURlStr(HttpUtil.URL_SUBMITLOCALORDER,
									new BasicNameValuePair(JsonUtil.USER_ID, uid),
									new BasicNameValuePair(JsonUtil.STORE_ID, store_id),
									new BasicNameValuePair(JsonUtil.PEOPLE, people),
									new BasicNameValuePair(JsonUtil.PHONE, phone),
									new BasicNameValuePair(JsonUtil.ORDER_TIME, order_time),
									new BasicNameValuePair(JsonUtil.IS_ROOM, is_room),
									new BasicNameValuePair("userName", username)));
							String result=HttpUtil.post(HttpUtil.URL_SUBMITLOCALORDER,
									new BasicNameValuePair(JsonUtil.USER_ID, uid),
									new BasicNameValuePair(JsonUtil.STORE_ID, store_id),
									new BasicNameValuePair(JsonUtil.PEOPLE, people),
									new BasicNameValuePair(JsonUtil.PHONE, phone),
									new BasicNameValuePair(JsonUtil.ORDER_TIME, order_time),
									new BasicNameValuePair(JsonUtil.IS_ROOM, is_room),
									new BasicNameValuePair("userName", username));
							Message msg=new Message();
							msg.what=1;
							msg.obj=result;
							mHandler.sendMessage(msg);
				}
				});
			}else {
				Intent shopIntent=new Intent(ShopBookActivity.this,AuthLoginActivity.class);
				startActivity(shopIntent);
			}
			}
			break;
		case R.id.submit:
			if (checkdata()) {
				showLoadingDialog();
				 ThreadPoolManager.getInstance().addTask(new Runnable() {				
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String uid=PreferenceUtil.getInstance(ShopBookActivity.this)
									.getUid();
							String store_id=mShop.getId();
							String people=personnum;
							String phone=new UserDao(ShopBookActivity.this).queryById(uid).getPhone();
							String order_time=timestr;
							String is_room=isroom;
							String username=name;	
		                    String type=point?"point":"schedule";
							Log.e("hjq", "disheslist="+disheslist);
							Log.e("hjq", HttpUtil.getURlStr(HttpUtil.URL_SUBMITORDER,
									new BasicNameValuePair(JsonUtil.USER_ID, uid),
									new BasicNameValuePair(JsonUtil.STORE_ID, store_id),
									new BasicNameValuePair(JsonUtil.PEOPLE, people),
									new BasicNameValuePair(JsonUtil.PHONE, phone),
									new BasicNameValuePair(JsonUtil.ORDER_TIME, order_time),
									new BasicNameValuePair(JsonUtil.IS_ROOM, is_room),
									new BasicNameValuePair(JsonUtil.DISHESLIST, disheslist),
									new BasicNameValuePair(JsonUtil.TOTAL_PRICE, total_price),
									new BasicNameValuePair("userName", username),
									new BasicNameValuePair(JsonUtil.TYPE, type)));
			               
							String result=HttpUtil.post(HttpUtil.URL_SUBMITORDER,
									new BasicNameValuePair(JsonUtil.USER_ID, uid),
									new BasicNameValuePair(JsonUtil.STORE_ID, store_id),
									new BasicNameValuePair(JsonUtil.PEOPLE, people),
									new BasicNameValuePair(JsonUtil.PHONE, phone),
									new BasicNameValuePair(JsonUtil.ORDER_TIME, order_time),
									new BasicNameValuePair(JsonUtil.IS_ROOM, is_room),
									new BasicNameValuePair(JsonUtil.DISHESLIST, disheslist),
									new BasicNameValuePair(JsonUtil.TOTAL_PRICE, total_price),
									new BasicNameValuePair("userName", username),
									new BasicNameValuePair(JsonUtil.TYPE, type));
							Log.e("hjq", "result="+result);
							Message msg=new Message();
							msg.what=2;
							msg.obj=result;
							mHandler.sendMessage(msg);
						}
					});
			}
			break;
		default:
			break;
		}
	}

	private boolean checkdata() {
		// TODO Auto-generated method stub
		if (time_text.getText().toString().trim().equals("")) {
			showShortToast("请选择时间");
			return false;
		}
		if (personnum_text.getText().toString().trim().equals(getResources().getString(R.string.shop_book_personnum))) {
			showShortToast("请选择人数");
			return false;
		}
		if (roomtype_text.getText().toString().trim().equals(getResources().getString(R.string.shop_book_pickbooth))) {
			showShortToast("请选择是否包间");
			return false;
		}
		if (nameedit.getText().toString().trim().equals("")) {
			showShortToast("请输入姓名");
			return false;
		}
		if (!mDate.after(new Date(System.currentTimeMillis()))) {
			showShortToast("只能选择当前时间之后的");
			return false;
		}
		if (PreferenceUtil.getInstance(this).getUid().equals("")) {
			showShortToast("请先登录");
			startActivity(new Intent(this, AuthLoginActivity.class));
			return false;
		}
		 SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd ");
		 String a1=dateformat1.format(mDate);
		 try {
	            String str =a1+time_text.getText().toString().trim();
	            Format f = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	            Date d = (Date) f.parseObject(str);
	            Log.e("hjq", d.toString());
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		 
	     timestr=a1+time_text.getText().toString().trim()+" "+CommonUtil.getWeekOfDate(mDate);
		 personnum=personnum_text.getText().toString().substring(0, 1);
		 isroom=roomtype_text.getText().toString().trim().equals(getString(R.string.shop_book_booth))?"1":"0";
	     name=nameedit.getText().toString()+((RadioButton)findViewById(mGroup.getCheckedRadioButtonId())).getText().toString();
		 return true;
	}

}

/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.app.MyApplication;
import com.zsct.customer.dao.UserDao;
import com.zsct.customer.model.Dish;
import com.zsct.customer.model.Menu;
import com.zsct.customer.model.Order;
import com.zsct.customer.model.Shop;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.ImageLoaderUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.PreferenceUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class ShopMenuAddSumActivity extends BaseActivity  {
	private ListView mListView;
	private TextView summary;
	private TextView submit;
	private TextView nametv;
	private TextView phonetv;
	private int[] nums;
	private MenusumAdapter sumAdapter;
    private ArrayList<Dish> dishArr=new ArrayList<Dish>();
    private double sum = 0;
    private String name="";
    private String disheslist="";
    private String store_name="";
    private Order mOrder;
    private ImageView orderlist;
    private Handler mHandler=new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		String result=msg.obj.toString();
    		Log.e("hjq", result);
    		closeLoadingDialog();
    		try {	
    		JSONObject object = new JSONObject(result);
    		 if (object.getString(JsonUtil.CODE).equals("1")) {
    			 showLongToast("加菜成功");
    			 MyApplication.getInstance().type=1;
    			 startActivity(new Intent(ShopMenuAddSumActivity.this, MainActivity.class));
 				 finish();
 			}else {
				showLongToast(object.getString(JsonUtil.MSG));
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
		setContentView(R.layout.activity_shop_menusum);
		Intent bef=getIntent();
		store_name=bef.getStringExtra("store_name");
		mOrder=(Order)bef.getSerializableExtra("order");
		dishArr=(ArrayList<Dish>)getIntent().getSerializableExtra("list");
		mListView = (ListView) findViewById(R.id.menusum_lv);
		summary = (TextView) findViewById(R.id.summary);
		nametv = (TextView) findViewById(R.id.personname);
		phonetv = (TextView) findViewById(R.id.personphone);
		submit = (TextView) findViewById(R.id.submit);
		orderlist = (ImageView) findViewById(R.id.orderlist);
		if (name.equals("")) {
			nametv.setText(new UserDao(this).queryById(PreferenceUtil.getInstance(this).getUid()).getName());
		}else {
			nametv.setText(name);
		}
		phonetv.setText(new UserDao(this).queryById(PreferenceUtil.getInstance(this).getUid()).getPhone());
		((TextView)findViewById(R.id.title)).setText(store_name);;
		findViewById(R.id.back).setOnClickListener(this);
		submit.setOnClickListener(this);
		orderlist.setOnClickListener(this);
		nums = new int[dishArr.size()];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = 1;
		}
		freshData();
		sumAdapter = new MenusumAdapter(this);
		mListView.setAdapter(sumAdapter);
	   
	}

	class MenusumAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context context;

		public MenusumAdapter(Context context) {
			// TODO Auto-generated constructor stub
			mInflater = getLayoutInflater().from(ShopMenuAddSumActivity.this);
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dishArr.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return dishArr.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder mHolder = null;
			if (convertView == null) {
				mHolder = new Holder();
				convertView = mInflater.inflate(R.layout.menusum_item, null);
				mHolder.image = (ImageView) convertView
						.findViewById(R.id.menu_image);
				mHolder.name = (TextView) convertView
						.findViewById(R.id.menu_name);
				mHolder.price = (TextView) convertView
						.findViewById(R.id.menu_price);
				mHolder.discount = (ImageView) convertView
						.findViewById(R.id.menu_discount);
				mHolder.num = (TextView) convertView
						.findViewById(R.id.menu_num);
				mHolder.sub = (ImageView) convertView.findViewById(R.id.sub);
				mHolder.add = (ImageView) convertView.findViewById(R.id.add);
				convertView.setTag(mHolder);
			} else {
				mHolder = (Holder) convertView.getTag();
			}
			ImageLoaderUtil.displayImage(HttpUtil.SERVER+dishArr.get(position).getImage(),
					mHolder.image, context);
			mHolder.name.setText(dishArr.get(position).getDishes_name());
			mHolder.price.setText("¥" + dishArr.get(position).getPrice() + "/个");
			if (dishArr.get(position).getDiscount().equals("1")) {
				mHolder.discount.setVisibility(View.VISIBLE);
			} else {
				mHolder.discount.setVisibility(View.INVISIBLE);
			}
			mHolder.num.setText(String.valueOf(nums[position]));
			mHolder.sub.setTag(position);
			mHolder.sub.setOnClickListener(ShopMenuAddSumActivity.this);
			mHolder.add.setTag(position);
			mHolder.add.setOnClickListener(ShopMenuAddSumActivity.this);
			return convertView;
		}

		private class Holder {
			public ImageView image;
			public TextView name;
			public TextView price;
			public ImageView discount;
			public TextView num;
			public ImageView sub;
			public ImageView add;
		}
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
			startActivity(new Intent(this,MainActivity.class));
			finish();
			break;
		case R.id.sub:
			int subpos = (Integer) v.getTag();
			if (nums[subpos] > 1) {
				nums[subpos] = nums[subpos] - 1;
				sumAdapter.notifyDataSetChanged();
				freshData();
			}
			break;
		case R.id.add:
			int addpos = (Integer) v.getTag();
			nums[addpos] = nums[addpos] + 1;
			sumAdapter.notifyDataSetChanged();
			freshData();
			break;
		case R.id.submit:
			int num=0;
			for (int i = 0; i < nums.length; i++) {
				num=num+nums[i];
			}
		    if (num==0) {
				showLongToast("还没有选择任何菜");
				return  ;
			}
		    	showLoadingDialog();
		    	ThreadPoolManager.getInstance().addTask(new Runnable() {				
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String uid=PreferenceUtil.getInstance(ShopMenuAddSumActivity.this)
								.getUid();
						try {
						     disheslist=getDataByJson();
							Log.e("hjq", "disheslist="+disheslist);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.e("hjq",HttpUtil.getURlStr(HttpUtil.URL_ADDFOODBYORDERID,
								new BasicNameValuePair(JsonUtil.DISHESLIST, disheslist),
								new BasicNameValuePair(JsonUtil.ORDER_ID,mOrder.getOrder_id())));
						String result=HttpUtil.post(HttpUtil.URL_ADDFOODBYORDERID,
								new BasicNameValuePair(JsonUtil.DISHESLIST, disheslist),
								new BasicNameValuePair(JsonUtil.ORDER_ID,mOrder.getOrder_id()  ));
				
						Message msg=new Message();
						msg.obj=result;
						mHandler.sendMessage(msg);
					}
				});
			
//			startActivity(new Intent(this, OrderDetailActivity.class));
			break;
		}
	}
	private String getDataByJson() throws JSONException {
		JSONArray array=new JSONArray();
		for (int i = 0; i < dishArr.size(); i++) {
			JSONObject object=new JSONObject();
			object.put(JsonUtil.DISHES_ID, dishArr.get(i).getId());
			object.put(JsonUtil.COUNT, nums[i]);
			object.put(JsonUtil.PRICE, Double.parseDouble(dishArr.get(i).getPrice())*nums[i]);
			array.put(object);
		}
		return array.toString();
	}
	private ArrayList<Menu> getMenuData(){
		ArrayList<Menu> array=new ArrayList<Menu>();
		for (int i = 0; i < dishArr.size(); i++) {
			Dish mDish=dishArr.get(i);
			array.add(new Menu(mDish.getDishes_name(),Double.parseDouble(mDish.getPrice())*nums[i]));
		}
		return array;
	}
	private void freshData() {
		int num=0;
		sum=0;
		for (int i = 0; i < dishArr.size(); i++) {
			sum = sum + Double.parseDouble(dishArr.get(i).getPrice())* nums[i];
			num=num+nums[i];
		}
		summary.setText("共计" + num + "个菜，¥" + sum);
	}
}

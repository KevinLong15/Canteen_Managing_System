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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fdcz.zsct.R;
import com.zsct.customer.adapter.DishListAdapter;
import com.zsct.customer.model.Dish;
import com.zsct.customer.model.Shop;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class ShopMenuSearchActivity extends BaseActivity {
    private String  mShopId;
    private EditText searchView;
    private ImageButton searchbtn;
    private ListView dishlv;
    private DishListAdapter mListAdapter;
    private String searchstr="";
    private ArrayList<Dish> dishes = new ArrayList<Dish>();
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result=msg.obj.toString();
			  if (result.trim().equals("null")) {
				    showShortToast("没有找到数据");
	            	   return ;	
				 }
			try {
				JSONArray dishArray = new JSONArray(result);
				dishes.clear();
				for (int i = 0; i < dishArray.length(); i++) {
					JSONObject object=dishArray.getJSONObject(i);
					String id=object.getString(JsonUtil.ID);
					String store_id=object.getString(JsonUtil.STORE_ID);
					String dishes_name=object.getString(JsonUtil.DISHES_NAME);
					String dishes_type=object.getString(JsonUtil.DISHES_TYPE);
					String image=object.getString(JsonUtil.IMAGE);
					String image_thumb=object.getString(JsonUtil.IMAGE_THUMB);
					String price=object.getString(JsonUtil.PRICE);
					String discount=object.getString(JsonUtil.DISCOUNT);
					String create_time=object.getString(JsonUtil.CREATE_TIME);
					dishes.add(new Dish(id, store_id, dishes_name, dishes_type, image, image_thumb, price, discount, create_time,false));
//					Log.e("hjq", "dishArray="+new Dish(id, store_id, dishes_name, dishes_type, image, image_thumb, price, discount, create_time,false).toString());
				}
				mListAdapter = new DishListAdapter(ShopMenuSearchActivity.this, dishes);
				dishlv.setAdapter(mListAdapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_menusearch);
		mShopId =  getIntent().getStringExtra("object");
		searchView=(EditText)findViewById(R.id.search);
		dishlv = (ListView) findViewById(R.id.menu_list);
		searchbtn=(ImageButton)findViewById(R.id.seachbtn);
		searchbtn.setOnClickListener(this);
		dishlv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Dish mDish=(Dish)mListAdapter.getItem(position);
				boolean select=mDish.isIsselect();
				select=!select;
				mDish.setIsselect(select);
				mListAdapter.refresh();
			}
		});
	}
   @Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	Intent aintent = new Intent(ShopMenuSearchActivity.this,ShopMenuActivity.class);
	ArrayList<Dish> temp = new ArrayList<Dish>();
	for (int i = 0; i < dishes.size(); i++) {
		if (dishes.get(i).isIsselect()) {
			temp.add(dishes.get(i));
		}
	}
	aintent.putExtra("data", temp);
	setResult(RESULT_OK,aintent);
	finish();
	super.onBackPressed();
}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.seachbtn:
			 searchstr=searchView.getText().toString().trim();
			if (searchstr.equals("")) {
				showShortToast("请输入菜品名称");
				return ;
			}
			ThreadPoolManager.getInstance().addTask(new 
					Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String result=HttpUtil.post(HttpUtil.URL_SEARCHDISHESBYSTORE, 
									new BasicNameValuePair(JsonUtil.STOREID, mShopId),
									new BasicNameValuePair(JsonUtil.NAME, searchstr));
						Log.e("hjq", "result="+result);
						Message msg=new Message();
						msg.obj=result;
						mHandler.sendMessage(msg);
						}
					});
			break;
		}
	}
}

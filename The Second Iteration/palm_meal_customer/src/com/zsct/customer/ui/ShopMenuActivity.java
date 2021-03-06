/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.ui;

import java.text.DecimalFormat;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.adapter.CategoryAdapter;
import com.zsct.customer.adapter.DishListAdapter;
import com.zsct.customer.model.Category;
import com.zsct.customer.model.Dish;
import com.zsct.customer.model.Shop;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.PreferenceUtil;
import com.zsct.customer.util.ThreadPoolManager;

public class ShopMenuActivity extends BaseActivity {
	private ListView categorylv;
	private ListView dishlv;
	private LinearLayout searchtext;
	private TextView categoryText;
	private TextView count_num;
	private TextView count_sum;
	private TextView submit;
	private CategoryAdapter categoryAdapter;
	private DishListAdapter dishAdapter;
	private Shop mShop;
	private ArrayList<Category> categories = new ArrayList<Category>();
	private ArrayList<Dish> dishes = new ArrayList<Dish>();
	private final int find_what = 0;
	private final int search_what = 1;
	private String create_time="";
	private String personnum="";
	private String isroom="";
	private String name="";
	private boolean schedule=false;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result=msg.obj.toString();
			switch (msg.what) {
			case find_what:
				Log.e("hjq", "find_what="+result);
               if (result.trim().equals("null")) {
			    showShortToast("没有找到数据");
            	   return ;	
			 }
				try {
					JSONObject json=new JSONObject(result);
					JSONArray cataArray=json.getJSONArray("dishestype");
					for (int i = 0; i < cataArray.length(); i++) {
						JSONObject object=cataArray.getJSONObject(i);
						String id=object.getString(JsonUtil.ID);
						String name=object.getString(JsonUtil.NAME);
						String store_id=object.getString(JsonUtil.STORE_ID);
						String order=object.getString(JsonUtil.ORDER);
						categories.add(new Category(id, name, store_id, order,0));
						Log.e("hjq", "cataArray="+new Category(id, name, store_id, order,0).toString());
					}
					
					JSONArray dishArray=json.getJSONArray("disheslist");
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
						Log.e("hjq", "dishArray="+new Dish(id, store_id, dishes_name, dishes_type, image, image_thumb, price, discount, create_time,false).toString());
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                categoryAdapter = new CategoryAdapter(ShopMenuActivity.this, categories);
				categorylv.setAdapter(categoryAdapter);
				dishAdapter = new DishListAdapter(ShopMenuActivity.this, dishes);
				dishlv.setAdapter(dishAdapter);
				break;
			case search_what:

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_menu);
		mShop = (Shop) getIntent().getSerializableExtra("object");
		categorylv = (ListView) findViewById(R.id.category_list);
		dishlv = (ListView) findViewById(R.id.menu_list);
		searchtext = (LinearLayout) findViewById(R.id.category_search);
		categoryText = (TextView) findViewById(R.id.category_title);
		count_num = (TextView) findViewById(R.id.count_num);
		count_sum = (TextView) findViewById(R.id.count_sum);
		submit = (TextView) findViewById(R.id.submit);
		((TextView)findViewById(R.id.title)).setText(mShop.getName());;
		findViewById(R.id.back).setOnClickListener(this);
		searchtext.setOnClickListener(this);
		submit.setOnClickListener(this);
		Intent bef=getIntent();
		schedule=bef.getBooleanExtra("schedule", false);
		if(schedule ) {
			Log.e("hjq","schedule=true");
			schedule=true;
			create_time=bef.getStringExtra("create_time");
			Log.e("hjq", bef.getStringExtra("create_time"));
			personnum=bef.getStringExtra("personnum");
			isroom=bef.getStringExtra("isroom");
			name=bef.getStringExtra("name");
		}
		categorylv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e("hjq",categories.get(position).toString());
				categoryText.setText(categories.get(position).getName());
				dishAdapter.showCatalist(categories.get(position).getId());
			}
		});
		dishlv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Dish mDish=(Dish)dishAdapter.getItem(position);
				boolean select=mDish.isIsselect();
				select=!select;
				mDish.setIsselect(select);
				dishAdapter.refresh();
			    categoryAdapter.setNumber(mDish.getDishes_type(), select);
			    countSum();
			}
		});
		ThreadPoolManager.getInstance().addTask(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = HttpUtil.post(HttpUtil.URL_FINDDISHESBYPOINT,
						new BasicNameValuePair(JsonUtil.STOREID, mShop.getId()));
				Message msg=new Message();
				msg.what=find_what;
				msg.obj=result;
				mHandler.sendMessage(msg);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.category_search:
			Intent mIntent=new Intent(ShopMenuActivity.this,
					ShopMenuSearchActivity.class);
			mIntent.putExtra("object", mShop.getId());
			startActivityForResult(mIntent, 1);
			break;
		case R.id.submit:
			ArrayList<Dish> sendList=new ArrayList<Dish>();
			for (int i = 0; i < dishes.size(); i++) {
				if (dishes.get(i).isIsselect()) {
					if (dishes.get(i).getDiscount().equals("1")) {
						Double discount=Double.valueOf(mShop.getDiscount())/100;
						Dish mDish=dishes.get(i);
						DecimalFormat deformat=new DecimalFormat("0.0");
						mDish.setPrice(Double.parseDouble(deformat.format(Double.parseDouble(dishes.get(i).getPrice())*discount))+"");
						sendList.add(mDish);
					}else {
						sendList.add(dishes.get(i));
					}
					
				}
			}
			if (sendList.size()==0) {
				showShortToast("没有选中任何菜单哦");
				return ;
			}
			if (PreferenceUtil.getInstance(this).getUid().equals("")) {
				Intent loginintent=new Intent(this,AuthLoginActivity.class);
				startActivity(loginintent);
				return ;
			}
			Intent sendIntent=new Intent(ShopMenuActivity.this,ShopMenuSumActivity.class);
			sendIntent.putExtra("schedule", schedule);
			sendIntent.putExtra("create_time",create_time);
			sendIntent.putExtra("personnum", personnum);
			sendIntent.putExtra("isroom",isroom);
			sendIntent.putExtra("name", name);
			sendIntent.putExtra("list", sendList);
			sendIntent.putExtra("object",mShop);
			startActivity(sendIntent);
			break;
		}
		
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { 
		case RESULT_OK:
		ArrayList<Dish> searchlist=(ArrayList<Dish>)data.getSerializableExtra("data");
		for (int i = 0; i < searchlist.size(); i++) {
			for (int j = 0; j < dishes.size(); j++) {
				if (searchlist.get(i).getId().equals(dishes.get(j).getId())) {
					if (!dishes.get(j).isIsselect()) {
						dishes.get(j).setIsselect(true);
						dishAdapter.refresh();
					    categoryAdapter.setNumber(dishes.get(j).getDishes_type(), true);
					}
				}
			}
		}
		countSum();
		  break;
		default:
		          break;
		}
		}

	private void countSum() {
		// TODO Auto-generated method stub
		int num=0;
		double sum=0.0;
		for (int i = 0; i < dishes.size(); i++) {
			if (dishes.get(i).isIsselect()) {
				if (dishes.get(i).getDiscount().equals("1")) {
					Double discount=Double.valueOf(mShop.getDiscount())/100;
					sum=sum+Double.parseDouble(dishes.get(i).getPrice())*discount;
				}else {
					sum=sum+Double.parseDouble(dishes.get(i).getPrice());
				}
				num++;
			}
		}
		count_num.setText("共"+num+"道菜");
		DecimalFormat deformat=new DecimalFormat("0.0");
		sum=Double.parseDouble(deformat.format(sum));
		count_sum.setText(getResources().getString(R.string.system_rmb)+sum);
	}
}

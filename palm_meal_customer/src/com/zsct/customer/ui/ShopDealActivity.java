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
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.model.Deal;
import com.zsct.customer.model.Shop;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.ImageLoaderUtil;
import com.zsct.customer.util.JsonUtil;
import com.zsct.customer.util.ThreadPoolManager;
import com.zsct.customer.xlistview.XListView;
import com.zsct.customer.xlistview.XListView.IXListViewListener;

public class ShopDealActivity extends BaseActivity implements IXListViewListener {
	private XListView mListView;
	private TextView name;
	private TextView summary;
	private DealAdapter mAdapter;
	private ArrayList<Deal> deals = new ArrayList<Deal>();
    private Shop mShop;
    private Runnable DealRun;
    private Handler mHandler=new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		String result=msg.obj.toString();
    		try {
				JSONArray jsonarr=new JSONArray(result);
				deals.clear();
				for (int i = 0; i < jsonarr.length(); i++) {
					JSONObject obj=jsonarr.getJSONObject(i);
					String id=obj.getString(JsonUtil.ID);
					String store_id=obj.getString(JsonUtil.STORE_ID);
					String title=obj.getString(JsonUtil.TITLE);
					String content=obj.getString(JsonUtil.CONTENT);
					String image=obj.getString(JsonUtil.IMAGE);
					String image_thumb=obj.getString(JsonUtil.IMAGE_THUMB);
					String old_price=obj.getString(JsonUtil.OLD_PRICE);
					String start_time=obj.getString(JsonUtil.START_TIME);
					String end_time=obj.getString(JsonUtil.END_TIME);
					String score=obj.getString(JsonUtil.SCORE);
					String dishes_id=obj.getString(JsonUtil.DISHES_ID);
					String group_price=obj.getString(JsonUtil.GROUP_PRICE);
					String store_name=obj.getString(JsonUtil.STORE_NAME);
					String address=obj.getString(JsonUtil.ADDRESS);
					String store_phone=obj.getString(JsonUtil.STORE_PHONE);
					String is_sales_count=obj.getString("is_sales_count");
					deals.add(new Deal(id, store_id, title, content, image, image_thumb, old_price, start_time, end_time, score, dishes_id, group_price, store_name, address, store_phone,is_sales_count));
				}
				mAdapter = new DealAdapter(ShopDealActivity.this);
				mListView.setAdapter(mAdapter);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		mListView.stopRefresh();
    	};
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deal);
		mShop=(Shop)getIntent().getSerializableExtra("object");
		mListView = (XListView) findViewById(R.id.shopgrouppurchaseList);
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(this);
		name = (TextView) findViewById(R.id.name);
		summary = (TextView) findViewById(R.id.summary);
		findViewById(R.id.back).setOnClickListener(this);
		name.setText(mShop.getName());
		summary.setText(mShop.getRoutes());
		DealRun=new Runnable() {		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result=HttpUtil.post(HttpUtil.URL_GETGROUPBYSTORE,
						new BasicNameValuePair(JsonUtil.STORE_ID,mShop.getId()));
				Log.e("hjq", result);
				Message msg=new Message();
				msg.obj=result;
				mHandler.sendMessage(msg);
				
			}
		};
		ThreadPoolManager.getInstance().addTask(DealRun);
		findViewById(R.id.back).setOnClickListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent detailin=new Intent(ShopDealActivity.this,
						ShopDealDetailActivity.class);
				detailin.putExtra("object", mShop);
				detailin.putExtra("deal", deals.get(position-1));
				startActivity(detailin);
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
	}
	
}
	class DealAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context context;

		public DealAdapter(Context context) {
			super();
			this.context = context;
			this.mInflater = getLayoutInflater().from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return deals.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return deals.get(position);
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
			Deal deal = deals.get(position);
			if (convertView == null) {
				mHolder = new Holder();
				convertView = mInflater.inflate(R.layout.deal_item, null);
				mHolder.name = (TextView) convertView
						.findViewById(R.id.menu_name);
				mHolder.image = (ImageView) convertView
						.findViewById(R.id.menu_image);
				mHolder.price = (TextView) convertView
						.findViewById(R.id.menu_price);
				mHolder.orgin_price = (TextView) convertView
						.findViewById(R.id.menu_price_orgin);
				mHolder.isreserved = (ImageView) convertView
						.findViewById(R.id.menu_image_reserve);
				mHolder.score = (TextView) convertView
						.findViewById(R.id.menu_score);
				mHolder.num = (TextView) convertView
						.findViewById(R.id.menu_num);
				convertView.setTag(mHolder);
			} else {
				mHolder = (Holder) convertView.getTag();
			}
			mHolder.name.setText(deal.getTitle());
			ImageLoaderUtil.displayImage(HttpUtil.SERVER+deal.getImage_thumb(), mHolder.image,
					context);
			mHolder.price.setText(deal.getGroup_price());
			mHolder.orgin_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			mHolder.orgin_price.setText(deal.getOld_price()+getString(R.string.shop_momey));
//			if (!deal.isIsreserved()) {
				mHolder.isreserved.setVisibility(View.VISIBLE);
//			} else {
				mHolder.isreserved.setVisibility(View.GONE);
//			}

			mHolder.score.setText("已售"+deal.getIs_sales_count());
//			mHolder.num.setText("("+deal.getGroup_price()+")");
			return convertView;
		}

		private class Holder {
			private TextView name;
			private ImageView image;
			private TextView price;
			private TextView orgin_price;
			private ImageView isreserved;
			private TextView score;
			private TextView num;
		}
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		ThreadPoolManager.getInstance().addTask(DealRun);
	}
}

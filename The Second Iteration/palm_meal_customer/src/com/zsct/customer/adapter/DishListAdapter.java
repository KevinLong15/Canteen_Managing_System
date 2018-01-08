/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.model.Dish;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.ImageLoaderUtil;

public class DishListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Dish> data = new ArrayList<Dish>();
	private ArrayList<Dish> catadata = new ArrayList<Dish>();
	private String type_id="";
	public DishListAdapter(Context context, ArrayList<Dish> shoplist) {
		super();
		this.context = context;
		data = shoplist;
		catadata=data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return catadata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return catadata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataList dl = null;
		if (convertView == null) {
			dl = new DataList();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.dish_item, null);
			dl.image = (ImageView) convertView.findViewById(R.id.menu_image);
			dl.name = (TextView) convertView.findViewById(R.id.menu_name);
			dl.price = (TextView) convertView.findViewById(R.id.menu_price);
			dl.discount = (ImageView) convertView.findViewById(R.id.menu_discount);
			dl.select = (ImageView) convertView.findViewById(R.id.menu_select);
			convertView.setTag(dl);
		} else {
			dl = (DataList) convertView.getTag();
		}
		ImageLoaderUtil.displayImage(HttpUtil.SERVER+catadata.get(position).getImage(), dl.image,
				context);
		dl.name.setText(catadata.get(position).getDishes_name());
		dl.price.setText("¥" + catadata.get(position).getPrice() + "/个");
		if (catadata.get(position).getDiscount().equals("1")) {
			dl.discount.setVisibility(View.VISIBLE);
		} else {
			dl.discount.setVisibility(View.GONE);
		}
		if (catadata.get(position).isIsselect()) {
			dl.select.setVisibility(View.VISIBLE);
		}else {
			dl.select.setVisibility(View.INVISIBLE);
		}
		return convertView;

	}
    public void showCatalist(String type_id){
    	this.type_id=type_id;
    	ArrayList<Dish> temp=new ArrayList<Dish>();
    	for (int i = 0; i < data.size(); i++) {
    		Log.e("hjq","data.size()="+data.size());
			if (data.get(i).getDishes_type().equals(type_id)) {
				temp.add(data.get(i));
				Log.e("hjq",data.get(i).toString());
			}
		}
    	catadata=temp;
    	Log.e("hjq","data.size()="+data.size());
    	notifyDataSetChanged();
    }
    public void refresh(){
    	notifyDataSetChanged();
//    	for (int i = 0; i < data.size(); i++) {
//    		Log.e("hjq",data.get(i).toString());
//		}
//    	
    	if (type_id.equals("")) {
			catadata=data;
			notifyDataSetChanged();
		}else {
		 	showCatalist(type_id);
	    	notifyDataSetChanged();
		}
   
    }
	private class DataList {
		public ImageView image;
		public TextView name;
		public TextView price;
		public ImageView discount;
		public ImageView select;

	}

}

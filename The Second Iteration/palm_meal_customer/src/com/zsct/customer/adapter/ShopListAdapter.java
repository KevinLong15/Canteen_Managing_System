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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.model.Shop;
import com.zsct.customer.util.CommonUtil;
import com.zsct.customer.util.HttpUtil;
import com.zsct.customer.util.ImageLoaderUtil;
import com.zsct.customer.util.PreferenceUtil;

public class ShopListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Shop> data = new ArrayList<Shop>();
    private Double loclat=0.0;
    private Double loclon=0.0;
	public ShopListAdapter(Context context, ArrayList<Shop> shoplist) {
		super();
		this.context = context;
		data = shoplist;
		String latstr=PreferenceUtil.getInstance(context).getString(PreferenceUtil.LAT, "");
    	if (!latstr.equals("")) {
			loclat=Double.parseDouble(latstr);
		}
    	String lonstr=PreferenceUtil.getInstance(context).getString(PreferenceUtil.LON, "");
    	if (!lonstr.equals("")) {
    		loclon=Double.parseDouble(lonstr);
    	}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
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
					R.layout.shop_item, null);
			dl.image = (ImageView) convertView.findViewById(R.id.image);
			dl.name = (TextView) convertView.findViewById(R.id.name);
			dl.reserve = (ImageView) convertView.findViewById(R.id.reserve);
			dl.order = (ImageView) convertView.findViewById(R.id.order);
			dl.card = (ImageView) convertView.findViewById(R.id.card);
			dl.group = (ImageView) convertView.findViewById(R.id.group);
			dl.pay = (ImageView) convertView.findViewById(R.id.pay);
			dl.category = (TextView) convertView
					.findViewById(R.id.category_name);
			dl.distance = (TextView) convertView.findViewById(R.id.distance);
			convertView.setTag(dl);
		} else {
			dl = (DataList) convertView.getTag();
		}
		ImageLoaderUtil.displayImage(HttpUtil.SERVER
				+ data.get(position).getImage_thumb(), dl.image, context);
		dl.name.setText(data.get(position).getName());
		dl.category.setText("餐厅类别："+data.get(position).getType_name());
		dl.distance.setText(data.get(position).getTemp_distance()+"km");
//		if (loclat==0.0&&loclon==0.0) {
//			dl.distance.setText("暂无数据");
//		}else {
//			String lat=data.get(position).getLat();
//			String lon=data.get(position).getLon();
//			if (!lat.equals("")&&!lon.equals("")) {
//				Double latdou=Double.parseDouble(lat);
//				Double londou=Double.parseDouble(lon);
//				dl.distance.setText(CommonUtil.GetDistance(loclat, londou, loclat, loclon)+"km");
//			}
//		
//		}
		if (data.get(position).getIs_schedule().equals("1")) {
			dl.reserve.setVisibility(View.VISIBLE);
		}else {
			dl.reserve.setVisibility(View.GONE);
		}
		if (data.get(position).getIs_point().equals("1")) {
			dl.order.setVisibility(View.VISIBLE);
		}else {
			dl.order.setVisibility(View.GONE);
		}
		if (data.get(position).getIs_card().equals("1")) {
			dl.card.setVisibility(View.VISIBLE);
		}else {
			dl.card.setVisibility(View.GONE);
		}
		if (data.get(position).getIs_group().equals("1")) {
			dl.group.setVisibility(View.VISIBLE);
		}else {
			dl.group.setVisibility(View.GONE);
		}
		if (data.get(position).getIs_pay().equals("1")) {
			dl.pay.setVisibility(View.VISIBLE);
		}else {
			dl.pay.setVisibility(View.GONE);
		}
		return convertView;

	}
    public void chanageDistance(){
    	String latstr=PreferenceUtil.getInstance(context).getString(PreferenceUtil.LAT, "");
    	if (!latstr.equals("")) {
			loclat=Double.parseDouble(latstr);
		}
    	String lonstr=PreferenceUtil.getInstance(context).getString(PreferenceUtil.LON, "");
    	if (!lonstr.equals("")) {
    		loclon=Double.parseDouble(lonstr);
    	}
    	notifyDataSetChanged();
    }
	private class DataList {
		public TextView name;
		public TextView category;
		public TextView distance;
		public ImageView image;
		public ImageView reserve;
		public ImageView order;
		public ImageView card;
		public ImageView group;
		public ImageView pay;
	}

}

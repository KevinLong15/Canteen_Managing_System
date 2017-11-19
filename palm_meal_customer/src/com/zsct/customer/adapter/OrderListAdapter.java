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
import android.widget.TextView;

import com.fdcz.zsct.R;
import com.zsct.customer.model.OrderItem;
import com.zsct.customer.util.CommonUtil;

public class OrderListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<OrderItem> data = new ArrayList<OrderItem>();

	public OrderListAdapter(Context context, ArrayList<OrderItem> orderlist) {
		super();
		this.context = context;
		data = orderlist;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataList dl = null;
		if (convertView == null) {
			dl = new DataList();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.order_item, null);	
			dl.name = (TextView) convertView.findViewById(R.id.name);
			dl.order_time = (TextView) convertView.findViewById(R.id.time);
			dl.local = (TextView) convertView.findViewById(R.id.local);
			dl.state = (TextView) convertView.findViewById(R.id.state);
			convertView.setTag(dl);
		} else {
			dl = (DataList) convertView.getTag();
		}
		dl.name.setText(data.get(position).getStoreName());
		dl.order_time.setText(CommonUtil.getOrderTimestr(data.get(position).getCreate_time()));
		if (data.get(position).getIs_local().equals("1")) {
			dl.local.setVisibility(View.VISIBLE);
		}else {
			dl.local.setVisibility(View.GONE);
		}
		if (data.get(position).getType().equals("group")) {
			dl.state.setText(CommonUtil.getDealOrderStatus(data.get(position).getStatus()));
		}else {
			dl.state.setText(CommonUtil.getOrderStatus(data.get(position).getStatus()));
		}
		return convertView;
	}

	private class DataList {
		public TextView name;
		public TextView order_time;
		public TextView local;
		public TextView state;
	}

}

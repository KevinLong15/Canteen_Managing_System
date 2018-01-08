/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsct.store.R;
import com.zsct.store.model.Order;
import com.zsct.store.util.CommonUtil;

public class OrderListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Order> data = new ArrayList<Order>();

	public OrderListAdapter(Context context, ArrayList<Order> orderlist) {
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
			dl.order_id = (TextView) convertView.findViewById(R.id.order_id);
			dl.create_time = (TextView) convertView
					.findViewById(R.id.create_time);
			dl.order_time = (TextView) convertView
					.findViewById(R.id.order_time);
			convertView.setTag(dl);
		} else {
			dl = (DataList) convertView.getTag();
		}
		dl.order_id.setText(data.get(position).getOrder_id());
		dl.create_time.setText(CommonUtil.getTimestr(data.get(position)
				.getCreate_time()));
		dl.order_time.setText(CommonUtil.getTimestr(data.get(position)
				.getOrder_time()));
		return convertView;
	}

	private class DataList {
		public TextView order_id;
		public TextView create_time;
		public TextView order_time;
	}

}

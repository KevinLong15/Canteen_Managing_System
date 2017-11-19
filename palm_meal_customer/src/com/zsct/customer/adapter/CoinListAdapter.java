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
import com.zsct.customer.model.Coin;

public class CoinListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Coin> data = new ArrayList<Coin>();

	public CoinListAdapter(Context context, ArrayList<Coin> coinlist) {
		super();
		this.context = context;
		data = coinlist;
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
					R.layout.coin_item, null);
			dl.dtime = (TextView) convertView.findViewById(R.id.dtime);
			dl.add = (TextView) convertView.findViewById(R.id.add);
			dl.money = (TextView) convertView.findViewById(R.id.money);
			convertView.setTag(dl);
		} else {
			dl = (DataList) convertView.getTag();
		}

		dl.dtime.setText(data.get(position).getCreate_time());
		if (data.get(position).getType().equals("add")) {
			dl.add.setText("充值");
		}else {
			dl.add.setText("消费");
		}
		dl.money.setText(data.get(position).getShibi());
		return convertView;

	}

	private class DataList {
		public TextView dtime;
		public TextView add;
		public TextView money;
	}

}

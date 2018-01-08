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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fdcz.zsct.R;

public class SingleTextAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> slist;


	public SingleTextAdapter(Context mContext, ArrayList<String> slist) {
		super();
		this.mContext = mContext;
		this.slist = slist;
	}

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return slist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return slist.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView tv;
		if (convertview == null) {
			convertview = LayoutInflater.from(mContext).inflate(
					R.layout.single_text_item1, null);
			tv = (TextView) convertview.findViewById(R.id.text);
			convertview.setTag(tv);
		} else {
			tv = (TextView) convertview.getTag();
		}
		tv.setText(slist.get(position));
		return convertview;
	}

	public interface onSelectListener {
		public void select(int position, String content);
	}

}

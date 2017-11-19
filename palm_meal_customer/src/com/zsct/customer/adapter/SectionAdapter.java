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
import android.widget.ImageView;
import android.widget.TextView;

import com.fdcz.zsct.R;

public class SectionAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> itemList;
	private int selectid;
	private int secindex;

	public SectionAdapter(Context context, ArrayList<String> item,
			int selectid, int secindex) {
		this.context = context;
		this.itemList = item;
		this.selectid = selectid;
		this.secindex = secindex;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataList data = null;
		if (convertView == null) {
			data = new DataList();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.section_item, null);
			data.mText = (TextView) convertView.findViewById(R.id.name);
			data.mImage = (ImageView) convertView.findViewById(R.id.checkimg);
			convertView.setTag(data);
		} else {
			data = (DataList) convertView.getTag();
		}
		data.mText.setText(itemList.get(position));
		if (selectid == position) {
			data.mImage.setVisibility(View.VISIBLE);
			convertView.setBackgroundResource(R.color.border_gray);
			data.mText.setTextColor(0xff1398a7);
		} else {
			data.mImage.setVisibility(View.INVISIBLE);
			convertView.setBackgroundResource(R.drawable.white_btn_text);
			data.mText.setTextColor(Color.BLACK);
		}
		if (secindex == 2) {
			//
		}
		return convertView;
	}

	public void freshdata() {
		this.notifyDataSetChanged();
	}

	private class DataList {
		public TextView mText;
		public ImageView mImage;
	}

}

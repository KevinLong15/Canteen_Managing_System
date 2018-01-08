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
import com.zsct.customer.model.Comment;

public class CommentAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Comment> slist;
	private int selectid = 0;

	public CommentAdapter(Context mContext, ArrayList<Comment> slist) {
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
		Holder holder;
		if (convertview == null) {
			convertview = LayoutInflater.from(mContext).inflate(
					R.layout.comment_item, null);
			holder = new Holder();
			holder.title = (TextView) convertview.findViewById(R.id.username);
			holder.time = (TextView) convertview
					.findViewById(R.id.comment_time);
			holder.content = (TextView) convertview.findViewById(R.id.content);
			convertview.setTag(holder);
		} else {
			holder = (Holder) convertview.getTag();
		}
		holder.title.setText(slist.get(position).getUser_name());
		holder.time.setText(slist.get(position).getComment_time());
		holder.content.setText(slist.get(position).getContent());
		return convertview;
	}

	class Holder {
		public TextView title;
		public TextView time;
		public TextView content;
	}

}

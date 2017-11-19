/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fdcz.zsct.R;

public class DialogUtil {
	public static void showDialog(Context context, int titleid, int msgid,
			int leftbtnid, int rightbtnid, OnClickListener LeftOnClickListener,
			OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setCancelable(false);
		builder.setTitle(titleid);
		builder.setMessage(msgid)
				.setNegativeButton(leftbtnid, LeftOnClickListener)
				.setPositiveButton(rightbtnid, RightOnClickListener).create()
				.show();
	}

	public static void showDialog(Context context, String title, String msg,
			String leftbtn, String rightbtn,
			OnClickListener LeftOnClickListener,
			OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setCancelable(cancelable);
		builder.setTitle(title).setMessage(msg)
				.setNegativeButton(leftbtn, LeftOnClickListener)
				.setPositiveButton(rightbtn, RightOnClickListener).create()
				.show();
	}

	public static void showNoTitleDialog(Context context, int msgid,
			int leftbtnid, int rightbtnid, OnClickListener LeftOnClickListener,
			OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setCancelable(cancelable);
		builder.setMessage(msgid)
				.setNegativeButton(leftbtnid, LeftOnClickListener)
				.setPositiveButton(rightbtnid, RightOnClickListener).create()
				.show();
	}

	public static void showNoTitleDialog(Context context, String msg,
			String leftbtn, String rightbtn,
			OnClickListener LeftOnClickListener,
			OnClickListener RightOnClickListener, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setCancelable(cancelable);
		builder.setMessage(msg).setNegativeButton(leftbtn, LeftOnClickListener)
				.setPositiveButton(rightbtn, RightOnClickListener).create()
				.show();
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ProgressBar spaceshipImage = (ProgressBar) v.findViewById(R.id.loading);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		tipTextView.setText(msg);// 设置加载信息
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;

	}
}

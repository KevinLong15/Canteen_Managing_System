/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.util;

import android.content.Context;
import android.widget.ImageView;

import com.fdcz.zsct.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 加载图片的工具类
 * 
 * @author 黄家强
 */
public class ImageLoaderUtil {
	public static DisplayImageOptions options_image, options_progress,
			options_grid;
	private static ImageLoader imageLoader;

	private static void initLoader(Context context) {
		options_image = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.defaultpic)
				.showImageForEmptyUri(R.drawable.defaultpic).cacheInMemory()
				.cacheOnDisc().imageScaleType(ImageScaleType.POWER_OF_2)
				.displayer(new RoundedBitmapDisplayer(0xff424242, 10)).build();
		options_progress = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.defaultpic).cacheOnDisc()
				.imageScaleType(ImageScaleType.EXACT).build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options_grid = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.defaultpic)
				.showImageForEmptyUri(R.drawable.defaultpic).cacheInMemory()
				.cacheOnDisc().build();
	}

	public static ImageLoader getImageLoader(Context context) {
		if (imageLoader == null) {
			initLoader(context);
		}
		return imageLoader;
	}

	public static void displayImage(String url, ImageView imageView,
			Context context) {
		getImageLoader(context).displayImage(url, imageView, options_image);
	}

	public static void displayImage(String url, ImageView imageView,
			Context context, DisplayImageOptions options) {
		getImageLoader(context).displayImage(url, imageView, options);
	}

	public static void displayImage(String url, ImageView imageView,
			Context context, DisplayImageOptions options,
			ImageLoadingListener imageLoadingListener) {
		getImageLoader(context).displayImage(url, imageView, options,
				imageLoadingListener);
	}

	public static void stopload(Context context) {
		getImageLoader(context).stop();
	}
}

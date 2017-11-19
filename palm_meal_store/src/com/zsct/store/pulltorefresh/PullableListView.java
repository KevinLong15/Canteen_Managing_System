/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.store.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullableListView extends ListView implements Pullable {

	public PullableListView(Context context) {
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if (getCount() == 0) {
			// û��item��ʱ��Ҳ��������ˢ��
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0).getTop() >= 0) {
			// ����ListView�Ķ�����
			return true;
		} else
			return false;
	}

	@Override
	public boolean canPullUp() {
		if (getCount() == 0) {
			// û��item��ʱ��Ҳ������������
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			// �����ײ���
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
							getLastVisiblePosition()
									- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}
}

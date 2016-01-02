package org.succlz123.commoncode.tabbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by succlz123 on 2015/7/13.
 */
public class TabBarLayout extends LinearLayout {

	public TabBarLayout(Context context) {
		super(context);
 	}

	public TabBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
 	}

	public TabBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
 	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public TabBarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
 	}

	public static interface OnTabChangeListener {
		public void onTabSelected(int index);
	}

	private OnTabChangeListener mOnTabChangeListener;

	private ArrayList<TabItemView> mTabItemViewList = new ArrayList<>();

	public void setOnTabChangeListener(OnTabChangeListener mOnTabChangeListener) {
		this.mOnTabChangeListener = mOnTabChangeListener;
	}

	public void setTabSelected(int index){
		mChildOnClickListener.onClick(getChildAt(index));
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mTabItemViewList.clear();
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			if (child instanceof TabItemView) {
				TabItemView tabItemView = (TabItemView) child;
				mTabItemViewList.add(tabItemView);
				tabItemView.setOnClickListener(mChildOnClickListener);
			}
		}
	}

	private OnClickListener mChildOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!(v instanceof Checkable)) {
				return;
			}
			Checkable child = (Checkable) v;
			if (child.isChecked()) {
				return;
			}
			((Checkable) v).setChecked(true);
			if (TabBarLayout.this.mOnTabChangeListener != null) {
				TabBarLayout.this.mOnTabChangeListener.onTabSelected(TabBarLayout.this.indexOfChild(v));
			}
			for (TabItemView tabItemView : TabBarLayout.this.mTabItemViewList) {
				if (tabItemView != v) {
					tabItemView.setChecked(false);
				}
			}
		}
	};
}

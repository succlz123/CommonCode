package org.succlz123.commoncode.tabbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by succlz123 on 2015/7/10.
 */
public class TabItemView extends LinearLayout implements Checkable {
	private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
	private boolean mChecked = false;

	public TabItemView(Context context) {
		super(context);
	}

	public TabItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TabItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public TabItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}


	@Override
	public int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CHECKED_STATE_SET);
		}
		return drawableState;
	}

	@Override
	public boolean isChecked() {
		return mChecked;
	}

	@Override
	public void setChecked(boolean b) {
		if (b != mChecked) {
			mChecked = b;
			refreshDrawableState();
		}
	}

	@Override
	public void toggle() {
		setChecked(!mChecked);
	}
}

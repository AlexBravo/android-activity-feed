package com.bandsintown.activityfeed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class FeedItemSingleLikedActivity extends AbsFeedItemSingleView {

	private FrameLayout mLikedActivityContainer;

	public FeedItemSingleLikedActivity(Context context) {
		super(context);
	}

	public FeedItemSingleLikedActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FeedItemSingleLikedActivity(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void initLayout() {
		mLikedActivityContainer = (FrameLayout) findViewById(R.id.fila_liked_container);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.aaf_item_liked_activity;
	}

	public void addChildToLikedContainer(View child) {
		mLikedActivityContainer.addView(child);
	}

}

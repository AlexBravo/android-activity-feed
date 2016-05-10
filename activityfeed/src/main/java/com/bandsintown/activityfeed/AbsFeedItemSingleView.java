package com.bandsintown.activityfeed;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class AbsFeedItemSingleView extends CardView {

	protected FeedItemViewHeader mHeader;
	protected FeedItemSingleFooterView mFooter;

	public AbsFeedItemSingleView(Context context) {
		this(context, null);
	}

	public AbsFeedItemSingleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AbsFeedItemSingleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateLayout();
	}

	private void inflateLayout() {
		LayoutInflater.from(getContext()).inflate(getLayoutResId(), this, true);

		mHeader = (FeedItemViewHeader) findViewById(R.id.feed_item_header);
		mFooter = (FeedItemSingleFooterView) findViewById(R.id.feed_item_footer);

		ViewGroup.LayoutParams params = mFooter.getLayoutParams();
		params.height = (int) getResources().getDimension(R.dimen.activity_feed_footer_height);
		mFooter.setLayoutParams(params);

		setContentPadding(0, 0, 0, 0);
		setRadius(getResources().getDimension(R.dimen.cardview_default_radius));

		initLayout();
	}

	public FeedItemViewHeader getHeader() {
		return mHeader;
	}

	public FeedItemSingleFooterView getFooter() {
		return mFooter;
	}

	protected abstract void initLayout();

	protected abstract int getLayoutResId();

}

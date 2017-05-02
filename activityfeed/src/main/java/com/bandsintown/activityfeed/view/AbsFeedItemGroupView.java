package com.bandsintown.activityfeed.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bandsintown.activityfeed.R;

public abstract class AbsFeedItemGroupView extends CardView {

	protected FeedItemViewHeader mHeader;
	protected FeedItemGroupFooterView mFooter;

	public AbsFeedItemGroupView(Context context) {
		this(context, null);
	}

	public AbsFeedItemGroupView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AbsFeedItemGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateLayout();
	}

	private void inflateLayout() {
		LayoutInflater.from(getContext()).inflate(getLayoutResId(), this, true);

		mHeader = (FeedItemViewHeader) findViewById(R.id.feed_item_header);
		mFooter = (FeedItemGroupFooterView) findViewById(R.id.feed_item_footer);

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

	public FeedItemGroupFooterView getFooter() {
		return mFooter;
	}

	protected abstract void initLayout();

	protected abstract int getLayoutResId();

}

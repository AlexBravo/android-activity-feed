package com.bandsintown.activityfeed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bandsintown.activityfeed.objects.FeedGroupInterface;

public class FeedItemGroupFooterView extends RelativeLayout {

	private TextView mViewMore;
	private ImageView mLikeButton;

	public FeedItemGroupFooterView(Context context) {
		this(context, null);
	}

	public FeedItemGroupFooterView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FeedItemGroupFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initLayout();
	}

	public void setOptions(FeedViewOptions options) {
		if(options != null) {
			mLikeButton.setVisibility(options.isEnableLiking() ? VISIBLE : GONE);
		}
	}

	private void initLayout() {
		LayoutInflater.from(getContext()).inflate(R.layout.activity_feed_item_group_footer, this, true);

		mLikeButton = (ImageView) findViewById(R.id.afigf_like_button);
		mViewMore = (TextView) findViewById(R.id.afigf_view_more_text);

		setId(R.id.feed_item_footer);
		setMinimumHeight(R.dimen.activity_feed_footer_height);
	}

	public void setInitialState(FeedGroupInterface group) {
		mLikeButton.setSelected(group.isGroupLikedByUser());

		//TODO change view more text based on the type of activity group if needed
	}

	public void setLikeClickListener(OnClickListener listener) {
		mLikeButton.setOnClickListener(listener);
	}

	public void onLikeClick(boolean isLikedByUser) {
		//Inverse because we update the view before the bool
		mLikeButton.setSelected(!isLikedByUser);
	}

	public void setViewMoreOnClickListener(OnClickListener listener) {
		setOnClickListener(listener);
	}

	public void setViewMoreText(String text) {
		mViewMore.setText(text);
	}

}

package com.bandsintown.activityfeed;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;

public class FeedItemSingleFooterView extends RelativeLayout {

	private TextView mLikeCount;
	private ImageView mLikeButton;
	private ImageView mComment;

	private MenuItem mReportItem;
	private MenuItem mDeleteItem;
	private MenuItem mUntrackItem;

	private OnFeedFooterMenuClickListener mFeedMenuListener;
	private OnFeedFooterMenuButtonClickListener mFeedMenuButtonClickListener;

	public FeedItemSingleFooterView(Context context) {
		this(context, null);
	}

	public FeedItemSingleFooterView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FeedItemSingleFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateLayout();
	}

	private void inflateLayout() {
		LayoutInflater.from(getContext()).inflate(R.layout.aaf_feed_item_footer, this, true);

		mLikeCount = (TextView) findViewById(R.id.afif_like_count);
		mLikeButton = (ImageView) findViewById(R.id.afif_like_button);
		mComment = (ImageView) findViewById(R.id.afif_comment_button);
		ImageView menuButton = (ImageView) findViewById(R.id.afif_menu_image);
		View anchor = findViewById(R.id.afif_anchor_view);

		final PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
		popupMenu.inflate(R.menu.feed_item_actions_menu);

		mReportItem = popupMenu.getMenu().getItem(0);
		mDeleteItem = popupMenu.getMenu().getItem(1);
		mUntrackItem = popupMenu.getMenu().getItem(2);

		menuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mFeedMenuButtonClickListener != null)
					mFeedMenuButtonClickListener.onMenuButtonClick();

				if(popupMenu.getMenu().hasVisibleItems())
					popupMenu.show();
			}

		});

		setId(R.id.feed_item_footer);
		setMinimumHeight(R.dimen.activity_feed_footer_height);
	}

	public void setOptions(FeedViewOptions options) {
		if(options != null) {
			mComment.setVisibility(mComment.getVisibility() == VISIBLE && options.isCommentingEnabled() ? VISIBLE : GONE);
			mLikeButton.setVisibility(mLikeButton.getVisibility() == VISIBLE && options.isEnableLiking() ? VISIBLE : GONE);

			boolean reportVis = mReportItem.isVisible();
			mReportItem.setVisible(mReportItem.isVisible() && options.isEnableReporting());

			boolean deleteVis = mDeleteItem.isVisible();
			mDeleteItem.setVisible(mDeleteItem.isVisible() && options.isEnableDeleting());

			boolean untrackVis = mUntrackItem.isVisible();
			mUntrackItem.setVisible(mUntrackItem.isVisible() && options.isEnableUntracking());
		}
	}

	public void setLikeCount(int count) {
		mLikeCount.setText(getContext().getResources().getQuantityString(R.plurals.like_count, count, count));
		if(count > 0)
			showLikeCount();
		else
			hideLikeCount();
	}

	public void hideLikeCount() {
		mLikeCount.setVisibility(GONE);
	}

	public void showLikeCount() {
		mLikeCount.setVisibility(VISIBLE);
	}

	public void setLikeClickListener(OnClickListener listener) {
		mLikeButton.setOnClickListener(listener);
	}

	public void onLikeClick(boolean isLikedByUser, int likeCount) {
		if(isLikedByUser) { // unlike the post
			mLikeButton.setSelected(false);
			setLikeCount(likeCount - 1);
		}
		else {
			mLikeButton.setSelected(true);
			setLikeCount(likeCount + 1);
		}
	}

	public void setOnLikesTotalClickListener(OnClickListener listener) {
		mLikeCount.setOnClickListener(listener);
	}

	public void setInitialState(final FeedItemInterface feedItem, final IntentRouter router) {
		//Hide like button for payload items
		if(feedItem.getId() > 0) {
			mLikeButton.setSelected(feedItem.isLikedByUser());
			setLikeCount(feedItem.getLikeCount());
		}
		else {
			setLikeCount(0);
			mLikeButton.setVisibility(GONE);
		}

		int id;
		try {
			id = feedItem.getActor().getUser() != null ? feedItem.getActor().getUser().getId() :
					feedItem.getActor().getArtist() != null ? feedItem.getActor().getArtist().getId() : 0;
		}
		catch(Exception e) {
			id = 0;
		}

		if(id == FeedModule.getPreferences().getUserId()) {
			mDeleteItem.setVisible(true);
			mReportItem.setVisible(false);
		}
		else {
			mReportItem.setVisible(true);
			mDeleteItem.setVisible(false);
		}

		if(feedItem.getVerb().equals(FeedValues.VERB_MESSAGE_RSVPS) || (feedItem.getVerb().equals(FeedValues.VERB_USER_POST) && feedItem.getObject().getEventStub() != null))
			mUntrackItem.setVisible(false);
		else
			mUntrackItem.setVisible(true);

		//Set comment button if necessary
		if(feedItem.getObject().getEventStub() != null) {
			mComment.setVisibility(VISIBLE);
			mComment.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					router.onCommentClicked(feedItem);
				}

			});
		}
		else
			mComment.setVisibility(GONE);

		mReportItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if(mFeedMenuListener != null)
					mFeedMenuListener.onReportClick(feedItem.getId());

				return true;
			}

		});

		mDeleteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if(mFeedMenuListener != null)
					mFeedMenuListener.onDeleteClick(feedItem.getId());

				return true;
			}

		});

		mUntrackItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				if(mFeedMenuListener != null)
					mFeedMenuListener.onUntrackClick(feedItem);

				return false;
			}

		});
	}

	public void setFeedMenuItemClickListener(OnFeedFooterMenuClickListener listener) {
		mFeedMenuListener = listener;
	}

	public interface OnFeedFooterMenuClickListener {

		void onReportClick(int feedId);
		void onDeleteClick(int feedId);
		void onUntrackClick(FeedItemInterface feedItem);

	}

	public void setFeedMenuButtonClickListener(OnFeedFooterMenuButtonClickListener listener) {
		mFeedMenuButtonClickListener = listener;
	}

	public interface OnFeedFooterMenuButtonClickListener {
		void onMenuButtonClick();
	}

}

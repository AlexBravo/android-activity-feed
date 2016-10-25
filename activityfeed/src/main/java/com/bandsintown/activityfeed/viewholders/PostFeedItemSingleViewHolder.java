package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bandsintown.activityfeed.FeedItemSinglePost;
import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.audio.AudioStateItem;
import com.bandsintown.activityfeed.audio.AudioStateManager;
import com.bandsintown.activityfeed.audio.OnAudioStateChangeListener;
import com.bandsintown.activityfeed.interfaces.OnFeedMenuItemAdapterClickListener;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.interfaces.OnLinkClickListener;
import com.bandsintown.activityfeed.interfaces.Recycleable;
import com.bandsintown.activityfeed.objects.AudioPreviewInfo;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.objects.RecyclingPreviewViewHelper;

import java.util.Collections;

import static com.bandsintown.activityfeed.objects.RecyclingPreviewViewHelper.IMAGE_CLICK;
import static com.bandsintown.activityfeed.objects.RecyclingPreviewViewHelper.ITEM_CLICK;

public class PostFeedItemSingleViewHolder extends AbsActivityFeedSingleViewHolder
		implements Recycleable, OnAudioStateChangeListener {

	private FeedItemSinglePost mItem;
	private RecyclingPreviewViewHelper mAudioPreviewHelper;

	public PostFeedItemSingleViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
		super(activity, options, itemView);
		mItem = (FeedItemSinglePost) mView;
	}

	@Override
	public void buildItem(FeedItemInterface feedItem, boolean lastItem, OnLikeClickedListener<FeedItemInterface> onLikeClickListener,
						  OnFeedMenuItemAdapterClickListener feedMenuItemAdapterClickListener, final IntentRouter router) {
		super.buildItem(feedItem, lastItem, onLikeClickListener, feedMenuItemAdapterClickListener, router);

		//TODO do we add an on click report to analytics here since a click doesn't do anything?
		if(feedItem.getObject().getPost().getMediaId() > 0)
			mItem.setImage(String.format(FeedValues.BIT_MEDIA_IMAGE_URL, feedItem.getObject().getPost().getMediaId()));
		else
			mItem.setImageGone();

		if(feedItem.getObject().getPost() != null && feedItem.getObject().getPost().getMessage() != null) {
			mItem.setMessage(feedItem.getObject().getPost().getMessage());
			AudioPreviewInfo audioPreviewInfo = mOptions.getLinkProcessor().process(feedItem.getObject().getPost().getMessage());
			if(audioPreviewInfo != null)
				setUpAudioPreview(mItem.getMusicPreviewCardView(), audioPreviewInfo, feedItem, router);
			else
				mItem.getMusicPreviewCardView().setVisibility(View.GONE);
		}
		else {
			mItem.setMessage(null);
			mItem.getMusicPreviewCardView().setVisibility(View.GONE);
		}

		mItem.setMessageLinksClickable(feedItem.getActor().getArtist() != null, new OnLinkClickListener() {

			@Override
			public boolean onClick(TextView textView, String url) {
				return router.onLinkClicked(url);
			}
		});
	}

	private void setUpAudioPreview(MusicPreviewCardView previewCardView, AudioPreviewInfo audioInfo,
								   FeedItemInterface itemInterface, final IntentRouter router) {

		previewCardView.setVisibility(View.VISIBLE);
		OnItemClickAtIndex<AudioPreviewInfo> previewBodyClickListener = new OnItemClickAtIndex<AudioPreviewInfo>() {

			@Override
			public void onItemClick(AudioPreviewInfo item, int index) {
				//this means the user clicked on the body of the preview instead of the
				if(item.getUrlInfoWasGeneratedFrom() != null)
					router.onLinkClicked(item.getUrlInfoWasGeneratedFrom());
			}
		};

		mAudioPreviewHelper = new RecyclingPreviewViewHelper(Collections.singletonList(audioInfo),
				itemInterface, router, mItem, getAdapterPosition(), previewBodyClickListener);

		mItem.getMusicPreviewCardView().setOnClickOfTypeAtListener(mAudioPreviewHelper, ITEM_CLICK, IMAGE_CLICK);

		String title = audioInfo.getSource().toUpperCase();
		String subtitle = audioInfo.getUrlInfoWasGeneratedFrom();

		previewCardView.setImage(null, null, R.drawable.spotify_icon_with_black_background);
		previewCardView.setText(title, subtitle);
	}

	@Override
	public void recycle() {
		AudioStateManager.getInstance().removeListener(this);
	}

	@Override
	public void onAudioStateChanged(AudioStateItem previousItem, AudioStateItem currentItem) {
		if(mAudioPreviewHelper != null)
			mAudioPreviewHelper.onAudioStateChanged(previousItem, currentItem);
	}

}
package com.bandsintown.activityfeed;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.interfaces.OnItemClickOfTypeAtIndex;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;

public class EventAnnouncementGroupView extends AbsFeedItemGroupView {

	private ImageView mBigImage;
	private TextView mArtistName;
	private TextView mDescription;

	public EventAnnouncementGroupView(Context context) {
		super(context);
	}

	public EventAnnouncementGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EventAnnouncementGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void initLayout() {
		mBigImage = (ImageView) findViewById(R.id.afibi_event_image);
		mArtistName = (TextView) findViewById(R.id.afibi_title);
		mDescription = (TextView) findViewById(R.id.afibi_desc);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.aaf_item_group_event_announcement;
	}

	public void buildItem(Context context, FeedGroupInterface group, final OnItemClickOfTypeAtIndex clickListener) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();

		int width =  metrics.widthPixels - (getResources().getDimensionPixelSize(R.dimen.recylcer_view_margin_adjustment) * 2);
		int height = width * 9 / 16; //Enforced 16:9 ratio

		ImageProvider.getInstance(getContext()).displayBigImage(group.getGroupActor().getActorImageUrl(false),
				mBigImage, width, height, ImageProvider.activityFeedImageDisplayer(getContext()));

		mArtistName.setText(group.getGroupActor().getActorName());

		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(clickListener != null)
					clickListener.onItemClick(0, 0, null);
			}

		});
	}

	public void setDescription(String description) {
		mDescription.setText(description);
	}

}

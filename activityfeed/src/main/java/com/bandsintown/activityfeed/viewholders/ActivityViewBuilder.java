package com.bandsintown.activityfeed.viewholders;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v8.renderscript.RenderScript;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bandsintown.activityfeed.AbsFeedItemSingleView;
import com.bandsintown.activityfeed.ApiListener;
import com.bandsintown.activityfeed.BitFeedApi;
import com.bandsintown.activityfeed.FeedDatabase;
import com.bandsintown.activityfeed.FeedItemSingleFooterView;
import com.bandsintown.activityfeed.FeedItemSingleLikedActivity;
import com.bandsintown.activityfeed.FeedItemSingleMessageWithTaggedEvent;
import com.bandsintown.activityfeed.FeedItemSingleMessageWithTaggedEventFlexibleHeight;
import com.bandsintown.activityfeed.FeedItemSinglePost;
import com.bandsintown.activityfeed.FeedItemSingleUserProfile;
import com.bandsintown.activityfeed.FeedItemSingleWatchTrailer;
import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.FeedUser;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeed.util.FeedUtil;
import com.bandsintown.activityfeed.util.Logger;
import com.google.gson.JsonObject;

/**
 * Created by rjaylward on 5/4/16 for Bandsintown
 */
public class ActivityViewBuilder {

    private AppCompatActivity mActivity;
    private BitFeedApi mApi;
    private FeedDatabase mDatabase;
    private SizeEstimate mAverageImageSizeEstimate;

    public ActivityViewBuilder(AppCompatActivity activity, BitFeedApi api, FeedDatabase database, SizeEstimate feedItemImageSizeEstimate) {
        mActivity = activity;
        mApi = api;
        mDatabase = database;
        mAverageImageSizeEstimate = feedItemImageSizeEstimate;
    }

    public AbsFeedItemSingleView getView(FeedItemInterface activityFeedItem, IntentRouter router) {
        final AbsFeedItemSingleView view;

        if(activityFeedItem != null && activityFeedItem.getVerb() != null) {
            switch(activityFeedItem.getVerb()) {
                case FeedValues.VERB_ARTIST_TRACKING:
                case FeedValues.VERB_EVENT_ANNOUNCEMENT:
                case FeedValues.VERB_RSVP:
                case FeedValues.VERB_LISTEN:
                case FeedValues.VERB_REQUEST:
                case FeedValues.VERB_RATE:
                case FeedValues.VERB_PROMOTE:
                case FeedValues.VERB_MESSAGE_RSVPS:
                    view = buildFeedItemMessageView(activityFeedItem, mAverageImageSizeEstimate, router);
                    break;
                case FeedValues.VERB_USER_POST:
                    view = buildFlexibleHeightFeedItemMessageView(activityFeedItem, mAverageImageSizeEstimate, router);
                    break;
                case FeedValues.VERB_ARTIST_POST:
                    view = buildFeedItemPost(activityFeedItem, router);
                    break;
                case FeedValues.VERB_USER_TRACKING:
                    view = buildFeedItemUserProfile(activityFeedItem, router);
                    break;
                case FeedValues.VERB_WATCH_TRAILER:
                case FeedValues.VERB_POST_TRAILER:
                    view = buildFeedItemTourTrailer(activityFeedItem, mAverageImageSizeEstimate, router);
                    break;
                case FeedValues.VERB_LIKE :
                    view = buildLikedActivityView(activityFeedItem, router);
                    break;
                default:
                    view = null;
                    Logger.exception(new IllegalArgumentException("view type not found: " + activityFeedItem.getVerb()));
            }
        }
        else {
            view = null;
            Logger.exception(new IllegalArgumentException("activity feed item or verb was null"));
        }

        return view;
    }

    private FeedItemSingleLikedActivity buildLikedActivityView(FeedItemInterface activityFeedItem, IntentRouter router) {
        FeedItemSingleLikedActivity item = new FeedItemSingleLikedActivity(mActivity);

        AbsFeedItemSingleView likedItem = getView(activityFeedItem.getObject().getLikedItem(), router);

        buildHeader(activityFeedItem.getObject().getLikedItem(), likedItem, router);
        buildFooter(activityFeedItem.getObject().getLikedItem(), likedItem, router);
        applyMargins(likedItem);

        item.addChildToLikedContainer(likedItem);

        return item;
    }

    private FeedItemSingleWatchTrailer buildFeedItemTourTrailer(final FeedItemInterface activityFeedItem, SizeEstimate imageSize, final IntentRouter router) {
        FeedItemSingleWatchTrailer item = new FeedItemSingleWatchTrailer(mActivity, imageSize);

        String imageUrl = activityFeedItem.getObject().getObjectImageUrl();
        if(imageUrl != null)
            item.setImage(imageUrl);

        if(activityFeedItem.getObject().hasImageOverlayTitleAndDesc()) {
            item.setObjectTitle(activityFeedItem.getObject().getObjectTitle(mActivity));
            item.setObjectDescription(activityFeedItem.getObject().getObjectDescription(mActivity));

            if(imageUrl == null)
                item.setDefaultImage(R.drawable.placeholder_big_image);
        }

        item.setOnImageClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO do we have 2 separate click actions here, 1 for the artist and another for the video or should both go to the video?
                router.onObjectClicked(activityFeedItem);
            }

        });

        item.setOnPlayButtonClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                router.onPlayTrailerClicked(activityFeedItem);
            }

        });

        return item;
    }

    private FeedItemSingleUserProfile buildFeedItemUserProfile(FeedItemInterface activityFeedItem, IntentRouter router) {
        FeedItemSingleUserProfile item = new FeedItemSingleUserProfile(mActivity);
        RenderScript renderScript = null;

        try {
            renderScript = RenderScript.create(mActivity.getApplicationContext());
        }
        catch(Exception e) {
            Logger.log("Renderscript Exception");
            Logger.exception(e);
        }

        FeedUser objectUser = activityFeedItem.getObject().getUser();

        item.setLocation(objectUser.getLocation());
        item.setName(objectUser.getFullName());

        float userPicSize = mActivity.getResources().getDimension(R.dimen.user_profile_inset_image_size);

        String imageUri;
        if(objectUser.getMediaId() > 0)
            imageUri = String.format(FeedValues.BIT_MEDIA_IMAGE_URL, objectUser.getMediaId());
        else if(objectUser.getFacebookId() != null)
            imageUri = String.format(FeedValues.FACEBOOK_IMAGE_URL, objectUser.getFacebookId());
        else
            imageUri = null;

        item.setImages(renderScript, imageUri, userPicSize);

        return item;
    }

    private FeedItemSinglePost buildFeedItemPost(FeedItemInterface activityFeedItem, IntentRouter router) {
        FeedItemSinglePost post = null;

        if(activityFeedItem.getObject() != null && activityFeedItem.getObject().getPost() != null) {
            post = new FeedItemSinglePost(mActivity, mAverageImageSizeEstimate);
            post.setMessage(activityFeedItem.getObject().getPost().getMessage());

            post.setMessageLinksClickable(activityFeedItem.getActor().getArtist() != null);

            if(activityFeedItem.getObject().getPost().getMediaId() > 0)
                post.setImage(String.format(FeedValues.BIT_MEDIA_IMAGE_URL, activityFeedItem.getObject().getPost().getMediaId()));
            else
                post.setImageGone();
        }
        else
            Logger.exception(new Exception("FeedPost was null in artist post"));

        return post;
    }

    private FeedItemSingleMessageWithTaggedEventFlexibleHeight buildFlexibleHeightFeedItemMessageView(FeedItemInterface activityFeedItem, SizeEstimate imageSize, IntentRouter router) {
        FeedItemSingleMessageWithTaggedEventFlexibleHeight item = new FeedItemSingleMessageWithTaggedEventFlexibleHeight(mActivity, imageSize);
        return (FeedItemSingleMessageWithTaggedEventFlexibleHeight) buildFeedItemMessageView(activityFeedItem, item, imageSize, router);
    }

    private FeedItemSingleMessageWithTaggedEvent buildFeedItemMessageView(final FeedItemInterface activityFeedItem, @Nullable FeedItemSingleMessageWithTaggedEvent item, SizeEstimate imageSize, final IntentRouter router) {
        if(item == null)
            item = new FeedItemSingleMessageWithTaggedEvent(mActivity, imageSize);

        if(activityFeedItem.getObject().getPost() != null && activityFeedItem.getObject().getPost().getMediaId() > 0)
            item.setImage(mActivity, String.format(FeedValues.BIT_MEDIA_IMAGE_URL, activityFeedItem.getObject().getPost().getMediaId()), activityFeedItem.getObject().isObjectImageUrlAUserPost());
        else if(activityFeedItem.getObject().getEventStub() != null && activityFeedItem.getObject().getEventStub().getImageId() > 0)
            item.setImage(mActivity, String.format(FeedValues.BIT_MEDIA_IMAGE_URL, activityFeedItem.getObject().getEventStub().getImageId()), activityFeedItem.getObject().isObjectImageUrlAUserPost());
        else if(activityFeedItem.getObject().getArtistStub() != null && activityFeedItem.getObject().getArtistStub().getImageId() > 0)
            item.setImage(mActivity, String.format(FeedValues.BIT_MEDIA_IMAGE_URL, activityFeedItem.getObject().getArtistStub().getImageId()), activityFeedItem.getObject().isObjectImageUrlAUserPost());
        else
            item.hideImageSection();

        if(activityFeedItem.getObject().getPost() != null && activityFeedItem.getObject().getPost().getMessage() != null)
            item.setUserMessage(activityFeedItem.getObject().getPost().getMessage());
        else
            item.hideUserMessageView();

        item.setObjectTitle(activityFeedItem.getObject().getObjectTitle(mActivity));
        item.setObjectDescription(activityFeedItem.getObject().getObjectDescription(mActivity));

        item.setOnImageClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                router.onObjectClicked(activityFeedItem);
            }

        });

        item.setMessageLinksClickable(activityFeedItem.getActor().getArtist() != null);

        return item;
    }

    private FeedItemSingleMessageWithTaggedEvent buildFeedItemMessageView(FeedItemInterface item, SizeEstimate imageSize, IntentRouter router) {
        return buildFeedItemMessageView(item, null, imageSize, router);
    }

    private String buildDescription(FeedItemInterface item) {
        switch(item.getDescriptionKey()) {
            case FeedValues.TRACKED :
                return mActivity.getString(R.string.description_tracked);
            case FeedValues.LIKES_YOUR_ACTIVITY :
                return mActivity.getString(R.string.description_likes_your_activity);
            case FeedValues.LISTENED_TO :
                return mActivity.getString(R.string.description_listened_to);
            case FeedValues.REQUESTED_PLAY_CITY :
                return mActivity.getString(R.string.description_requested_play_city, item.getObject().getArtistStub().getName(),
                        item.getActor().getUser().getLocation());
            case FeedValues.RATED_EVENT :
                return mActivity.getString(R.string.description_rated_event, item.getObject().getEventStub().getFormattedTitle(mActivity),
                        FeedUtil.getStars(mActivity, item));
            case FeedValues.RSVP_GOING :
                return mActivity.getString(FeedUtil.checkIfDatetimeHasPassed(item.getObject().getEventStub().getStartsAt()) ? R.string.description_rsvp__past_tense
                        : R.string.description_rsvp_going);
            case FeedValues.RSVP_MAYBE :
                return mActivity.getString(FeedUtil.checkIfDatetimeHasPassed(item.getObject().getEventStub().getStartsAt()) ? R.string.description_rsvp__past_tense
                        : R.string.description_rsvp_maybe);
            case FeedValues.COMMENTED :
                return mActivity.getString(R.string.description_commented);
            case FeedValues.POSTED :
                return mActivity.getString(R.string.description_posted);
            case FeedValues.PLAYING_TOMORROW :
                return mActivity.getString(R.string.description_playing_tomorrow);
            case FeedValues.ON_SALE_TOMORROW :
                return mActivity.getString(R.string.description_on_sale_tomorrow);
            case FeedValues.PLAYING_TODAY :
                return mActivity.getString(R.string.description_playing_today);
            case FeedValues.ON_SALE_TODAY :
                return mActivity.getString(R.string.description_on_sale_today);
            case FeedValues.JUST_ANNOUNCED :
                return mActivity.getString(R.string.description_just_announced);
            case FeedValues.NEXT_MONTH :
                return mActivity.getString(R.string.description_next_month);
            case FeedValues.THIS_WEEKEND :
                return mActivity.getString(R.string.description_this_weekend);
            case FeedValues.NEXT_WEEK :
                return mActivity.getString(R.string.description_next_week);
            case FeedValues.WATCHED_TRAILER:
                return mActivity.getString(R.string.description_watched_a_tour_trailer);
            case FeedValues.POSTED_TRAILER:
                return mActivity.getString(R.string.description_posted_a_tour_trailer);
            default :
                return null;
        }
    }

    public void buildHeader(final FeedItemInterface item, final AbsFeedItemSingleView view, final IntentRouter router) {
        //Null check for payload
        if(item.getDatetime() != null) {
            String timestamp = DateUtils.getRelativeTimeSpanString(FeedUtil.convertDatetimeToMillis(item.getDatetime())).toString();
            view.getHeader().setTimestamp(timestamp);
        }

        view.getHeader().setName(String.valueOf(item.getActor().getActorName()));
        view.getHeader().setPosterImage(ImageProvider.getInstance(mActivity), item.getActor().getActorImageUrl(true));
        view.getHeader().setDescription(buildDescription(item));

        view.getHeader().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                router.onHeaderClicked(item);
            }

        });
    }

    public void buildFooter(final FeedItemInterface feedItem, final AbsFeedItemSingleView view, final IntentRouter router) {
        if(feedItem.getVerb().equals(FeedValues.VERB_LIKE)) {
            //Hide footer since we don't like likes
            view.getFooter().setVisibility(View.GONE);
        }
        else {
            view.getFooter().setInitialState(feedItem, router);
            view.getFooter().setLikeClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    view.getFooter().onLikeClick(feedItem.isLikedByUser(), feedItem.getLikeCount());

                    if(feedItem.isLikedByUser()) // unlike the post
                        makeLikeItemRequest(feedItem, false, view.getFooter(), router);
                    else
                        makeLikeItemRequest(feedItem, true, view.getFooter(), router);
                }

            });

            view.getFooter().setOnLikesTotalClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(feedItem.getLikeCount() > 0) {
                        router.onLikesTotalClick(feedItem);
                    }
                }

            });

            view.getFooter().setFeedMenuItemClickListener(new FeedItemSingleFooterView.OnFeedFooterMenuClickListener() {

                @Override
                public void onReportClick(int feedId) {
                    openFlagFeedItemActivity(feedId, router);
                }

                @Override
                public void onDeleteClick(int feedId) {
                    promptToDeletePost(feedItem);
                }

            });
        }
    }

    private void makeLikeItemRequest(final FeedItemInterface feedItem, final boolean isLiked, final FeedItemSingleFooterView footer, final IntentRouter router) {
        mApi.updatedLikeStatus(feedItem.getId(), isLiked, new ApiListener<JsonObject>() {

            @Override
            public void onResponse(JsonObject response) {
                if(footer != null) {
                    feedItem.setIsLikedByUser(isLiked);
                    feedItem.incrementLikeCountByAmount(isLiked ? 1 : -1);

                    footer.setInitialState(feedItem, router);
                }
                mDatabase.updateActivityFeedLikeStatus(feedItem, true);
            }

            @Override
            public void onErrorResponse(Exception error) {
                // do nothing because the database shouldn't change
                Logger.log("Make like request error ---------->", error.getMessage());

                if(footer != null)
                    footer.setInitialState(feedItem, router);
            }

        });
    }

    public void applyMargins(AbsFeedItemSingleView view) {
        int horizontalMargins = (int) mActivity.getResources().getDimension(R.dimen.activity_feed_card_horizontal_margin);
        int verticalMargins = (int) mActivity.getResources().getDimension(R.dimen.activity_feed_card_top_margin);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(horizontalMargins, verticalMargins, horizontalMargins, 0); //Don't place a bottom margin here, only for last item in buildItem
        view.setLayoutParams(params);
    }

    public void promptToDeletePost(final FeedItemInterface item) {
        AlertDialog dialog = new AlertDialog.Builder(mActivity).create();
        dialog.setTitle(mActivity.getString(R.string.delete_post));
        dialog.setMessage("Are you sure you want to delete this post?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, mActivity.getString(android.R.string.yes), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeDeleteApiRequest(item);
            }

        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, mActivity.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
    }

    private void makeDeleteApiRequest(final FeedItemInterface item) {
        mApi.deleteActivityFeedItem(item.getId(), new ApiListener() {

            @Override
            public void onResponse(Object response) {
                mDatabase.deleteActivityFeedItem(item.getId());
                mActivity.finish();
            }

            @Override
            public void onErrorResponse(Exception error) {
                Toast.makeText(mActivity, R.string.unfortunately_unable_to_delete_your_post_please_try_again_later, Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void openFlagFeedItemActivity(int feedId, IntentRouter router) {
        router.onFlagFeedItem(feedId);
    }

}

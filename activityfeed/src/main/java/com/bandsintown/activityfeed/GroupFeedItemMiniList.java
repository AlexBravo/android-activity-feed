package com.bandsintown.activityfeed;

import android.content.Context;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.interfaces.AudioControlsGroup;
import com.bandsintown.activityfeed.interfaces.OnItemClickOfTypeAtIndex;
import com.bandsintown.activityfeed.util.Logger;

import java.util.ArrayList;

/**
 * Created by rjaylward on 4/6/16 for Bandsintown
 */
public class GroupFeedItemMiniList extends AbsFeedItemGroupView implements AudioControlsGroup {

    private ArrayList<GroupFeedItemMiniListItem> mListItems = new ArrayList<>();
    private LinearLayout mLinearLayout;
    private ImageProvider mImageProvider;

    private ViewModel mViewModel;

    private static final int MAX_ITEMS = 3;

    public GroupFeedItemMiniList(Context context, ImageProvider provider, ViewModel viewModel) {
        super(context);

        mViewModel = viewModel;
        mImageProvider = provider;
    }

    public GroupFeedItemMiniList(Context context) {
        super(context);
        mImageProvider = ImageProvider.getInstance(context);
    }

    public void setViewModel(ViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void loadItems(int size, OnItemClickOfTypeAtIndex clickOfTypeAtIndexListener, int mainClickType, int imageClickType) {
        Logger.log("LOADING A MINI LIST ITEM IN THE ACTIVITY FEED!!!!!");
        mLinearLayout.removeAllViews();

        if(mViewModel != null) {
            mListItems.clear();

            for(int i = 0; i < Math.min(size, MAX_ITEMS); i++) {
                GroupFeedItemMiniListItem miniListItem = new GroupFeedItemMiniListItem(getContext(), i);

                miniListItem.setOnClickOfTypeAtListener(clickOfTypeAtIndexListener, mainClickType, imageClickType);

                miniListItem.setIndex(i);
                miniListItem.setImage(mImageProvider, mViewModel.getImageUrlErrorResIdPair(i));
                miniListItem.setPlayButtonVisibility(mViewModel.playButtonVisible(i) ? VISIBLE : GONE);
                miniListItem.setText(mViewModel.getTitle(i), mViewModel.getSubtitle(i));

                mLinearLayout.addView(miniListItem, i, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                mListItems.add(miniListItem);
            }
        }
        else {
            Logger.exception(new Exception("You must set the view model before you load the items"));
        }
    }

    @Override
    protected void initLayout() {
        mLinearLayout = (LinearLayout) findViewById(R.id.lml_root);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aaf_group_listitem_mini_list;
    }

    public interface ViewModel {
        String getTitle(int index);
        String getSubtitle(int index);
        Pair<String, Integer> getImageUrlErrorResIdPair(int index);
        boolean playButtonVisible(int index);
    }

    public void setAudioPlayerStateAtIndex(int index, int state) {
        if(mListItems.size() > index)
            mListItems.get(index).setMediaControlsState(state);
    }
}
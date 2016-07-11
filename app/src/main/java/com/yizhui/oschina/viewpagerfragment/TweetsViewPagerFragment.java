package com.yizhui.oschina.viewpagerfragment;

import android.os.Bundle;

import com.yizhui.oschina.base.BaseViewPagerFragment;
import com.yizhui.oschina.fragment.TweetFragment;

/**
 * Created by Yizhui on 2016/5/26.
 */
public class TweetsViewPagerFragment extends BaseViewPagerFragment {
    @Override
    protected void initViewPagerInfos() {
        addViewPagerInfo("最新动弹", TweetFragment.class, getBundle(0));
        addViewPagerInfo("热门动弹", TweetFragment.class, getBundle(0));
    }

    private Bundle getBundle(int category){
        Bundle bundle=new Bundle();
        bundle.putInt(TweetFragment.BUNDLE_KEY_CATEGORY,category);
        return bundle;
    }

}

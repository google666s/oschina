package com.yizhui.oschina.fragment;

import android.os.Bundle;

import com.yizhui.oschina.adapter.TweetAdapter;
import com.yizhui.oschina.api.remote.OSChinaApi;
import com.yizhui.oschina.base.BaseListAdapter;
import com.yizhui.oschina.base.BaseListFragment;
import com.yizhui.oschina.bean.Tweet;
import com.yizhui.oschina.bean.TweetList;
import com.yizhui.oschina.util.XmlUtils;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Yizhui on 2016/5/26.
 */
public class TweetFragment extends BaseListFragment<Tweet> {

    protected static final String TAG=TweetFragment.class.getSimpleName();

    public static final String BUNDLE_KEY_CATEGORY="key_category";

    private int mCategory= TweetList.CATALOG_LATEST;

    private int mTweetTotalCount=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args=getArguments();
        if(args!=null && args.getString(BUNDLE_KEY_CATEGORY)!=null){
            mCategory=args.getInt(BUNDLE_KEY_CATEGORY);
        }
    }

    @Override
    protected int getListItemTotalCount() {
        return mTweetTotalCount;
    }

    @Override
    protected List<Tweet> parseList(InputStream is) {
        TweetList result= XmlUtils.toBean(TweetList.class,is);
        mTweetTotalCount=result.getTweetCount();
        return result.getList();
    }

    @Override
    protected void getDataFromServer() {
        OSChinaApi.getTweetList(mCategory,mCurrentPage,getPageSize(),mResponseHandler);
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new TweetAdapter();
    }
}

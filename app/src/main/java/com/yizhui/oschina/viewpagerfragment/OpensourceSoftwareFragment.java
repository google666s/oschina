package com.yizhui.oschina.viewpagerfragment;

import android.os.Bundle;

import com.yizhui.oschina.base.BaseViewPagerFragment;
import com.yizhui.oschina.bean.SoftwareList;
import com.yizhui.oschina.fragment.SoftwareListFragment;

/**
 * Created by Yizhui on 2016/5/22.
 */
public class OpensourceSoftwareFragment extends BaseViewPagerFragment {

    @Override
    protected void initViewPagerInfos() {

        addViewPagerInfo("推荐", SoftwareListFragment.class, getBundle(SoftwareList.CATEGORY_RECOMMEND));
        addViewPagerInfo("最新", SoftwareListFragment.class, getBundle(SoftwareList.CATEGORY_TIME));
        addViewPagerInfo("热门", SoftwareListFragment.class, getBundle(SoftwareList.CATEGORY_VIEW));
        addViewPagerInfo("国产", SoftwareListFragment.class, getBundle(SoftwareList.CATEGORY_LIST_CN));
    }

    private Bundle getBundle(String category){
        Bundle bundle=new Bundle();
        bundle.putString(SoftwareListFragment.BUNDLE_KEY_CATEGORY, category);
        return bundle;
    }
}

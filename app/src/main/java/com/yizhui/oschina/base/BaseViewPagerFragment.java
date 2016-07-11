package com.yizhui.oschina.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yizhui.oschina.R;
import com.yizhui.oschina.ui.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yizhui on 2016/5/26.
 */
public abstract class BaseViewPagerFragment extends Fragment {

    @InjectView(R.id.viewPager)
    ViewPager mViewPager;

    @InjectView(R.id.pagerSlidingTabStrip)
    PagerSlidingTabStrip mPagerSlidingTabStrip;

    protected List<ViewPagerInfo> mViewPagerInfos=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_base_viewpager,container,false);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPagerInfos.clear();

        initViewPagerInfos();

        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return Fragment.instantiate(getContext(),
                        mViewPagerInfos.get(position).fragmentClass.getName(),
                        mViewPagerInfos.get(position).fragmentArgs);
            }

            @Override
            public int getCount() {
                return mViewPagerInfos.size();
            }
        });

        mPagerSlidingTabStrip.setTabItemTitles(getTabTitles());
        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

    protected abstract void initViewPagerInfos();


    static class ViewPagerInfo{
        String tabTitle;
        Class<?> fragmentClass;
        Bundle fragmentArgs;

        public ViewPagerInfo(String title,Class<?> frgClass,Bundle frgArgs){
            tabTitle=title;
            fragmentClass=frgClass;
            fragmentArgs=frgArgs;
        }
    }

    public void addViewPagerInfo(String tabTitle,Class<?> fragmentClass,Bundle fragmentArgs){
        mViewPagerInfos.add(new ViewPagerInfo(tabTitle, fragmentClass, fragmentArgs));
    }

    private List<String> getTabTitles(){
        List<String> titles=new ArrayList<>();
        for(int i=0;i<mViewPagerInfos.size();i++){
            titles.add(mViewPagerInfos.get(i).tabTitle);
        }
        return titles;
    }
}

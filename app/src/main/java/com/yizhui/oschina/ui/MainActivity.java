package com.yizhui.oschina.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.fragment.MyInformationFragment;
import com.yizhui.oschina.viewpagerfragment.OpensourceSoftwareFragment;
import com.yizhui.oschina.viewpagerfragment.TweetsViewPagerFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private NavigationDrawerFragment navDrawerFragment;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.main_tab_host)
    FragmentTabHost mTabHost;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        if(AppContext.getNightModeSwitch()){
            setTheme(R.style.NightTheme);
        }else{
            setTheme(R.style.DayTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        FragmentManager fm=getSupportFragmentManager();

        navDrawerFragment=(NavigationDrawerFragment)fm.findFragmentById(R.id.navigation_drawer_fragment);
        navDrawerFragment.setupDrawerLayout(mDrawerLayout);

//        if(fm.findFragmentByTag("OpensourceSoftwareFragment")==null){
//            fm.beginTransaction().add(R.id.fragment_container,new OpensourceSoftwareFragment(),"OpensourceSoftwareFragment").commit();
//        }

//        if(fm.findFragmentByTag("TweetViewPagerFragment")==null){
//            fm.beginTransaction().add(R.id.fragment_container,new TweetFragment(),"TweetViewPagerFragment").commit();
//        }

        initTabHost();



    }

    private void initTabHost(){

        MainTab[] mainTabs=new MainTab[]{
                new MainTab(0,R.string.main_tab_news,R.drawable.tab_icon_news, OpensourceSoftwareFragment.class),
                new MainTab(1,R.string.main_tab_tweet,R.drawable.tab_icon_tweet, TweetsViewPagerFragment.class),
                new MainTab(2,R.string.main_tab_quick,R.drawable.tab_icon_news, TweetsViewPagerFragment.class),
                new MainTab(3,R.string.main_tab_explore,R.drawable.tab_icon_explore, OpensourceSoftwareFragment.class),
                new MainTab(4,R.string.main_tab_me,R.drawable.tab_icon_me, MyInformationFragment.class)
        };

        mTabHost.setup(this,getSupportFragmentManager(),R.id.fragment_container);
        if(Build.VERSION.SDK_INT>10){
            mTabHost.getTabWidget().setShowDividers(0);
        }

        for(int i=0;i<mainTabs.length;i++){

            View view=View.inflate(this,R.layout.main_tab_indicator,null);

            TextView tab_title=(TextView)view.findViewById(R.id.tab_item_title);
            ImageView tab_image=(ImageView)view.findViewById(R.id.tab_item_image);

            tab_title.setText(mainTabs[i].getResName());
            tab_image.setImageResource(mainTabs[i].getResIcon());

            mTabHost.addTab(mTabHost.newTabSpec(getString(mainTabs[i].getResName())).setIndicator(view),
                    mainTabs[i].getClz(),null);
        }
    }

    private static class MainTab{
        private int index;
        private int resName;
        private int resIcon;
        private Class<?> clz;

        public MainTab(int index,int resName,int resIcon,Class<?> clz){
            this.index=index;
            this.resName=resName;
            this.resIcon=resIcon;
            this.clz=clz;
        }


        public int getIndex() {
            return index;
        }

        public int getResName() {
            return resName;
        }

        public int getResIcon() {
            return resIcon;
        }

        public Class<?> getClz() {
            return clz;
        }
    }
}

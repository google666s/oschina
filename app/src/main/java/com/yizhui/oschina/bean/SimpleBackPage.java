package com.yizhui.oschina.bean;

import com.yizhui.oschina.R;
import com.yizhui.oschina.fragment.AboutOSCFragment;
import com.yizhui.oschina.fragment.BrowserFragment;
import com.yizhui.oschina.fragment.MyInformationDetailFragment;
import com.yizhui.oschina.fragment.SettingsFragment;
import com.yizhui.oschina.fragment.SettingsNotificationFragment;

/**
 * Created by Yizhui on 2016/7/4.
 */
public enum SimpleBackPage {

    SETTING(1,R.string.toolbar_title_settings, SettingsFragment.class),

    SETTING_NOTIFICATION(2,R.string.toolbar_title_settings_notification, SettingsNotificationFragment.class),

    ABOUT_OSC(3,R.string.toolbar_title_about, AboutOSCFragment.class),

    BROWSER(4,R.string.app_name, BrowserFragment.class),

    MY_INFORMATION_DETAIL(5,R.string.toolbar_title_my_information_detail, MyInformationDetailFragment.class);


    private int value;
    private int resTitle;
    private Class<?> fragmentClass;

    SimpleBackPage(int value,int resTitle,Class<?> fragmentClass){
        this.value=value;
        this.resTitle=resTitle;
        this.fragmentClass=fragmentClass;
    }

    public int getValue(){
        return value;
    }

    public int getResTitle() {
        return resTitle;
    }

    public Class<?> getFragmentClass() {
        return fragmentClass;
    }

    public static SimpleBackPage getPageByValue(int value){
        for(SimpleBackPage page : values()){
            if(page.getValue()==value)
                return page;
        }
        return null;
    }
}



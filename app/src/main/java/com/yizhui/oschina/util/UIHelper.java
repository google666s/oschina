package com.yizhui.oschina.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yizhui.oschina.bean.SimpleBackPage;
import com.yizhui.oschina.fragment.BrowserFragment;
import com.yizhui.oschina.ui.LoginActivity;
import com.yizhui.oschina.ui.SimpleBackActivity;

/**
 * Created by Yizhui on 2016/7/3.
 */
public class UIHelper {

    public static void showSimpleBack(Activity context,int pageValue){
        showSimpleBack(context,pageValue,null);
    }

    public static void showSimpleBack(Activity context,int pageNum,Bundle args){
        Intent intent=new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE,pageNum);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS,args);
        context.startActivity(intent);
    }

    public static void openBrowser(Activity context,String url){
        Bundle args=new Bundle();
        args.putString(BrowserFragment.BUNDLE_KEY_URL, url);
        showSimpleBack(context, SimpleBackPage.BROWSER.getValue(), args);
    }

    public static void openLoginActivity(Activity context){
        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


}

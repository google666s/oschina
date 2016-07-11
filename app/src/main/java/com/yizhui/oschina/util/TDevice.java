package com.yizhui.oschina.util;

import android.net.ConnectivityManager;

import com.yizhui.oschina.AppContext;

/**
 * Created by Yizhui on 2016/7/5.
 */
public class TDevice {

    public static boolean hasInternet(){
        return ((ConnectivityManager)(AppContext.context().getSystemService("connectivity"))).getActiveNetworkInfo()!=null;
    }
}

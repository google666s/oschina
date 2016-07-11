package com.yizhui.oschina.util;

import android.util.Log;

/**
 * Created by Yizhui on 2016/5/23.
 */
public class TLog {

    public static boolean DEBUG=true;

    public static void d(String tag,String msg){
        if(DEBUG)
            Log.d(tag,msg);
    }

    public static void i(String tag,String msg){
        if(DEBUG)
            Log.i(tag,msg);
    }

    public static void e(String tag,String msg){
        if(DEBUG)
            Log.e(tag,msg);
    }

    public static void v(String tag,String msg){
        if(DEBUG)
            Log.v(tag,msg);
    }
}

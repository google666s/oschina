package com.yizhui.oschina.api;

import android.os.Build;

import com.yizhui.oschina.AppContext;

/**
 * Created by Yizhui on 2016/5/23.
 */
public class ApiHttpClientHelper {

    public static String getUserAgent(AppContext appContext){
        StringBuilder ua=new StringBuilder("OSChina.NET");
        ua.append("/"+appContext.getVersionName()+"_"+appContext.getVersionCode()); //App版本信息
        ua.append("/Android"); //手机系统平台
        ua.append("/"+ Build.VERSION.RELEASE); //手机系统版本
        ua.append("/"+Build.MODEL); //手机型号
        ua.append("/"+appContext.getAppId()); //客户端唯一标识
        return ua.toString();
    }
}

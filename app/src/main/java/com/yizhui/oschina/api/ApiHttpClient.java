package com.yizhui.oschina.api;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.util.TLog;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.Locale;

import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Created by Yizhui on 2016/5/23.
 */
public class ApiHttpClient {

    public static final String HOST="www.oschina.net";
    private static final String API_URL="http://www.oschina.net/%s";

    public static AsyncHttpClient client;

    private ApiHttpClient(){}

    public static void setHttpClient(AsyncHttpClient c){
        client=c;
        client.addHeader("Accept-Language", Locale.getDefault().toString());
        client.addHeader("Host",HOST);
        client.addHeader("Connection","Keep-Alive");
        client.setUserAgent(ApiHttpClientHelper.getUserAgent(AppContext.getInstance()));
    }

    public static AsyncHttpClient getHttpClient(){
        return client;
    }

    public static String getCookies(AsyncHttpClient httpClient){
        HttpContext httpContext=httpClient.getHttpContext();
        CookieStore cookieStore=(CookieStore)httpContext.getAttribute(ClientContext.COOKIE_STORE);
        String strCookies="";
        if(cookieStore!=null){
            for(HttpCookie c : cookieStore.getCookies()){
                strCookies+=c.getName()+"="+c.getValue()+";";
            }
        }
        return strCookies;
    }

    /**
     * 为每次Request附加Cookie
     * @param cookie
     */
    public static void addCookie(String cookie){
        client.addHeader("Cookie",cookie);
    }

    /**
     * 为每次Request移除Cookie
     */
    public static void cleanCookie(){
        client.removeHeader("Cookie");
    }

    public static String getApiUrl(String action){
        if(action.startsWith("http:") || action.startsWith("https:"))
            return action;

        return String.format(API_URL,action);
    }

    public static void get(String url,AsyncHttpResponseHandler handler){
        client.get(getApiUrl(url), handler);
        log(new StringBuilder("GET ").append(url).toString());
    }

    public static void get(String url,RequestParams params,AsyncHttpResponseHandler handler){
        client.get(getApiUrl(url),params,handler);
        log(new StringBuilder("GET ").append(url).append("&")
                .append(params).toString());
    }

    public static void post(String url, AsyncHttpResponseHandler handler) {
        client.post(getApiUrl(url), handler);
        log(new StringBuilder("POST ").append(url).toString());
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(getApiUrl(url), params, handler);
        log(new StringBuilder("POST ").append(url).append("&")
                .append(params).toString());
    }

    private static void log(String log){
        TLog.d("ApiHttpClient",log);
    }
}

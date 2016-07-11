package com.yizhui.oschina.api.remote;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.api.ApiHttpClient;

/**
 * Created by Yizhui on 2016/5/23.
 */
public class OSChinaApi {

    public static void login(String username,String password,AsyncHttpResponseHandler handler){
        RequestParams params=new RequestParams();
        params.put("username",username);
        params.put("pwd",password);
        params.put("keep_login",1);
        ApiHttpClient.get("action/api/login_validate",params,handler);
    }

    public static void  getMyInformation(int uid,AsyncHttpResponseHandler handler){
        RequestParams params=new RequestParams();
        params.put("uid",uid);
        ApiHttpClient.get("action/api/my_information", params, handler);
    }

    public static void getSoftwareList(String searchTag,int page,int pageSize,AsyncHttpResponseHandler handler){
        RequestParams params=new RequestParams();
        params.put("searchTag",searchTag);
        params.put("pageIndex",page);
        params.put("pageSize", pageSize);
        ApiHttpClient.get("action/api/software_list", params, handler);
    }

    public static void getTweetList(int uid,int page,int pageSize,AsyncHttpResponseHandler handler){
        RequestParams params=new RequestParams();
        params.put("uid",uid);
        params.put("pageIndex",page);
        params.put("pageSize",pageSize);
        ApiHttpClient.get("action/api/tweet_list",params,handler);
    }

    public static void getTweetTopicList(String topic,int page,int pageSize,AsyncHttpResponseHandler handler){
        RequestParams params=new RequestParams();
        params.put("title",topic);
        params.put("pageIndex",page);
        params.put("pageSize",pageSize);
        ApiHttpClient.get("action/api/tweet_topic_list",params,handler);
    }

    public static void pubLikeTweet(int tweetId,int tweetAuthorId,AsyncHttpResponseHandler handler){
        RequestParams params=new RequestParams();
        params.put("tweetid",tweetId);
        params.put("uid","");
        params.put("ownerOfTweet",tweetAuthorId);
        ApiHttpClient.post("action/api/tweet_like", params, handler);
    }

    public static void pubUnLikeTweet(int tweetId,int tweetAuthorId,AsyncHttpResponseHandler handler){
        RequestParams params=new RequestParams();
        params.put("tweetid",tweetId);
        params.put("uid","");
        params.put("ownerOfTweet",tweetAuthorId);
        ApiHttpClient.post("action/api/tweet_unlike",params,handler);
    }

    public static void getTweetLikeList(int tweetId,int page,AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_like_list", params, handler);
    }
}

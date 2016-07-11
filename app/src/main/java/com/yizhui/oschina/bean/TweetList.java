package com.yizhui.oschina.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yizhui on 2016/5/27.
 */
@XStreamAlias("oschina")
public class TweetList extends Entity implements ListEntity<Tweet> {

    public final static int CATALOG_LATEST=0;
    public final static int CATALOG_HOT=-1;
    public final static int CATALOG_ME=1;

    @XStreamAlias("tweetcount")
    private int tweetCount;

    @XStreamAlias("pagesize")
    private int pageSize;

    @XStreamAlias("tweets")
    private List<Tweet> tweetList=new ArrayList<>();

    public int getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Tweet> getTweetList() {
        return tweetList;
    }

    public void setTweetList(List<Tweet> tweetList) {
        this.tweetList = tweetList;
    }

    @Override
    public List<Tweet> getList() {
        return tweetList;
    }
}

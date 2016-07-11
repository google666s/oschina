package com.yizhui.oschina.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Yizhui on 2016/5/24.
 */

@XStreamAlias("oschina")
public class SoftwareList extends Entity implements ListEntity<SoftwareDec> {

    //推荐、最新、热门、国产
    public static final String CATEGORY_RECOMMEND="recommend";
    public static final String CATEGORY_TIME="time";
    public static final String CATEGORY_VIEW="view";
    public static final String CATEGORY_LIST_CN="list_cn";


    @XStreamAlias("softwarecount")
    private int softwareCount;
    @XStreamAlias("pagesize")
    private int pageSize;
    @XStreamAlias("softwares")
    private List<SoftwareDec> softwareList=new ArrayList<SoftwareDec>();

    public int getSoftwareCount() {
        return softwareCount;
    }

    public void setSoftwareCount(int softwareCount) {
        this.softwareCount = softwareCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<SoftwareDec> getSoftwareList() {
        return softwareList;
    }

    public void setSoftwareList(List<SoftwareDec> softwareList) {
        this.softwareList = softwareList;
    }

    @Override
    public List<SoftwareDec> getList() {
        return softwareList;
    }
}

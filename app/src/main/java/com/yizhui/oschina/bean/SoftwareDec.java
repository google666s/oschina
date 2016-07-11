package com.yizhui.oschina.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 软件列表
 *
 * Created by Yizhui on 2016/5/24.
 */
@XStreamAlias("software")
public class SoftwareDec extends Entity {

    @XStreamAlias("name")
    private String name;
    @XStreamAlias("description")
    private String description;
    @XStreamAlias("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

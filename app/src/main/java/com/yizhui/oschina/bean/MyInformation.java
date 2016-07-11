package com.yizhui.oschina.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Yizhui on 2016/7/6.
 */
@XStreamAlias("oschina")
public class MyInformation extends Base{

    @XStreamAlias("user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

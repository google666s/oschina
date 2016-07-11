package com.yizhui.oschina.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Yizhui on 2016/7/5.
 */
@XStreamAlias("oschina")
public class LoginUserBean extends Entity {

    @XStreamAlias("result")
    private Result result;

    @XStreamAlias("user")
    private User user;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

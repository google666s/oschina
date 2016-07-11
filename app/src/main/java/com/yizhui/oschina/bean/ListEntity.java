package com.yizhui.oschina.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yizhui on 2016/5/27.
 */
public interface ListEntity<T extends Entity> extends Serializable {

    public List<T> getList();
}

package com.yizhui.oschina.fragment;

import android.os.Bundle;

import com.yizhui.oschina.adapter.SoftwareListAdapter;
import com.yizhui.oschina.api.remote.OSChinaApi;
import com.yizhui.oschina.base.BaseListAdapter;
import com.yizhui.oschina.base.BaseListFragment;
import com.yizhui.oschina.bean.SoftwareDec;
import com.yizhui.oschina.bean.SoftwareList;
import com.yizhui.oschina.util.XmlUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yizhui on 2016/5/22.
 */
public class SoftwareListFragment extends BaseListFragment<SoftwareDec> {

    private int totalCount=0;

    public static final String BUNDLE_KEY_CATEGORY="key_category";

    private String mCategory=SoftwareList.CATEGORY_RECOMMEND;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args=getArguments();
        if(args!=null && args.getString(BUNDLE_KEY_CATEGORY)!=null){
            mCategory=args.getString(BUNDLE_KEY_CATEGORY);
        }
    }

    @Override
    protected int getListItemTotalCount() {
        return totalCount;
    }

    @Override
    protected List<SoftwareDec> parseList(InputStream is) {
        SoftwareList list = XmlUtils.toBean(SoftwareList.class, is);
        if(list!=null){
            totalCount=list.getSoftwareCount();
            return list.getSoftwareList();
        }
        return new ArrayList<SoftwareDec>();
    }

    @Override
    protected void getDataFromServer() {
        OSChinaApi.getSoftwareList(mCategory, mCurrentPage, getPageSize(), mResponseHandler);
    }

    @Override
    protected List<SoftwareDec> getNewItems(List<SoftwareDec> listExists, List<SoftwareDec> list) {
        List<SoftwareDec> newItems=new ArrayList<>();

        for(int i=0;i<list.size();i++){
            int j;
            for(j=0;j<listExists.size();j++){
                if(listExists.get(j).getName().equals(list.get(i).getName())){
                    break;
                }
            }
            if(j==listExists.size()){
                newItems.add(list.get(i));
            }
        }

        return newItems;
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new SoftwareListAdapter();
    }
}
package com.yizhui.oschina.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.adapter.SoftwareListAdapter;
import com.yizhui.oschina.bean.Entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Yizhui on 2016/5/25.
 */
public abstract class BaseListFragment<T extends Entity>  extends Fragment{

    private static final String LOG_TAG = "BaseListFragment";

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_NO_MORE = 3;
    public static final int STATE_PRESS_NONE = 4;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.listView)
    ListView mListView;

    protected AsyncHttpResponseHandler mResponseHandler;

    protected BaseListAdapter mAdapter;

    protected int mCurrentPage = 0;

    protected int mState = STATE_NONE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pull_refresh_listview, container, false);
        ButterKnife.inject(this, view);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setColorSchemeColors(R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 0;
                mState = STATE_NONE;
                getDataFromServer();
            }
        });

        mAdapter = getListAdapter();

        mListView.setAdapter(mAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (mState == STATE_REFRESH || mState == STATE_LOAD_MORE) {
                    return;
                }

                boolean scrollEnd = false; //是否滚动到底部

                try {
                    if (mAdapter.getFooterView() != null && view.getPositionForView(mAdapter.getFooterView()) == view.getLastVisiblePosition()) {
                        scrollEnd = true;
                    }
                } catch (Exception e) {

                }

                if (mState == STATE_NONE && scrollEnd) {
                    if (mAdapter.getState() == SoftwareListAdapter.STATE_LOAD_MORE) {
                        mCurrentPage++;
                        mState = STATE_LOAD_MORE;
                        mAdapter.setFooterViewLoadMore();

                        getDataFromServer();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mResponseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    List<T> responseResult=parseList(new ByteArrayInputStream(responseBody));

                    List<T> newItems =getNewItems(mAdapter.getDatas(),responseResult);

                    int adapterState;
                    if(mAdapter.getCount()+newItems.size()==0){
                        adapterState=SoftwareListAdapter.STATE_EMPTY_ITEM;
                    }else if(mAdapter.getDatas().size()+newItems.size()==getListItemTotalCount()){
                        adapterState=SoftwareListAdapter.STATE_NO_MORE;
                    }else{
                        adapterState=SoftwareListAdapter.STATE_LOAD_MORE;
                    }

                    Log.d("GetDataFromServer", getListItemTotalCount() + " curPage:" + mCurrentPage + " datas:" + mAdapter.getCount() + " pageSize:" + getPageSize() + " newItems:" + newItems.size() + " " + adapterState);

                    mAdapter.setState(adapterState);
                    mAdapter.addData(newItems);

                    mState = STATE_NONE;
                    mSwipeRefreshLayout.setRefreshing(false);

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        };

        getDataFromServer();
    }

    protected abstract int getListItemTotalCount();

    protected abstract List<T> parseList(InputStream is);

    protected abstract void getDataFromServer();

    protected abstract BaseListAdapter getListAdapter();

    protected List<T> getNewItems(List<T> listExists,List<T> list){
        List<T> newItems=new ArrayList<>();

        for(int i=0;i<list.size();i++){
            int j;
            for(j=0;j<listExists.size();j++){
                if(listExists.get(j).getId()==list.get(i).getId()){
                    break;
                }
            }
            if(j==listExists.size()){
                newItems.add(list.get(i));
            }
        }

        return newItems;
    }

    protected int getPageSize(){
        return AppContext.PAGE_SIZE;
    }
}
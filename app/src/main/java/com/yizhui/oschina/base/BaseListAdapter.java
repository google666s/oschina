package com.yizhui.oschina.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yizhui.oschina.R;
import com.yizhui.oschina.bean.Entity;
import com.yizhui.oschina.util.TLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yizhui on 2016/5/25.
 */
public abstract class BaseListAdapter<T extends Entity> extends BaseAdapter {

    public static final int STATE_EMPTY_ITEM=0;
    public static final int STATE_LOAD_MORE=1;
    public static final int STATE_NO_MORE=2;
    public static final int STATE_NO_DATA=3;

    protected int _loadMoreText;
    protected int _noMoreText;
    protected int _noDataText;

    protected int mState=STATE_EMPTY_ITEM;

    protected List<T> mDatas=new ArrayList<T>();

    protected View mFooterView;

    class FooterViewHolder{
        ProgressBar progressBar;
        TextView text;
    }

    public BaseListAdapter(){
        _loadMoreText= R.string.loading;
        _noMoreText=R.string.loading_no_more;
        _noDataText=R.string.no_data;
    }

    protected boolean hasFooterView(){
        return true;
    }

    @Override
    public int getCount() {
        switch (mState){
            case STATE_LOAD_MORE:
            case STATE_NO_MORE:
                return hasFooterView()?mDatas.size()+1:mDatas.size();
            case STATE_NO_DATA:
                return 1;
            default:
                return mDatas.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(mDatas.size()>position){
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TLog.d("getView", position + "");

        if(position==getCount()-1 && hasFooterView()){
            return getFooterView(LayoutInflater.from(parent.getContext()));
        }

        return getContentView(position,convertView,parent);
    }

    protected View getFooterView(LayoutInflater inflater){
        if(mFooterView==null){
            mFooterView=inflater.inflate(R.layout.list_cell_footer,null);

            ProgressBar progressBar=(ProgressBar)mFooterView.findViewById(R.id.list_cell_footer_progressbar);
            TextView text=(TextView)mFooterView.findViewById(R.id.list_cell_footer_text);

            FooterViewHolder holder=new FooterViewHolder();
            holder.progressBar=progressBar;
            holder.text=text;

            mFooterView.setTag(holder);
        }
        setFooterViewState(mState);
        return mFooterView;
    }

    protected void setFooterViewState(int state){
        if(mFooterView==null) return;
        FooterViewHolder holder=(FooterViewHolder)mFooterView.getTag();
        switch (mState){
            case STATE_LOAD_MORE:
                mFooterView.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.text.setVisibility(View.VISIBLE);
                holder.text.setText(_loadMoreText);
                break;
            case STATE_NO_MORE:
                mFooterView.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.GONE);
                holder.text.setVisibility(View.VISIBLE);
                holder.text.setText(_noMoreText);
                break;
            default:
                mFooterView.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.GONE);
                holder.text.setVisibility(View.GONE);
                break;
        }
    }

    public void setFooterViewLoadMore(){
        setFooterViewState(STATE_LOAD_MORE);
    }

    public void setFooterViewNoMore(){
        setFooterViewState(STATE_NO_MORE);
    }

    protected abstract View getContentView(int position,View convertView,ViewGroup parent);


    public View getFooterView(){
        return mFooterView;
    }

    public int getState(){
        return mState;
    }

    public List<T> getDatas(){
        return mDatas;
    }

    public void setState(int state){
        mState=state;
    }

    public void addData(List<T> data){
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void addItem(T item){
        mDatas.add(item);
        notifyDataSetChanged();
    }

    public void addItem(int position,T item){
        mDatas.add(position, item);
        notifyDataSetChanged();
    }

    public void removeItem(T item){
        mDatas.remove(item);
        notifyDataSetChanged();
    }

    public void clear(){
        mDatas.clear();
        notifyDataSetChanged();
    }
}


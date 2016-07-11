package com.yizhui.oschina.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yizhui.oschina.R;
import com.yizhui.oschina.base.BaseListAdapter;
import com.yizhui.oschina.bean.SoftwareDec;

/**
 * Created by Yizhui on 2016/5/24.
 */
public class SoftwareListAdapter extends BaseListAdapter<SoftwareDec> {

    static class ViewHolder{
        TextView title;
        TextView desc;
    }

    @Override
    protected View getContentView(int position, View convertView, ViewGroup parent) {
        if(convertView==null || convertView.getTag()==null|| !(convertView.getTag() instanceof ViewHolder) ){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell_software,parent,false);

            TextView title=(TextView)convertView.findViewById(R.id.list_cell_software_title);
            TextView desc=(TextView)convertView.findViewById(R.id.list_cell_software_desc);

            ViewHolder viewHolder=new ViewHolder();
            viewHolder.title=title;
            viewHolder.desc=desc;

            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder=(ViewHolder)convertView.getTag();
        viewHolder.title.setText(((SoftwareDec)mDatas.get(position)).getName());
        viewHolder.desc.setText(((SoftwareDec) mDatas.get(position)).getDescription());

        return convertView;
    }
}

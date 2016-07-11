package com.yizhui.oschina.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yizhui.oschina.R;
import com.yizhui.oschina.base.BaseListAdapter;
import com.yizhui.oschina.bean.Tweet;
import com.yizhui.oschina.widget.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yizhui on 2016/5/26.
 */
public class TweetAdapter extends BaseListAdapter<Tweet> {

    static class ViewHolder{

        @InjectView(R.id.tweet_face)
        CircleImageView face;
        @InjectView(R.id.tweet_name)
        TextView author;
        @InjectView(R.id.tweet_content)
        TextView content;
        @InjectView(R.id.tweet_image)
        ImageView image;
        @InjectView(R.id.tweet_like_users )
        TextView likeUsers;
        @InjectView(R.id.tweet_time)
        TextView time;
        @InjectView(R.id.tweet_platform)
        TextView platform;
        @InjectView(R.id.tweet_like_state)
        TextView likeState;
        @InjectView(R.id.tweet_comment_count)
        TextView commentCount;

        public ViewHolder(View view){
            ButterKnife.inject(this,view);
        }
    }

    @Override
    protected View getContentView(int position, View convertView, ViewGroup parent) {
        if(convertView==null || convertView.getTag()==null|| !(convertView.getTag() instanceof ViewHolder) ){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell_tweet,parent,false);

            ViewHolder vh=new ViewHolder(convertView);
            convertView.setTag(vh);
        }

        ViewHolder vh=(ViewHolder)convertView.getTag();

        Tweet tweet=mDatas.get(position);

        vh.author.setText(tweet.getAuthor());
        vh.time.setText(tweet.getPubDate());
        vh.content.setText(tweet.getAuthor());

        vh.commentCount.setText(tweet.getCommentCount());

        if(tweet.getLikeUser()==null || tweet.getLikeUser().size()==0){
            vh.likeUsers.setVisibility(View.GONE);
        }else{

        }

        return convertView;
    }
}

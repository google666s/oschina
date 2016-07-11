package com.yizhui.oschina.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.api.remote.OSChinaApi;
import com.yizhui.oschina.bean.MyInformation;
import com.yizhui.oschina.bean.SimpleBackPage;
import com.yizhui.oschina.bean.User;
import com.yizhui.oschina.util.UIHelper;
import com.yizhui.oschina.util.XmlUtils;
import com.yizhui.oschina.widget.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Yizhui on 2016/7/6.
 */
public class MyInformationFragment extends Fragment implements View.OnClickListener {

    private static final String TAG="MyInformationFragment";

    @InjectView(R.id.tv_username)
    TextView mTvUserName;
    @InjectView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @InjectView(R.id.iv_gender)
    ImageView mIvGender;
    @InjectView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @InjectView(R.id.tv_score)
    TextView mTvScore;
    @InjectView(R.id.tv_favorite)
    TextView mTvFavorite;
    @InjectView(R.id.tv_following)
    TextView mTvFollowing;
    @InjectView(R.id.tv_fans)
    TextView mTvFans;
    @InjectView(R.id.ll_user_info)
    View mUserInfoContainer;

    private final BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"cxx-"+intent.getAction());
            switch (intent.getAction()){
                case AppContext.INTENT_ACTION_LOGOUT:
                    onUserLogout();
                    break;
                case AppContext.INTENT_ACTION_USER_CHANGE:
                    Log.d(TAG,"receive INTENT_ACTION_USER_CHANGE");
                    int uid=AppContext.getInstance().getLoginUid();
                    OSChinaApi.getMyInformation(uid,mHandler);
                    break;
                case AppContext.INTENT_ACTION_NOTICE:

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter=new IntentFilter();
        filter.addAction(AppContext.INTENT_ACTION_LOGOUT);
        filter.addAction(AppContext.INTENT_ACTION_USER_CHANGE);
        filter.addAction(AppContext.INTENT_ACTION_NOTICE);
        getActivity().registerReceiver(mReceiver,filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_information,container,false);
        ButterKnife.inject(this, view);
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    private void initView(View view){

        if(!AppContext.getInstance().isLogin()){
            onUserLogout();
        }else{
            User user=AppContext.getInstance().getUserInfo();
            fillUserInfo(user);
        }
    }

    private void onUserLogout(){
        int v=View.GONE;
        mUserInfoContainer.setVisibility(v);
        mIvQrCode.setVisibility(v);
        mIvGender.setVisibility(v);
        mTvUserName.setText(R.string.no_login);
    }

    private void onUserLogin(){
        int v=View.VISIBLE;
        mUserInfoContainer.setVisibility(v);
        mIvQrCode.setVisibility(v);
        mIvGender.setVisibility(v);
    }

    private void fillUserInfo(User user){
        mTvUserName.setText(user.getName());
        mTvScore.setText(user.getScore()+"");
        mTvFavorite.setText(user.getFavoritecount()+"");
        mTvFollowing.setText(user.getFollowers()+"");
        mTvFans.setText(user.getFans()+"");
    }

    @Override
    @OnClick({R.id.iv_avatar,R.id.iv_qr_code,R.id.ll_my_score,R.id.ll_my_favorite,R.id.ll_my_following,
             R.id.ll_my_fans,R.id.ll_my_message,R.id.ll_my_blog,R.id.ll_my_note,R.id.ll_my_team})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_avatar:
                if(AppContext.getInstance().isLogin())
                    UIHelper.showSimpleBack(getActivity(), SimpleBackPage.MY_INFORMATION_DETAIL.getValue());
                else
                    UIHelper.openLoginActivity(getActivity());
                break;
            default:
                return;
        }
    }

    private AsyncHttpResponseHandler mHandler=new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            MyInformation info=XmlUtils.toBean(MyInformation.class, responseBody);
            if(info.getUser()!=null){
                onUserLogin();
                fillUserInfo(info.getUser());
            }else{
                AppContext.showToast(R.string.toast_getuserinfo_error);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            AppContext.showToast(R.string.toast_internet_error);
        }
    };
}

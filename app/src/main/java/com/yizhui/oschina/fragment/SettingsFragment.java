package com.yizhui.oschina.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.yizhui.oschina.AppConfig;
import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.base.BaseActivity;
import com.yizhui.oschina.bean.SimpleBackPage;
import com.yizhui.oschina.util.DialogHelper;
import com.yizhui.oschina.util.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yizhui on 2016/7/1.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener{

    @InjectView(R.id.tb_loading_img)
    SwitchButton mTbLoadImg;
    @InjectView(R.id.tb_double_click_exit)
    SwitchButton mTbDoubleClickExit;
    @InjectView(R.id.tv_cache_size)
    TextView mCacheFileSize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings,container,false);
        ButterKnife.inject(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((BaseActivity)getActivity()).setActionBarTitle(R.string.toolbar_title_settings);
    }

    private void initView(View view){
        view.findViewById(R.id.rl_loading_img).setOnClickListener(this);
        view.findViewById(R.id.rl_notification).setOnClickListener(this);
        view.findViewById(R.id.rl_clean_cache).setOnClickListener(this);
        view.findViewById(R.id.rl_double_click_exit).setOnClickListener(this);
        view.findViewById(R.id.rl_about_app).setOnClickListener(this);
        view.findViewById(R.id.rl_exit_app).setOnClickListener(this);

        mTbLoadImg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppContext.Pref_Setting.setPreference(AppConfig.KEY_SETTINGS_LOAD_IMAGE, isChecked);
            }
        });

        mTbDoubleClickExit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppContext.Pref_Setting.setPreference(AppConfig.KEY_SETTINGS_DOUBLE_CLICK_EXIT, isChecked);
            }
        });
    }

    private void initData() {
        mTbLoadImg.setCheckedImmediately(AppContext.Pref_Setting.getPreference(AppConfig.KEY_SETTINGS_LOAD_IMAGE, false));
        mTbDoubleClickExit.setCheckedImmediately(AppContext.Pref_Setting.getPreference(AppConfig.KEY_SETTINGS_DOUBLE_CLICK_EXIT, false));
        mCacheFileSize.setText(calculateCacheFileSize()+"MB");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_loading_img:
                mTbLoadImg.toggle();
                break;
            case R.id.rl_notification:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.SETTING_NOTIFICATION.getValue());
                break;
            case R.id.rl_clean_cache:
                handleCleanCache();
                break;
            case R.id.rl_double_click_exit:
                mTbDoubleClickExit.toggle();
                break;
            case R.id.rl_about_app:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.ABOUT_OSC.getValue());
                break;
            case R.id.rl_exit_app:
                handleExitApp();
                break;
        }
    }

    private double calculateCacheFileSize(){

        return 0;
    }

    private void handleCleanCache(){
        DialogHelper.getConfirmDialog(getActivity(),"是否清空缓存?", new Dialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //to clean cache
                mCacheFileSize.setText("0MB");
            }
        }).create().show();
    }

    private void handleExitApp(){

    }
}

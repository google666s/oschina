package com.yizhui.oschina.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;
import com.yizhui.oschina.AppConfig;
import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yizhui on 2016/7/3.
 */
public class SettingsNotificationFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.tb_exit_no_notice)
    SwitchButton mTbAppExit;
    @InjectView(R.id.tb_voice)
    SwitchButton mTbVoice;
    @InjectView(R.id.tb_vibration)
    SwitchButton mTbVibration;
    @InjectView(R.id.tb_accpet)
    SwitchButton mTbAccept;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_settings_notification,container,false);
        ButterKnife.inject(this,view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((BaseActivity)getActivity()).setActionBarTitle(R.string.toolbar_title_settings_notification);
    }

    private void initView(View view){
        view.findViewById(R.id.rl_accept).setOnClickListener(this);
        view.findViewById(R.id.rl_voice).setOnClickListener(this);
        view.findViewById(R.id.rl_vibration).setOnClickListener(this);
        view.findViewById(R.id.rl_app_exit).setOnClickListener(this);

        setOnCheckedChangedListener(mTbAccept, AppConfig.KEY_SETTINGS_NOTIFICATION_ACCEPT);
        setOnCheckedChangedListener(mTbVoice,AppConfig.KEY_SETTINGS_NOTIFICATION_VOICE);
        setOnCheckedChangedListener(mTbVibration,AppConfig.KEY_SETTINGS_NOTIFICATION_VIBRATION);
        setOnCheckedChangedListener(mTbAppExit,AppConfig.KEY_SETTINGS_NOTIFICATION_EXIT_APP);
    }



    private void initData(){
        mTbAccept.setCheckedImmediately(AppContext.Pref_Setting.getPreference(AppConfig.KEY_SETTINGS_NOTIFICATION_ACCEPT, false));
        mTbVoice.setCheckedImmediately(AppContext.Pref_Setting.getPreference(AppConfig.KEY_SETTINGS_NOTIFICATION_VOICE, false));
        mTbVibration.setCheckedImmediately(AppContext.Pref_Setting.getPreference(AppConfig.KEY_SETTINGS_NOTIFICATION_VIBRATION, false));
        mTbAppExit.setCheckedImmediately(AppContext.Pref_Setting.getPreference(AppConfig.KEY_SETTINGS_NOTIFICATION_EXIT_APP, false));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_accept:
                mTbAccept.toggle();
                break;
            case R.id.rl_voice:
                mTbVoice.toggle();
                break;
            case R.id.rl_vibration:
                mTbVibration.toggle();
                break;
            case R.id.rl_app_exit:
                mTbAppExit.toggle();
                break;
            default:
                break;
        }
    }


    private void setOnCheckedChangedListener(SwitchButton btn,final String key){
        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppContext.Pref_Setting.setPreference(key, isChecked);
            }
        });
    }
}

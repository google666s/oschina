package com.yizhui.oschina.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.util.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yizhui on 2016/7/4.
 */
public class AboutOSCFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.tv_app_version)
    TextView mVersion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about,container,false);
        ButterKnife.inject(this, view);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view){

        view.findViewById(R.id.rl_check_version).setOnClickListener(this);
        view.findViewById(R.id.rl_feedback).setOnClickListener(this);
        view.findViewById(R.id.rl_score).setOnClickListener(this);
        view.findViewById(R.id.rl_git_client).setOnClickListener(this);
        view.findViewById(R.id.tv_official_website).setOnClickListener(this);
        view.findViewById(R.id.tv_more).setOnClickListener(this);
    }

    private void initData(){
        mVersion.setText("V "+AppContext.getInstance().getVersionName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_check_version:
                break;
            case R.id.rl_feedback:
                break;
            case R.id.rl_score:
                break;
            case R.id.rl_git_client:
                break;
            case R.id.tv_official_website:
                UIHelper.openBrowser(getActivity(), "https://www.oschina.net");
                break;
            case R.id.tv_more:
                UIHelper.openBrowser(getActivity(), "https://www.oschina.net/home/aboutosc");
                break;
            default:
                break;
        }
    }
}

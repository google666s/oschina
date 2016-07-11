package com.yizhui.oschina.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yizhui.oschina.R;
import com.yizhui.oschina.base.BaseActivity;
import com.yizhui.oschina.bean.SimpleBackPage;

/**
 * Created by Yizhui on 2016/7/4.
 */
public class SimpleBackActivity extends BaseActivity {

    public static final String BUNDLE_KEY_PAGE="BUNDLE_KEY_PAGE";
    public static final String BUNDLE_KEY_ARGS="BUNDLE_KEY_ARGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_fragment);

        Intent intent=getIntent();

        SimpleBackPage page=SimpleBackPage.getPageByValue(intent.getIntExtra(BUNDLE_KEY_PAGE, -1));

        if(page!=null){
            FragmentManager fm=getSupportFragmentManager();
            try {
                Fragment fragment = (Fragment)page.getFragmentClass().newInstance();
                Bundle args=intent.getBundleExtra(BUNDLE_KEY_ARGS);
                if(args!=null){
                    fragment.setArguments(args);
                }
                fm.beginTransaction().replace(R.id.container, fragment, "Fragment").commit();

            }catch (Exception e){
                e.printStackTrace();
                throw new IllegalArgumentException("generate fragment error. by value:"+page.getValue());
            }
            setActionBarTitle(page.getResTitle());
        }
    }
}

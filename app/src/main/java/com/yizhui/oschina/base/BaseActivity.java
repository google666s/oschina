package com.yizhui.oschina.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;

/**
 * Created by Yizhui on 2016/7/3.
 */
public class BaseActivity extends AppCompatActivity {

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //设置主题的代码需要放置于super.onCreate之前
        if(AppContext.getNightModeSwitch()){
            setTheme(R.style.NightTheme);
        }else{
            setTheme(R.style.DayTheme);
        }

        super.onCreate(savedInstanceState);

        if(hasActionBar()){
            initActionBar();
        }
    }

    protected boolean hasActionBar(){return true;}

    private void initActionBar(){
        mActionBar=getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void setActionBarTitle(int resId){
        setActionBarTitle(getString(resId));
    }

    public void setActionBarTitle(String title){
        if(title==null ||title.equals("")){
            title=getString(R.string.app_name);
        }
        if(hasActionBar()||mActionBar!=null){
            mActionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

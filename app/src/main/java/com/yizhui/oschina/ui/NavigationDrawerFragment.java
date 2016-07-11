package com.yizhui.oschina.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.bean.SimpleBackPage;
import com.yizhui.oschina.util.UIHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yizhui on 2016/5/19.
 */
public class NavigationDrawerFragment extends Fragment implements View.OnClickListener {


    @InjectView(R.id.drawer_menu_item_answer)
    View mMenuItemAnswer;
    @InjectView(R.id.drawer_menu_item_opensoft)
     View mMenuItemOpensoft;
    @InjectView(R.id.drawer_menu_item_blog)
     View mMenuItemBlog;
    @InjectView(R.id.drawer_menu_item_gitclient)
     View mMenuItemGitclient;
    @InjectView(R.id.drawer_menu_item_setting)
    View mMenuItemSetting;
    @InjectView(R.id.drawer_menu_item_theme)
    View mMenuItemTheme;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_navigation_drawer,container,false);

        ButterKnife.inject(this, view);

        initView(view);

        return view;
    }

    private void initView(View view){
        mMenuItemAnswer.setOnClickListener(this);
        mMenuItemBlog.setOnClickListener(this);
        mMenuItemOpensoft.setOnClickListener(this);
        mMenuItemGitclient.setOnClickListener(this);
        mMenuItemSetting.setOnClickListener(this);
        mMenuItemTheme.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.drawer_menu_item_setting:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.SETTING.getValue());
                break;
            case R.id.drawer_menu_item_blog:
                break;
            case R.id.drawer_menu_item_theme:
                AppContext.setNightModeSwitch(!AppContext.getNightModeSwitch());
                getActivity().recreate();
                break;
        }

        mDrawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawers();
            }
        },800);
    }

    private ActionBar getActionBar(){
        return ((AppCompatActivity)getActivity()).getSupportActionBar();
    }

    public void setupDrawerLayout(final DrawerLayout drawerLayout){

        mDrawerLayout=drawerLayout;

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                  /* host Activity */
                drawerLayout,          /* DrawerLayout object */
                null,                  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.syncState();  // Sync the toggle state
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}

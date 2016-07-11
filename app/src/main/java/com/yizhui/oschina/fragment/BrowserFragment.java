package com.yizhui.oschina.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yizhui on 2016/7/4.
 */
public class BrowserFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.webview)
    WebView mWebView;
    @InjectView(R.id.browser_back)
    ImageView mBack;
    @InjectView(R.id.browser_forward)
    ImageView mForward;
    @InjectView(R.id.browser_refresh)
    ImageView mRefresh;
    @InjectView(R.id.browser_systembrowser)
    ImageView mSystemBrowser;
    @InjectView(R.id.progress)
    ProgressBar mProgress;
    @InjectView(R.id.browser_bottom_bar)
    View mBottomBar;

    public static final String BUNDLE_KEY_URL="BUNDLE_KEY_URL";

    public static final String URL_DEFAULT="http://www.oschina.net";

    private String mCurrentUrl=URL_DEFAULT;

    private TranslateAnimation mAnimBottomIn,mAnimBottomOut;

    private GestureDetector mGestureDetector;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_browser, container,false);
        ButterKnife.inject(this, view);
        initData();
        initView(view);
        return view;
    }

    private void initData(){
        Bundle args=getArguments();
        if(args!=null){
            mCurrentUrl=args.getString(BUNDLE_KEY_URL,URL_DEFAULT);
        }
    }

    private void initView(View view){
        initWebView();
        initAnimation();

        mBack.setOnClickListener(this);
        mForward.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
        mSystemBrowser.setOnClickListener(this);

        mGestureDetector=new GestureDetector(getActivity(),new MyGestureListener());
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });

        mWebView.loadUrl(mCurrentUrl); //开始加载页面
    }

    private void initWebView(){
        WebSettings webSettings=mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //启用支持JS
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //优先使用缓存
        webSettings.setAllowFileAccess(true); //可访问文件
        webSettings.setBuiltInZoomControls(true); //支持缩放
        if(Build.VERSION.SDK_INT>=11){
            webSettings.setPluginState(WebSettings.PluginState.ON);
            webSettings.setDisplayZoomControls(false);
        }

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }

    private void initAnimation(){
        mAnimBottomIn= (TranslateAnimation)AnimationUtils.loadAnimation(getActivity(),R.anim.anim_brows_bottom_in);
        mAnimBottomOut= (TranslateAnimation)AnimationUtils.loadAnimation(getActivity(),R.anim.anim_brows_bottom_out);

        mAnimBottomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBottomBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mAnimBottomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBottomBar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.browser_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_shared:
                showSharedDialog();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browser_back:
                mWebView.goBack();
                break;
            case R.id.browser_forward:
                mWebView.goForward();
                break;
            case R.id.browser_refresh:
                mWebView.reload();
                break;
            case R.id.browser_systembrowser:
                try{
                    //启用外部浏览器
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(mCurrentUrl));
                    startActivity(intent);
                }catch (Exception e){
                    AppContext.showToast("网页地址错误");
                }
                break;
        }
    }

    private void showSharedDialog(){

    }

    /**
     * 加载页面前被调用
     * @param view
     * @param url
     */
    private void onUrlLoading(WebView view,String url){
        mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * 加载完成后被调用
     * @param view
     * @param url
     */
    private void onUrlFinished(WebView view,String url){
        mProgress.setVisibility(View.GONE);
    }

    /**
     * 当前WebView显示页面的标题
     * @param view
     * @param title
     */
    private void onWebTitle(WebView view,String title){
        if(mWebView!=null && getActivity()!=null){ //由于WebView加载属于耗时操作，可能会在Activity已经关闭后才被调用
            ((BaseActivity)getActivity()).setActionBarTitle(title);
        }
    }

    /**
     * 当前WebView显示页面的图标
     * @param view
     * @param icon
     */
    private void onWebIcon(WebView view,Bitmap icon){

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            onUrlLoading(view,url);
            mCurrentUrl=url;
            return super.shouldOverrideUrlLoading(view,url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            onUrlFinished(view,url);
        }
    }

    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            onWebTitle(view,title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            onWebIcon(view,icon);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress>90){
                mProgress.setVisibility(View.GONE);
            }
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

        private int mDoubleTapCount=0; //WebView双击事件次数

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mDoubleTapCount++;
            if(mDoubleTapCount%2==0){
                mBottomBar.startAnimation(mAnimBottomIn);
            }else{
                mBottomBar.startAnimation(mAnimBottomOut);
            }
            return super.onDoubleTap(e);
        }
    }
}

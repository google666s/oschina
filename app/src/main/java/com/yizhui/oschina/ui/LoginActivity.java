package com.yizhui.oschina.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.api.remote.OSChinaApi;
import com.yizhui.oschina.base.BaseActivity;
import com.yizhui.oschina.bean.LoginUserBean;
import com.yizhui.oschina.util.CyptoUtils;
import com.yizhui.oschina.util.TDevice;
import com.yizhui.oschina.util.XmlUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Yizhui on 2016/7/5.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG="LoginActivity";

    private static final String BUNDLE_KEY_REQUEST_CODE = "BUNDLE_KEY_REQUEST_CODE";

    @InjectView(R.id.et_username)
    EditText mUserName;
    @InjectView(R.id.et_password)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initView();
    }

    private void initView(){

        String account=AppContext.Pref_Login.getPreference("user.account", "");
        String password=AppContext.Pref_Login.getPreference("user.pwd","");
        mUserName.setText(account);
        if(password!="") mPassword.setText(CyptoUtils.decode(password));
    }


    @Override
    @OnClick({R.id.btn_login,R.id.iv_login_by_qq,R.id.iv_login_by_wechat,R.id.iv_login_by_sinaweibo})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                handleLoginClick();
                break;
            case R.id.iv_login_by_qq:
                break;
            case R.id.iv_login_by_wechat:
                break;
            case R.id.iv_login_by_sinaweibo:
                break;
            default:
                break;
        }
    }

    private void handleLoginClick(){

        Log.d(TAG,"handleLoginClick");

        if(!TDevice.hasInternet()){
            AppContext.showToast(R.string.toast_no_internet);
            return;
        }

        String userName=mUserName.getText().toString();
        String password=mPassword.getText().toString();

        if(userName.equals("")){
            AppContext.showToastShort(R.string.toast_input_username);
            mUserName.requestFocus();
            return;
        }

        if (password.equals("")) {
            AppContext.showToastShort(R.string.toast_input_password);
            mPassword.requestFocus();
            return;
        }

        OSChinaApi.login(userName,password,mHandler);
    }

    private final AsyncHttpResponseHandler mHandler=new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            LoginUserBean bean=XmlUtils.toBean(LoginUserBean.class, responseBody);
            if(bean!=null){
                handleLoginUserBean(bean);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            AppContext.showToast(R.string.toast_internet_error);
        }
    };

    private void handleLoginUserBean(LoginUserBean bean){
        if(bean.getResult().isOK()){

            AppContext.getInstance().login(bean.getUser());

            Intent intent=new Intent();
            intent.putExtra(BUNDLE_KEY_REQUEST_CODE,1);
            setResult(RESULT_OK,intent);
            sendBroadcast(new Intent());
            finish();

        }else{
            AppContext.showToast(bean.getResult().getErrorMessage());
        }
    }
}

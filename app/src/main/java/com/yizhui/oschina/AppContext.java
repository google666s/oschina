package com.yizhui.oschina;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.loopj.android.http.AsyncHttpClient;
import com.yizhui.oschina.api.ApiHttpClient;
import com.yizhui.oschina.base.BaseApplication;
import com.yizhui.oschina.bean.User;
import com.yizhui.oschina.util.CyptoUtils;
import com.yizhui.oschina.util.SharedPrefrenceHelper;

import java.util.UUID;

/**
 * Created by Yizhui on 2016/5/21.
 */
public class AppContext extends BaseApplication {

    private static AppContext instance;

    public static final int PAGE_SIZE=20; //默认分页大小

    public static SharedPrefrenceHelper Pref_App =null;
    public static SharedPrefrenceHelper Pref_Setting =null;
    public static SharedPrefrenceHelper Pref_Login =null;

    public static final String INTENT_ACTION_USER_CHANGE = "net.oschina.action.USER_CHANGE";
    public static final String INTENT_ACTION_COMMENT_CHANGED = "net.oschina.action.COMMENT_CHANGED";
    public static final String INTENT_ACTION_NOTICE = "net.oschina.action.APPWIDGET_UPDATE";
    public static final String INTENT_ACTION_LOGOUT = "net.oschina.action.LOGOUT";

    @Override
    public void onCreate() {
        super.onCreate();

        instance=this;
        init();
    }

    public static AppContext getInstance(){
        return instance;
    }

    private void init(){

        //SharedPreference
        Pref_App =new SharedPrefrenceHelper(this,AppConfig.PREFERENCE_APP);
        Pref_Setting =new SharedPrefrenceHelper(this,AppConfig.PREFERENCE_SETTING);
        Pref_Login =new SharedPrefrenceHelper(this,AppConfig.PREFERENCE_LOGIN);

        //Http Api
        AsyncHttpClient client=new AsyncHttpClient();
        ApiHttpClient.setHttpClient(client);
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId(){
        String uniqueID= Pref_App.getPreference(AppConfig.KEY_APP_UNIQUEID,"");
        if(uniqueID.equals("")){
            uniqueID= UUID.randomUUID().toString();
            Pref_App.setPreference(AppConfig.KEY_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    private PackageInfo getPackageInfo(){
        PackageInfo info=null;
        try{
            info=getPackageManager().getPackageInfo(getPackageName(),0);
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace(System.err);
        }
        return info;
    }

    /**
     *  获取APP版本号
     * @return
     */
    public int getVersionCode(){
        PackageInfo info=getPackageInfo();
        if(info!=null){
            return info.versionCode;
        }
        return 0;
    }

    /**
     *  获取APP版本字串
     * @return
     */
    public String getVersionName(){
        PackageInfo info=getPackageInfo();
        if(info!=null){
            return info.versionName;
        }
        return "";
    }


    //夜间模式
    public static boolean getNightModeSwitch(){
        return Pref_Setting.getPreference(AppConfig.KEY_SETTINGS_NIGHT_MODE_SWITCH, false);
    }

    //设置夜间模式
    public static void setNightModeSwitch(boolean on){
        Pref_Setting.setPreference(AppConfig.KEY_SETTINGS_NIGHT_MODE_SWITCH, on);
    }

    private int _loginUid;
    private boolean _isLogin;

    public int getLoginUid(){
        return _loginUid;
    }

    public boolean isLogin(){
        return _isLogin;
    }

    /**
     * 保存登录信息
     *
     * @param user 用户信息
     */
    public void saveUserInfo(final User user) {
        Pref_Login.setPreference("user.uid",user.getId());
        Pref_Login.setPreference("user.name",user.getName());
        Pref_Login.setPreference("user.face", user.getPortrait()); //用户头像Url
        Pref_Login.setPreference("user.account", user.getAccount());
        Pref_Login.setPreference("user.pwd", CyptoUtils.encode(user.getPwd()));
        Pref_Login.setPreference("user.location", user.getLocation());
        Pref_Login.setPreference("user.followers", user.getFollowers());
        Pref_Login.setPreference("user.fans", user.getFans());
        Pref_Login.setPreference("user.score", user.getScore());
        Pref_Login.setPreference("user.favoritecount", user.getFavoritecount());
        Pref_Login.setPreference("user.gender", user.getGender());
        Pref_Login.setPreference("user.isRememberMe", user.isRememberMe());
    }

    public User getUserInfo(){
        User user=new User();
        user.setId(Pref_Login.getPreference("user.uid",0));
        user.setName(Pref_Login.getPreference("user.name", ""));
        user.setPortrait(Pref_Login.getPreference("user.face", ""));
        user.setAccount(Pref_Login.getPreference("user.account", ""));
        user.setLocation(Pref_Login.getPreference("user.location", ""));
        user.setFollowers(Pref_Login.getPreference("user.followers", 0));
        user.setFans(Pref_Login.getPreference("user.fans", 0));
        user.setScore(Pref_Login.getPreference("user.score", 0));
        user.setFavoritecount(Pref_Login.getPreference("user.favoritecount", 0));
        user.setGender(Pref_Login.getPreference("user.gender", ""));
        return user;
    }

    public void login(User user){

        String cookies=ApiHttpClient.getCookies(ApiHttpClient.getHttpClient());
        if(cookies!=""){
            ApiHttpClient.addCookie(cookies);
            AppContext.Pref_Login.setPreference(AppConfig.KEY_LOGIN_COOKIE, cookies);
        }

        AppContext.getInstance().saveUserInfo(user); //保存登录用户信息

        this._loginUid = user.getId();
        this._isLogin = true;

        Intent intent=new Intent(INTENT_ACTION_USER_CHANGE);
        sendBroadcast(intent);
    }

    public void logout(){

        ApiHttpClient.cleanCookie();
        Pref_Login.removeAllPreference(); //清除登录用户信息

        this._loginUid=-1;
        this._isLogin=false;

        Intent intent=new Intent(INTENT_ACTION_LOGOUT);
        sendBroadcast(intent);
    }
}

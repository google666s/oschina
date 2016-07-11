package com.yizhui.oschina.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Iterator;

/**
 * Created by Yizhui on 2016/7/5.
 */
public class SharedPrefrenceHelper {

    private String prefName="";
    private Context context=null;

    public SharedPrefrenceHelper(Context context,String preferenceName){
        this.prefName=preferenceName;
        this.context=context.getApplicationContext();
    }

    public SharedPreferences getPreferences(){
        return context.getSharedPreferences(prefName,context.MODE_PRIVATE);
    }

    public void preference_editor_commit(SharedPreferences.Editor editor){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD){
            editor.apply();
        }else{
            editor.commit();
        }
    }

    public void setPreference(String key,int value){
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.putInt(key,value);
        preference_editor_commit(editor);
    }

    public void setPreference(String key,boolean value){
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.putBoolean(key, value);
        preference_editor_commit(editor);
    }

    public void setPreference(String key,String value){
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.putString(key, value);
        preference_editor_commit(editor);
    }

    public int getPreference(String key,int defValue){
        return getPreferences().getInt(key,defValue);
    }

    public boolean getPreference(String key,boolean defValue){
        return getPreferences().getBoolean(key, defValue);
    }

    public String getPreference(String key,String defValue){
        return getPreferences().getString(key, defValue);
    }

    public void removeAllPreference(){
        SharedPreferences prefs=getPreferences();
        SharedPreferences.Editor editor=prefs.edit();
        Iterator<String> iterator=prefs.getAll().keySet().iterator();
        while(iterator.hasNext()){
            editor.remove(iterator.next());
        }
        editor.commit();
    }
}

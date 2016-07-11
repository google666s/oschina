package com.yizhui.oschina;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Yizhui on 2016/5/21.
 */
public class AppManager {

    private static Stack<Activity> stack=new Stack<>();

    public static void addActivity(Activity activity){
        stack.push(activity);
    }

    public static void removeAllActivity(){
        stack.clear();
    }

    public static void removeActivity(){
        stack.pop();
    }
}

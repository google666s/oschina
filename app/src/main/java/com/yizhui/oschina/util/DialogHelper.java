package com.yizhui.oschina.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Yizhui on 2016/7/2.
 */
public class DialogHelper {


    public static AlertDialog.Builder getConfirmDialog(Context context,String message,DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定",onClickListener);
        builder.setNegativeButton("取消",null);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context,String title,String[] items,DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, onClickListener);
        builder.setNegativeButton("取消",null);
        return builder;
    }






































}

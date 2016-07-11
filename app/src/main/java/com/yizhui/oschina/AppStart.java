package com.yizhui.oschina;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.yizhui.oschina.ui.MainActivity;

/**
 * Created by Yizhui on 2016/7/2.
 */
public class AppStart extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view= View.inflate(this,R.layout.activity_appstart,null);
        setContentView(view);

        //渐变展示启动屏
        AlphaAnimation aa=new AlphaAnimation(0.5f,1.0f);
        aa.setDuration(800);
        view.setAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {redirectTo();}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void redirectTo(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

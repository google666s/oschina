package com.yizhui.oschina.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yizhui.oschina.R;

import java.util.List;

/**
 * Created by Yizhui on 2016/5/21.
 */
public class PagerSlidingTabStrip extends LinearLayout  {

    private static int TAB_ITEM_NUM=0;
    private int mCurrentIndex =0;

    private Paint paint;
    private int indicator_translate_x;
    private int indicator_width;
    private int indicator_height;
    private static final int MAX_INDICATOR_HEIGHT=3;

    private int indicator_color; //滑块颜色
    private int highline_color;  //高亮颜色
    private int normal_color;    //正常颜色
    private int tabItemTextSize_sp; //Tab 文本大小

    private boolean mGetTabItemNormalColor=false;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mPageChangeListener;


    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);
        indicator_color = a.getInteger(R.styleable.PagerSlidingTabStrip_slidingBlockColor, Color.GREEN);
        highline_color=a.getInteger(R.styleable.PagerSlidingTabStrip_highlineTextColor,Color.GREEN);
        tabItemTextSize_sp=a.getInteger(R.styleable.PagerSlidingTabStrip_tabItemTextSize_sp,16);
        a.recycle();

        initPaint();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void initPaint(){
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(indicator_color);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawRect(
                indicator_translate_x, getHeight() - indicator_height,
                indicator_translate_x + indicator_width, getHeight(),
                paint);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        indicator_width=w/TAB_ITEM_NUM;
        indicator_height=Math.min(h/10,MAX_INDICATOR_HEIGHT);
    }

    private void highlineText(int positon){
        TextView tv=(TextView)getChildAt(positon);

        if(!mGetTabItemNormalColor){
            normal_color=tv.getCurrentTextColor();
            mGetTabItemNormalColor=true;
        }

        for(int i=0;i<getChildCount();i++){
            ((TextView)getChildAt(i)).setTextColor(normal_color);
        }
        tv.setTextColor(highline_color);
    }

    public void setTabItemTitles(List<String> tabItemTitles){
        TAB_ITEM_NUM=tabItemTitles.size();
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight=1;
        for(int i=0;i<TAB_ITEM_NUM;i++){
            final int index=i;
            TextView tv=new TextView(getContext());
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, tabItemTextSize_sp);
            tv.setText(tabItemTitles.get(i));
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCurrentIndex ==index) return;

                    mViewPager.setCurrentItem(index);
                }
            });
            addView(tv);
        }

        highlineText(mCurrentIndex);
    }

    public void setViewPager(ViewPager viewPager){
        mViewPager=viewPager;

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator_translate_x=(int)((position+positionOffset)*indicator_width);
                invalidate();

                if(mPageChangeListener!=null){
                    mPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex=position;
                highlineText(mCurrentIndex);

                if(mPageChangeListener != null) {
                    mPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(mPageChangeListener!=null){
                    mPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener){
        mPageChangeListener=listener;
    }
}

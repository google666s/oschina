package com.yizhui.oschina.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yizhui.oschina.R;

/**
 * Created by Yizhui on 2016/5/26.
 */
public class CircleImageView01 extends ImageView {


    private static final int DEFAULT_BORDER_WIDTH=0;
    private static final int DEFAULT_BORDER_COLOR= Color.WHITE;

    private int mBorderWidth;
    private int mBorderColor;

    private Paint paint;

    private Paint mPaint;
    private Paint mCirclePaint;
    private Paint mBorderPaint;

    public CircleImageView01(Context context) {
        this(context, null);
    }

    public CircleImageView01(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.CircleImageView01,defStyleAttr,0);
        mBorderWidth=a.getDimensionPixelSize(R.styleable.CircleImageView01_borderWidth, DEFAULT_BORDER_WIDTH);
        mBorderColor=a.getColor(R.styleable.CircleImageView01_borderColor, DEFAULT_BORDER_COLOR);
        a.recycle();

        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (drawable != null) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getCircleBitmap(bitmap);
            int z = Math.min(b.getWidth(),b.getHeight());
            final Rect rectSrc = new Rect(0, 0, z , z);
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            canvas.drawBitmap(b, rectSrc, rectDest, null);
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int min = Math.min(bitmap.getWidth(),bitmap.getHeight());
        Rect rect = new Rect(0, 0, min , min);
        paint.setAntiAlias(true);

        canvas.drawColor(Color.TRANSPARENT);

        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}

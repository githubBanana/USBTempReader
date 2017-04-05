package com.xs.mpandroidchardemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.xs.mpandroidchardemo.R;

/**
 * @Description
 * @Author xs.lin
 * @Date 2017/4/5 17:44
 */

public class SpeedPointer2 extends View implements Runnable {

    private Context context;
    private int fatherViewWidth,fatherViewHeight;
    private int wheelWidth,wheelHeight;
    private Bitmap wheel;
    private Paint pointPaint;

    public SpeedPointer2(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SpeedPointer2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setAntiAlias(true);
        BitmapDrawable bmpDrawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_pointer);
        wheel = bmpDrawable.getBitmap();
        wheelWidth = wheel.getWidth();
        wheelHeight = wheel.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        fatherViewWidth = getWidth();
        fatherViewWidth = getHeight();
        wheel = Bitmap.createScaledBitmap(wheel,(int) (wheelWidth * 1f), (int) (wheelHeight * 1f), false);
        canvas.drawBitmap(wheel, fatherViewWidth * 0.075f, fatherViewHeight * 0.45f, pointPaint);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void run() {

    }
}

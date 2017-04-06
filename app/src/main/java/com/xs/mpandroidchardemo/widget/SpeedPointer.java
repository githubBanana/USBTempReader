package com.xs.mpandroidchardemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xs.mpandroidchardemo.R;

/**
 * @Description
 * @Author xs.lin
 * @Date 2017/4/5 17:44
 */

public class SpeedPointer extends View implements Runnable {

    private Context context;
    private int fatherViewWidth,fatherViewHeight;
    private int wheelWidth,wheelHeight;
    private Bitmap wheel;
    private Paint pointPaint,timeTextPaint,tempTextPaint;
    private static float ANGLE = 360 / 14;//每摄氏度所占的角度
    private float dAngleValue;//角度值总变化量
    private float previewValue;//前一次的温度值
    private static boolean isNotContinueThread = false;
    private String realTimeTempValue = "";
    private String realTime = "";

    public SpeedPointer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SpeedPointer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        timeTextPaint = new Paint();
        timeTextPaint.setColor(getResources().getColor(android.R.color.darker_gray));
        timeTextPaint.setStrokeWidth(10f);

        tempTextPaint = new Paint();
        tempTextPaint.setColor(0xff9dd9d7);
        tempTextPaint.setStrokeWidth(20f);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setAntiAlias(true);

        BitmapDrawable bmpDrawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_pointer);
        wheel = bmpDrawable.getBitmap();
        wheelWidth = wheel.getWidth();
        wheelHeight = wheel.getHeight();
        wheel = Bitmap.createScaledBitmap(wheel,(int) (wheelWidth * 0.93f), (int) (wheelHeight * 0.93f), false);

        previewValue = 37.0f;
//        setValueInit();//默认指针定位到32摄氏度
//        new Thread(this).start();
    }

    public void setValueInit() {
        dAngleValue = (32 - previewValue) * ANGLE;
        postView();
        previewValue = 32;
    }

    public void setValue(float value) {
        realTimeTempValue = String.valueOf(value)+"℃";
        dAngleValue = (value - previewValue) * ANGLE;
        postView();
    }

    private void setTime(String time) {
        this.realTime = time;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        fatherViewWidth = getWidth();
        fatherViewHeight = getHeight();
        tempTextPaint.setTextSize(fatherViewWidth * 0.12f);
        timeTextPaint.setTextSize(fatherViewWidth * 0.06f);

        canvas.rotate(dAngleValue,fatherViewWidth / 2,fatherViewHeight / 2);
        canvas.drawBitmap(wheel, fatherViewWidth * 0.188f, fatherViewHeight * 0.14f, pointPaint);

//        canvas.drawText(realTime,fatherViewWidth / 2 * 0.55f,fatherViewHeight / 2 * 0.8f,timeTextPaint);
//        canvas.drawText(realTimeTempValue,fatherViewWidth / 2 * 0.745f,fatherViewHeight / 2 * 1.1f,tempTextPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isNotContinueThread = true;
    }

    private void postView() {
        post(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
            }
        });
    }

    @Override
    public void run() {

    }
/*
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {

            if (isNotContinueThread)
                break;
            if (dAngleValue !=  0) {
                dnAngle = dAngleValue / 10;
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postView();
                }
                dnAngle = 0;
                dAngleValue = 0;
            }
        }
    }*/


}

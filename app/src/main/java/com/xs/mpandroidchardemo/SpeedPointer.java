package com.xs.mpandroidchardemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by siushen on 2016/3/6.
 */
public class SpeedPointer extends View implements Runnable {
    private Context mContext;
    private Bitmap auto_wheel;
    private int         wheel_width;
    private int         wheel_height;
    private int         fatherView_width,fatherView_height;
    private Paint paint;
    private Paint speedPoint_paint;
    private int         goleSpeed = 0;
    private int         speed_progress = 0;
    private boolean     control_threadDie;
    public void setSpeed(int speed) {
        goleSpeed = speed;
    }

    private OnListenSpeed mOnListenSpeed;
    public SpeedPointer(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SpeedPointer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        BitmapDrawable bmpDraw = (BitmapDrawable) getResources()
                .getDrawable(R.mipmap.tp_choose);
        auto_wheel = bmpDraw.getBitmap();
        wheel_width = auto_wheel.getWidth();
        wheel_height = auto_wheel.getHeight();

        paint = new Paint();
        paint.setColor(0xff9dd9d7);
        paint.setStrokeWidth(10f);
        speedPoint_paint  = new Paint(Paint.ANTI_ALIAS_FLAG);
        speedPoint_paint.setAntiAlias(true);
        new Thread(this).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        fatherView_width = getWidth();
        fatherView_height = getHeight();
        auto_wheel = Bitmap.createScaledBitmap(auto_wheel, (int) (fatherView_width * 0.86f), (int) (wheel_height * 1.15f), false);
        canvas.rotate(speed_progress*0.01f, fatherView_height / 2, fatherView_width / 2);
        canvas.drawBitmap(auto_wheel, fatherView_width * 0.075f, fatherView_height * 0.45f, speedPoint_paint);
        if (mOnListenSpeed != null)
        mOnListenSpeed.speedCallBack(speed_progress);
    }

    private void postSpeedView() {
        post(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
            }
        });
    }

    /**
     * 实时调动指针指向的线程
     */
    public void run() {
        while (true) {
                if(control_threadDie)
                    break;
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(speed_progress < goleSpeed) {
                    if((goleSpeed - speed_progress) < 1) {
                        speed_progress = goleSpeed;
                    }else {
                        speed_progress = speed_progress + 10;
                    }
                    postSpeedView();
            } else if(speed_progress > goleSpeed) {
                if((speed_progress - goleSpeed) < 1)
                    speed_progress = goleSpeed;
                else
                    speed_progress = speed_progress - 10;
                postSpeedView();
            } else {}

        }
    }
    public void setOnlistenSpeed(Context context) {
        mOnListenSpeed = (OnListenSpeed) context;
    }

    /**
     * 转速值回调
     */
    public interface OnListenSpeed {
        void speedCallBack(int speed);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        control_threadDie = true;
    }

}

package com.xs.mpandroidchardemo.manager;

import android.app.Service;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import com.xs.mpandroidchardemo.R;

/**
 * Created by Administrator on 2017/4/6.
 */
public class AlertManager {

    private Context context;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private static AlertManager instance;
    public static AlertManager getInstance(Context context) {
        if (instance == null) {
            synchronized (AlertManager.class) {
                instance = new AlertManager(context);
            }
        }
        return instance;
    }
    private AlertManager(Context context){
        this.context = context;
        mediaPlayer = new MediaPlayer();
        vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    }


    public void start() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer = MediaPlayer.create(context, R.raw.circuit);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            vibrator.vibrate(30000);
        }
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.stop();
        if (vibrator != null && vibrator.hasVibrator())
            vibrator.cancel();
    }


}

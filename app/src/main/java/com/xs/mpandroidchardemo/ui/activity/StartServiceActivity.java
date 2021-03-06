package com.xs.mpandroidchardemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xs.mpandroidchardemo.service.UsbService;
import com.xs.mpandroidchardemo.event.NotifyEvent;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Simon on 2017/4/4.
 */
public class StartServiceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        moveTaskToBack(true);
        EventBus.getDefault().register(this);
        startService(new Intent(this,UsbService.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        moveTaskToBack(true);
    }

    public void touch(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void OnEvent(String finish) {
        if (NotifyEvent.FNIISH_APP.equals(finish)) {
            finish();
        }
    }
}

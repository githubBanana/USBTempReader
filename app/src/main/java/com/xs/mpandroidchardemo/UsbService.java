package com.xs.mpandroidchardemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.xs.mpandroidchardemo.event.NotifyEvent;

import de.greenrobot.event.EventBus;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @Description
 * @Author xs.lin
 * @Date 2017/4/7 19:10
 */

public class UsbService extends Service {

    private String TAG = "lalal";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        new ReadUsbThread().start();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mUsbReceiver, filter);
        Log.e(TAG, "onCreate: " );
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG, "onStart: " );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " );
        initUsbData();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
        unregisterReceiver(mUsbReceiver);
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            String deviceName = usbDevice.getDeviceName();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                EventBus.getDefault().post(NotifyEvent.FNIISH_APP);
                stopSelf();
            }
        }
    };

    class ReadUsbThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(UsbService.this,ViewPagerActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
        }
    }

    public void initUsbData() {

    }

}

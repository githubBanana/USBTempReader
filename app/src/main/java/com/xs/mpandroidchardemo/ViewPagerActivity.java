package com.xs.mpandroidchardemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.xs.mpandroidchardemo.adapter.MyFragmentPagerAdapter;
import com.xs.mpandroidchardemo.entity.RecordBean;
import com.xs.mpandroidchardemo.event.NotifyEvent;
import com.xs.mpandroidchardemo.utils.TimeHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import static android.hardware.usb.UsbConstants.USB_DIR_OUT;

/**
 * Created by Administrator on 2017/4/4.
 */
public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.iv_1)                ImageView           mImg1;
    @Bind(R.id.iv_2)                ImageView           mImg2;
    @Bind(R.id.iv_3)                ImageView           mImg3;
    @Bind(R.id.iv_4)                ImageView           mImg4;
    private List<ImageView> _ivs = new ArrayList<>();

    //设备列表
    private HashMap<String, UsbDevice> deviceList;
    //从设备读数据
    private Button read_btn;
    //给设备写数据（发指令）
    private Button write_btn;
    //USB管理器:负责管理USB设备的类
    private UsbManager manager;
    //找到的USB设备
    private UsbDevice mUsbDevice;
    //代表USB设备的一个接口
    private UsbInterface mInterface;
    private UsbDeviceConnection mDeviceConnection;
    //代表一个接口的某个节点的类:写数据节点
    private UsbEndpoint usbEpOut;
    //代表一个接口的某个节点的类:读数据节点
    private UsbEndpoint usbEpIn;
    //要发送信息字节
    private byte[] sendbytes;
    //接收到的信息字节
    private byte[] receiveytes;
    private boolean isBack = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ButterKnife.bind(this);
        initView();
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);
        initUsbData();
        sendToUsb();
        readFromUsb();
    }

    private void sendToUsb() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(1000);
                int ret = -1;
                // 发送准备命令
                byte[] bytes=new byte[]{0x21,0x09,0x00,0x02,0x00,0x00,0x08,0x00};
                int e=0;
                while (true){
                    if (isBack || mDeviceConnection == null){
                        return;
                    }
                    if (true){//sp.getBoolean("dahai",false)
                        Log.e("HHH", "run: " + "有数据" );
                        int i = mDeviceConnection.controlTransfer(33, 9, 0, 0, bytes, bytes.length, 1000);
                        receiveytes=new byte[64];
                        ret = mDeviceConnection.bulkTransfer(usbEpIn, receiveytes, receiveytes.length, 200);
                        if (i!=-1 || ret!=-1){
                            Message message=Message.obtain();
                            message.what=1;
                            message.obj=receiveytes;
                            handler.sendMessage(message);
                        }
                    } else {
                        Log.e("HHH", "run: " + "完了" );
                        handler.sendEmptyMessage(0);
                    }
                    SystemClock.sleep(1000 * 60 * 2);
                }
            }
        }).start();
    }

    private void readFromUsb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //读取数据2
                while (true){
                    if (usbEpIn==null){
                        continue;
                    }
                    int inMax = usbEpIn.getMaxPacketSize();
                    byte[] buffer =new byte[inMax];
                    int ret = mDeviceConnection.bulkTransfer(usbEpIn, buffer, buffer.length, 300);
                    byte b = buffer[3];
                    Log.e("HHH", "run 读取的状态 " + b + "--------------------------" + Arrays.toString(buffer));
                    if (b==1){
                    } else if (b==2){
                    }
                    SystemClock.sleep(500);
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    EventBus.getDefault().post(NotifyEvent.STATUS_CONN);
                    byte[] bytes = (byte[]) msg.obj;
                    byte vTemperatureL = bytes[0];
                    byte vTemperatureH = bytes[1];
                    byte vCnt = bytes[2];
                    byte vErr = bytes[3];

                    float tem = (vTemperatureH * 256 + vTemperatureL + vErr) / (float)10;

//                    doubles.add(tem);
                    Log.e("HHH", "handleMessage: "+tem+"C  " + Arrays.toString(bytes));
                    RecordBean recordBean = new RecordBean();
                    recordBean.setValue(tem);
                    recordBean.setTime(TimeHelper.getToday());
                    recordBean.setMin(TimeHelper.getBetweenMinutes());
                    EventBus.getDefault().post(recordBean);
                    break;

                case -1:
//                    mText.setText("关闭");
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isBack = true;
    }

    private void initView() {
        _ivs.add(mImg1);
        _ivs.add(mImg2);
        _ivs.add(mImg3);
        _ivs.add(mImg4);
        setImageViewTrue(0);
    }

    private void setImageViewTrue(int position) {
        for (int i = 0; i < _ivs.size(); i++) {
            if (position == i)
                _ivs.get(i).setEnabled(true);
            else
                _ivs.get(i).setEnabled(false);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageViewTrue(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initUsbData() {

        // 获取USB设备
        manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //获取到设备列表
        deviceList = manager.getDeviceList();
        boolean empty = deviceList.isEmpty();
        if (!empty) {
            // deviceList不为空
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            Log.e("HHH", "initUsbData: " + deviceIterator.toString() );
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();
                // 输出设备信息
                Log.e("HHH", "DeviceInfo: " + device.getVendorId() + " , "
                        + device.getProductId());
                System.out.println("DeviceInfo:" + device.getVendorId()
                        + " , " + device.getProductId());
                // 保存设备VID和PID
                int vendorId = device.getVendorId();
                int productId = device.getProductId();


                showTmsg(vendorId+"不为空");
                // 保存匹配到的设备
                if (vendorId == 4760 && productId == 561) {
                    mUsbDevice = device; // 获取USBDevice
                    System.out.println("发现待匹配设备:" + device.getVendorId()
                            + "," + device.getProductId());
                    Context context = getApplicationContext();
                    showTmsg("发现待匹配设备");
                }
            }
        } else {
            showTmsg("空");
        }

        // }
        //获取设备接口
        if (mUsbDevice==null) {
            showTmsg("usb不存在");
            return;
        }
        for (int i = 0; i < mUsbDevice.getInterfaceCount(); i++) {
            // 一般来说一个设备都是一个接口，你可以通过getInterfaceCount()查看接口的个数
            // 这个接口上有两个端点，分别对应OUT 和 IN
            UsbInterface usbInterface = mUsbDevice.getInterface(i);
            mInterface = usbInterface;
            break;
        }
        //用UsbDeviceConnection 与 UsbInterface 进行端点设置和通讯
        int endpointCount = mInterface.getEndpointCount();


        for (int i = 0; i < mInterface.getEndpointCount(); i++) {

            UsbEndpoint ep = mInterface.getEndpoint(i);

            if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_INT) {
                if (ep.getDirection() == USB_DIR_OUT) {
                    Log.e("HHH", "initUsbData: " + "写的" );
                } else {
                    Log.e("HHH", "initUsbData: " + "读的" );
                }
            }
        }

        Log.e("HHH", "initUsbData: " + endpointCount );
        if (endpointCount>0){
            if (mInterface.getEndpoint(0) != null) {
                usbEpIn = mInterface.getEndpoint(0);
            }
        }
        if (endpointCount>1){
            if (mInterface.getEndpoint(1) != null) {
                usbEpOut = mInterface.getEndpoint(1);
            }
        }

        if (mInterface != null) {
            // 判断是否有权限
            if (manager.hasPermission(mUsbDevice)) {
                // 打开设备，获取 UsbDeviceConnection 对象，连接设备，用于后面的通讯
                mDeviceConnection = manager.openDevice(mUsbDevice);
                if (mDeviceConnection == null) {
                    return;
                }
                if (mDeviceConnection.claimInterface(mInterface, true)) {
                    showTmsg("找到设备接口");

                } else {
                    mDeviceConnection.close();
                }
            } else {
                showTmsg("没有权限");
            }
        } else {
            showTmsg("没有找到设备接口！");
        }
    }

    private void showTmsg(String msg) {
//        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}

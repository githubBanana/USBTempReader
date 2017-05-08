package com.xs.mpandroidchardemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xs.mpandroidchardemo.R;
import com.xs.mpandroidchardemo.adapter.MyFragmentPagerAdapter;
import com.xs.mpandroidchardemo.entity.RecordBean;
import com.xs.mpandroidchardemo.event.NotifyEvent;
import com.xs.mpandroidchardemo.manager.AlertManager;
import com.xs.mpandroidchardemo.manager.widget.ControlViewPaper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Administrator on 2017/4/4.
 */
public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.viewPager)
    ControlViewPaper viewPager;
    @Bind(R.id.rg_main)
    RadioGroup radioGroup;
    @Bind(R.id.rb_setting)
    RadioButton rbSetting;
    @Bind(R.id.iv_1)                ImageView           mImg1;
    @Bind(R.id.iv_2)                ImageView           mImg2;
    @Bind(R.id.iv_3)                ImageView           mImg3;
    @Bind(R.id.iv_4)                ImageView           mImg4;
    private List<ImageView> _ivs = new ArrayList<>();

    private static final String REALTIME_VALUE = "realtime_value";//实时温度数据
    private static final String BUNDLE_REATIME_VALUE = "bundle_realtime_value";

    public static void start(Context context,RecordBean recordBean) {
        Intent intent = new Intent(context,ViewPagerActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(REALTIME_VALUE,recordBean);
        intent.putExtra(BUNDLE_REATIME_VALUE,bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();

        RecordBean recordBean = new RecordBean();
        if (getIntent() != null) {
            Bundle bundle = getIntent().getBundleExtra(BUNDLE_REATIME_VALUE);
            if (bundle != null) {
                recordBean = (RecordBean) bundle.getSerializable(REALTIME_VALUE);
            }
        }
        viewPager.setAdapter(new MyFragmentPagerAdapter(this,getSupportFragmentManager(),recordBean));
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setDisableScroll(true);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AlertManager.getInstance(this).stop();
        EventBus.getDefault().unregister(this);
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
//        setImageViewTrue(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private long _exitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK &&  event.getAction()==KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - _exitTime) > 2000) {
                Toast.makeText(getApplicationContext(),"再按一次返回手机主界面", Toast.LENGTH_SHORT).show();
                _exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    @Subscribe
    public void onEvent(String finish) {
        if (NotifyEvent.FNIISH_APP.equals(finish)) {
            finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.rb_setting:
                Log.e("info", "onCheckedChanged: " );
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.rb_record:
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.rb_auxiliaryfunc:
                viewPager.setCurrentItem(3, false);
                break;
            default:
                break;
        }
    }
}

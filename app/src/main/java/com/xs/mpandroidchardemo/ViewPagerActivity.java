package com.xs.mpandroidchardemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.xs.mpandroidchardemo.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ButterKnife.bind(this);
        initView();
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);
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
}

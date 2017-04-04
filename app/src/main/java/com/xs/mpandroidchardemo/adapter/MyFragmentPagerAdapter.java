package com.xs.mpandroidchardemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xs.mpandroidchardemo.fragment.AuxiliaryFuncFragment;
import com.xs.mpandroidchardemo.fragment.MainFragment;
import com.xs.mpandroidchardemo.fragment.RecordFragment;
import com.xs.mpandroidchardemo.fragment.SettingFragment;

/**
 * Created by Administrator on 2017/4/4.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MainFragment();
                break;
            case 1:
                fragment = new SettingFragment();
                break;
            case 2:
                fragment = new RecordFragment();
                break;
            case 3:
                fragment = new AuxiliaryFuncFragment();
                break;
            default:break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}

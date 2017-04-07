package com.xs.mpandroidchardemo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xs.mpandroidchardemo.entity.RecordBean;
import com.xs.mpandroidchardemo.ui.fragment.AuxiliaryFuncFragment;
import com.xs.mpandroidchardemo.ui.fragment.MainFragment;
import com.xs.mpandroidchardemo.ui.fragment.RecordFragment;
import com.xs.mpandroidchardemo.ui.fragment.SettingFragment;

/**
 * Created by Simon on 2017/4/4.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private RecordBean recordBean;
    private Context context;

    public MyFragmentPagerAdapter(Context context,FragmentManager fm, RecordBean recordBean) {
        super(fm);
        this.context = context;
        this.recordBean = recordBean;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = MainFragment.newInstance(context,recordBean);
                break;
            case 1:
                fragment = SettingFragment.newInstance(context,recordBean);
                break;
            case 2:
                fragment = RecordFragment.newInstance(context,recordBean);
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

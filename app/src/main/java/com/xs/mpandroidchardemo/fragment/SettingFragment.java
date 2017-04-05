package com.xs.mpandroidchardemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.kyleduo.switchbutton.SwitchButton;
import com.xs.mpandroidchardemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/4.
 */
public class SettingFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{

    @Bind(R.id.sb_unit)
    SwitchButton unitSwitchBtn;
    @Bind(R.id.sb_tip)
    SwitchButton tipSwitchBtn;
    @Bind(R.id.sb_conn)
    SwitchButton connSwitchBtn;
    @Bind(R.id.ll_tip)
    LinearLayout linearLayoutTip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        linearLayoutTip.setVisibility(View.GONE);
        unitSwitchBtn.setOnCheckedChangeListener(this);
        tipSwitchBtn.setOnCheckedChangeListener(this);
        connSwitchBtn.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sb_unit:

                break;
            case R.id.sb_conn:

                break;
            case R.id.sb_tip:
                if (isChecked)
                    linearLayoutTip.setVisibility(View.VISIBLE);
                else
                    linearLayoutTip.setVisibility(View.GONE);
                break;
            default:break;
        }
    }
}

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
import com.xs.mpandroidchardemo.adapter.RecordAdapter;
import com.xs.mpandroidchardemo.entity.RecordBean;
import com.xs.mpandroidchardemo.event.NotifyEvent;
import com.xs.mpandroidchardemo.manager.AlertManager;
import com.xs.mpandroidchardemo.utils.Constant;
import com.xs.mpandroidchardemo.utils.SharePreferenceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

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
    private float currentTempValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        linearLayoutTip.setVisibility(View.GONE);
        unitSwitchBtn.setOnCheckedChangeListener(this);
        tipSwitchBtn.setOnCheckedChangeListener(this);
        connSwitchBtn.setOnCheckedChangeListener(this);
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
      /*  boolean isConn = SharePreferenceUtil.getBoolean(getContext(),"isConn");
        if (isConn) {
            connSwitchBtn.setChecked(true);
        } else {
            connSwitchBtn.setChecked(false);
        }*/

        int unit = SharePreferenceUtil.getInt(getContext(),"unit");
        if (unit == 0) {
            unitSwitchBtn.setChecked(true);
        } else {
            unitSwitchBtn.setChecked(false);
        }

        boolean isAlert = SharePreferenceUtil.getBoolean(getContext(),"isAlert");
        if (isAlert) {
            tipSwitchBtn.setChecked(true);
        } else {
            tipSwitchBtn.setChecked(false);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sb_unit:
                if (isChecked)
                    SharePreferenceUtil.setValue(getContext(),Constant.UNIT,0);//摄氏度
                else
                    SharePreferenceUtil.setValue(getContext(),Constant.UNIT,1);//华氏度
                break;
            case R.id.sb_conn:
                if (isChecked)
                    SharePreferenceUtil.setValue(getContext(),Constant.IS_CONNN,true);
                else
                    SharePreferenceUtil.setValue(getContext(),Constant.IS_CONNN,false);
                break;
            case R.id.sb_tip:
                if (isChecked) {
                    linearLayoutTip.setVisibility(View.VISIBLE);
                    SharePreferenceUtil.setValue(getContext(),Constant.IS_ALERT,true);
                    if (currentTempValue > Constant.HIGH) {
                        AlertManager.getInstance(getContext()).start();
                    }
                } else {
                    SharePreferenceUtil.setValue(getContext(),Constant.IS_ALERT,false);
                    linearLayoutTip.setVisibility(View.GONE);
                    AlertManager.getInstance(getContext()).stop();
                    currentTempValue = 0;
                }
                break;
            default:break;
        }
    }

    @Subscribe
    public void onEvent(String status) {
        if (NotifyEvent.STATUS_CONN.equals(status)) {
            connSwitchBtn.setChecked(true);

        } else if (NotifyEvent.STATUS_DISCONN.equals(status)) {
            connSwitchBtn.setChecked(false);
        }
    }

    @Subscribe
    public void onEvent(RecordBean recordBean) {
        this.currentTempValue = recordBean.getValue();
    }
}

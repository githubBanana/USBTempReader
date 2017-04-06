package com.xs.mpandroidchardemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xs.mpandroidchardemo.R;
import com.xs.mpandroidchardemo.entity.AppDatabaseCache;
import com.xs.mpandroidchardemo.entity.RecordBean;
import com.xs.mpandroidchardemo.event.NotifyEvent;
import com.xs.mpandroidchardemo.utils.TimeHelper;
import com.xs.mpandroidchardemo.widget.SpeedPointer;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2017/4/4.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.pointer)
    SpeedPointer speedPointer;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_value)
    TextView tvValue;
    @Bind(R.id.tv_status_conn)
    TextView tvStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(RecordBean recordBean) {
        float value = recordBean.getValue() < 32 ? 31.4f : recordBean.getValue();
        speedPointer.setValue(value);
        tvTime.setText(TimeHelper.getDetailCurrDate());
        tvValue.setText(recordBean.getValue()+"℃");

        /*保存数据库*/
        if (recordBean.getValue() < 34)
            recordBean.setValue(34);
        if (recordBean.getValue() > 42)
            recordBean.setValue(42);
        AppDatabaseCache.getcache(getContext()).insertRecord(recordBean);
    }

    @Subscribe
    public void onEvent(String status) {
        if (NotifyEvent.STATUS_CONN.equals(status)) {
            tvStatus.setText("已连接");
        } else if(NotifyEvent.STATUS_DISCONN.equals(status)) {
            tvStatus.setText("未连接");
        }
    }

}

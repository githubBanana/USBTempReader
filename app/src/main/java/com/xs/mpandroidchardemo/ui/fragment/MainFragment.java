package com.xs.mpandroidchardemo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xs.mpandroidchardemo.R;
import com.xs.mpandroidchardemo.manager.AlertManager;
import com.xs.mpandroidchardemo.manager.db.AppDatabaseCache;
import com.xs.mpandroidchardemo.entity.RecordBean;
import com.xs.mpandroidchardemo.utils.Constant;
import com.xs.mpandroidchardemo.utils.SharePreferenceUtil;
import com.xs.mpandroidchardemo.utils.TimeHelper;
import com.xs.mpandroidchardemo.widget.SpeedPointer;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Simon on 2017/4/4.
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

    private static final String REALTIME_VALUE = "realtime_value";

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(Context context, RecordBean recordBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(REALTIME_VALUE, recordBean);
        return (MainFragment) Fragment.instantiate(context, MainFragment.class.getName(), bundle);
    }

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
        if (getArguments() != null) {
            RecordBean recordBean = (RecordBean) getArguments().getSerializable(REALTIME_VALUE);
            onEvent(recordBean);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(final RecordBean recordBean) {
        if (recordBean.getValue() == 0)
            return;
        if (SharePreferenceUtil.getBoolean(getContext(), Constant.IS_ALERT)) {
            if (recordBean.getValue() >= Constant.HIGH)
                AlertManager.getInstance(getContext()).start();
            else
                AlertManager.getInstance(getContext()).stop();
        }

        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    float value = recordBean.getValue() < 32 ? 31.4f : recordBean.getValue();
                    speedPointer.setValue(value);
                    tvTime.setText(TimeHelper.getDetailCurrDate());
                    tvValue.setText(recordBean.getValue()+"℃");
                    tvStatus.setText("已连接");
                }
            });
        }

        /*保存数据库*/
        if (recordBean.getValue() < 34)
            recordBean.setValue(34);
        if (recordBean.getValue() > 42)
            recordBean.setValue(42);
        AppDatabaseCache.getcache(getContext()).insertRecord(recordBean);
    }
}

package com.xs.mpandroidchardemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.xs.mpandroidchardemo.ChartActivity;
import com.xs.mpandroidchardemo.R;
import com.xs.mpandroidchardemo.adapter.RecordAdapter;
import com.xs.mpandroidchardemo.manager.db.AppDatabaseCache;
import com.xs.mpandroidchardemo.entity.RecordBean;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2017/4/4.
 */
public class RecordFragment extends Fragment {

    @Bind(R.id.recycler_view)
    SwipeMenuRecyclerView recyclerView;
    @Bind(R.id.multi_state_view)
    MultiStateView multiStateView;
    @Bind(R.id.tv_day)
    TextView tvDay;

    private RecordAdapter recordAdapter;
    private List<RecordBean> recordBeanList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        String day = Calendar.getInstance().get(Calendar.DATE)+"";
        tvDay.setText(day.length() == 1 ? "0"+day : day);
        recyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.item_height);
                SwipeMenuItem item = new SwipeMenuItem(getActivity());
                item.setImage(R.drawable.ic_cancel_red_24dp)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(width)
                        .setText("删除");
                swipeRightMenu.addMenuItem(item);
            }
        });

        recyclerView.setSwipeMenuItemClickListener(new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
                AppDatabaseCache.getcache(getContext()).deleteRecord(recordAdapter.getItem(adapterPosition));
                recordBeanList = AppDatabaseCache.getcache(getContext()).queryAllRecord();
                recordAdapter.remove(adapterPosition);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemViewSwipeEnabled(false); // 开启滑动删除
        recordAdapter = new RecordAdapter(getContext());
        recyclerView.setAdapter(recordAdapter);
        recordAdapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item, int position) {
                ChartActivity.start(getActivity(),item);
            }
        });

       queryData();
    }

    private void queryData() {
        new Thread(){
            @Override
            public void run() {
                recordBeanList = AppDatabaseCache.getcache(getContext()).queryAllRecord();
                if (recordBeanList == null || recordBeanList.size() == 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                        }
                    });
                } else {
                    HashSet<String> hashSet = new HashSet();
                    for (RecordBean rb:
                            recordBeanList) {
                        hashSet.add(rb.getTime());
                    }
                    Iterator<String> iterator = hashSet.iterator();
                    final List<String> recordBeanTemp = new ArrayList<>();
                    while (iterator.hasNext()) {
                        recordBeanTemp.add(iterator.next());
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recordAdapter.setData(recordBeanTemp);
                            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(RecordBean recordBean) {
        if (recordBeanList == null || recordBeanList.size() == 0) {
            queryData();
        }
    }
}

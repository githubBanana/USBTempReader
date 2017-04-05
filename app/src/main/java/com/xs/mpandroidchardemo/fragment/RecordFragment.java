package com.xs.mpandroidchardemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kennyc.view.MultiStateView;
import com.xs.mpandroidchardemo.R;
import com.xs.mpandroidchardemo.adapter.RecordAdapter;
import com.xs.mpandroidchardemo.entity.RecordBean;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/4.
 */
public class RecordFragment extends Fragment {

    @Bind(R.id.recycler_view)
    SwipeMenuRecyclerView recyclerView;
    @Bind(R.id.multi_state_view)
    MultiStateView multiStateView;

    private RecordAdapter recordAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
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
                recordAdapter.remove(adapterPosition);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemViewSwipeEnabled(false); // 开启滑动删除
        List<RecordBean> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            RecordBean bean = new RecordBean();
            bean.setTime("1232-22-11"+i);
            list.add(bean);
        }
        recordAdapter = new RecordAdapter(getContext());
        recyclerView.setAdapter(recordAdapter);
        recordAdapter.setData(list);
        recordAdapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecordBean item, int position) {
                Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
            }
        });

        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }
}

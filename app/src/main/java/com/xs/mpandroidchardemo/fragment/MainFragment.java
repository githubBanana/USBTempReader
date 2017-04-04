package com.xs.mpandroidchardemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xs.mpandroidchardemo.R;

/**
 * Created by Administrator on 2017/4/4.
 */
public class MainFragment extends Fragment {

/*
    public static MainFragment newInstance(Context context) {
        Bundle bundle = new Bundle();
        return (MainFragment) Fragment.instantiate(, TopicCommentFragment.class.getName(), bundle);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }
}

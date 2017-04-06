package com.xs.mpandroidchardemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xs.mpandroidchardemo.R;
import com.xs.mpandroidchardemo.entity.RecordBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author xs.lin
 * @Date 2017/4/5 13:59
 */

public class RecordAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {

    private List<String> list;
    private Context context;

    public RecordAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.item_record,parent,false);
        return view;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new RecordViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecordAdapter.RecordViewHolder _holder = (RecordAdapter.RecordViewHolder) holder;
        _holder.itemView.setTag(_holder);
        _holder.setData(getItem(position),position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getItem(int position) {
        return list.get(position);
    }

    class RecordViewHolder extends RecyclerView.ViewHolder{

        TextView tvTime;
        View itemView;

        public RecordViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);

        }

        public void setData(final String item, final int position) {
            tvTime.setText(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(item,position);
                    }
                }
            });
        }
    }


    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void setData(Collection<String> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(String item,int position);
    }
}

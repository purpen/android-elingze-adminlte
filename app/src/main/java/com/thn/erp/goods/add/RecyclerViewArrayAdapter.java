package com.thn.erp.goods.add;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/4/2 18:57
 * Email: 895745843@qq.com
 */

public class RecyclerViewArrayAdapter extends RecyclerView.Adapter {
    private List<String> list;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


    public RecyclerViewArrayAdapter(List<String> strings, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.list = strings;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_textview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRecyclerViewItemClickListener != null) {
                    onRecyclerViewItemClickListener.onClick(view, holder.getAdapterPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public String getItem(int position){
        return list.get(position);
    }

    public void setList(List<String> list){
        if (this.list == null) {
            this.list = list;
        } else {
            this.list.clear();
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView tv;
        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}

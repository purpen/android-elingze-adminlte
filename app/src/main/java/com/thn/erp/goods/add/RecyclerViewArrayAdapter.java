package com.thn.erp.goods.add;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thn.erp.common.OnRecyclerViewItemClickListener;

import java.util.List;

/**
 * Created by Stephen on 2018/4/2 18:57
 * Email: 895745843@qq.com
 */

public class RecyclerViewArrayAdapter extends RecyclerView.Adapter {
    private Context context;
    private int resource;
    private List<String> list;
    private int textViewResourceId;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


    public RecyclerViewArrayAdapter(Context context, int resource, List<String> strings, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.context = context;
        this.resource = resource;
        this.list = strings;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public RecyclerViewArrayAdapter(Context context, int resource, int textViewResourceId, List<String> strings, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.list = strings;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(resource, parent, false);
        return new RecyclerView.ViewHolder(inflate) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.itemView instanceof TextView) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(list.get(position));
        } else {
            TextView textview = holder.itemView.findViewById(textViewResourceId);
            textview.setText(list.get(position));
        }
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
}

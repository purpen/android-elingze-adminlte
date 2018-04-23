package com.thn.erp.more.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;

import java.util.List;

/**
 * Created by Stephen on 2018/4/20 15:10
 * Email: 895745843@qq.com
 */

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {
    private List<String> stringList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public ToolsAdapter(List<String> list, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.stringList = list;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_more_tool_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (position < stringList.size()) {
            String text = stringList.get(position);
            holder.textViewEntry11.setText(text);
            holder.linearLayoutMoreStockExport.setVisibility(View.VISIBLE);
        } else {
            holder.linearLayoutMoreStockExport.setVisibility(View.INVISIBLE);
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
        return 4;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
            private LinearLayout linearLayoutMoreStockExport;
            private TextView textViewEntry11;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayoutMoreStockExport = (LinearLayout) itemView.findViewById(R.id.linearLayout_more_stock_export);
            textViewEntry11 = (TextView) itemView.findViewById(R.id.textView_entry_11);
        }
    }
}

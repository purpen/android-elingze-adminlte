package com.thn.erp.more.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.more.ToolsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/4/20 15:10
 * Email: 895745843@qq.com
 */

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {

    private List<ToolsActivity.ItemBean> list;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public ToolsAdapter(List<ToolsActivity.ItemBean> list, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.list = list;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_more_tool_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ToolsActivity.ItemBean itemBean = list.get(position);
        holder.title.setText(itemBean.title);
        THNGlideUtil.displayImageWithId(itemBean.resId,holder.imageView);
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
        return list.size();
    }

   static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}

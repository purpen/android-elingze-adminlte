package com.thn.erp.more.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.base.BaseRecyclerViewAdapter;
import com.thn.erp.base.BaseRecyclerViewViewHolder;
import com.thn.erp.common.interfaces.ImpRecyclerViewItemClick;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Stephen on 2018/3/12 18:07
 * Email: 895745843@qq.com
 */

public class RepositoryRecyclerViewAdapter extends BaseRecyclerViewAdapter {
    private LayoutInflater layoutInflater;
    private ImpRecyclerViewItemClick itemClick;
    private List<Map<String, String>> mapList;

    public RepositoryRecyclerViewAdapter(Context context, ImpRecyclerViewItemClick itemClick) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepositoryRecyclerViewAdapter.VH(layoutInflater.inflate(R.layout.item_recyclerview_repositorymanage, parent, false));
    }

    @Override
    public int getItemCount() {
        return mapList == null ? 0 :mapList.size();
    }

    @Override
    public void onBindBaseViewHolder(final BaseRecyclerViewViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.click(holder.getAdapterPosition());
                }
            }
        });

        Map<String, String> map = mapList.get(position);
        VH vh = (VH) holder;
        vh.textView1.setText(map.get(MapKeyInRepository.REPOSITORY_NAME));
        vh.textView2.setText(map.get(MapKeyInRepository.REPOSITORY_MANAGER));
    }

    class VH extends BaseRecyclerViewViewHolder {
        private TextView textView1;
        private TextView textView2;

        public VH(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.textView1);
            textView2 = (TextView) itemView.findViewById(R.id.textView2);
        }
    }

    public void addList(List<Map<String, String>> contentList) {
        if (mapList == null) {
            mapList = new ArrayList<>();
        }
        mapList.addAll(contentList);
        notifyDataSetChanged();
    }

    public void setList(List<Map<String, String>> contentList) {
        mapList = contentList;
        notifyDataSetChanged();
    }
}

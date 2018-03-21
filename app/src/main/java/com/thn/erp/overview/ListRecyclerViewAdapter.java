package com.thn.erp.overview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;
import com.thn.erp.common.GlobalCallBack;

import java.util.List;
import java.util.Map;

/**
 * Created by Stephen on 2017/3/3 21:20
 * Email: 895745843@qq.com
 */

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.VH> {

    private LayoutInflater mLayoutInflater;
    private GlobalCallBack mGlobalCallBack;
    private List<Object> list;

    public ListRecyclerViewAdapter(Context context, GlobalCallBack globalCallBack) {
        this.mGlobalCallBack = globalCallBack;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ListRecyclerViewAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListRecyclerViewAdapter.VH(mLayoutInflater.inflate(R.layout.item_recyclerview_product2, null));
    }

    @Override
    public void onBindViewHolder(ListRecyclerViewAdapter.VH holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        final Object o = list.get(adapterPosition);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGlobalCallBack != null) {
                    mGlobalCallBack.callBack(o);
                }
            }
        });
        if (list.size() != 0) {
            THNGlideUtil.displayImage(/*list.get(adapterPosition).getCover_url()*/"", holder.productImg);
            Object o1 = list.get(adapterPosition);
            if (o1 instanceof Map) {
                Map<String, String> map = (Map<String,String>) o1;
                holder.name.setText(map.get("name"));
                holder.productImg.setImageResource(Integer.valueOf(map.get("img")));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView productImg;
        TextView name;
        TextView price;

        public VH(View itemView) {
            super(itemView);
            productImg = (ImageView)itemView. findViewById(R.id.product_img);
            name = (TextView)itemView. findViewById(R.id.name);
        }
    }

    public void putList(List<Object> categoryListItems){
        this.list = categoryListItems;
        notifyDataSetChanged();
    }
}

package com.thn.erp.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Stephen on 2018/3/12 18:09
 * Email: 895745843@qq.com
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewViewHolder> {

    public BaseRecyclerViewAdapter() {
        super();
    }

    @NonNull
    @Override
    public void onBindViewHolder(BaseRecyclerViewViewHolder holder, int position) {
        onBindBaseViewHolder((BaseRecyclerViewViewHolder)holder, position);
    }

    public abstract void onBindBaseViewHolder(BaseRecyclerViewViewHolder holder, int position);
}

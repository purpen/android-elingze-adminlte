package com.thn.erp.overview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.basemodule.tools.GlideUtil;
import com.thn.erp.R;
import com.thn.erp.common.interfaces.GlobalCallBack;
import com.thn.erp.overview.bean.CustomMenuBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Stephen on 2017/3/3 21:20
 * Email: 895745843@qq.com
 */

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SELECT_MENU = 0x0010;
    private int size;
    private GlobalCallBack mGlobalCallBack;
    private List<CustomMenuBean> list;
    private int imgSize;
    public ListRecyclerViewAdapter(@NonNull List<CustomMenuBean> list, GlobalCallBack globalCallBack) {
        this.mGlobalCallBack = globalCallBack;
        this.list = list;
        this.size = list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        imgSize = context.getResources().getDimensionPixelSize(R.dimen.dp50);
        if (viewType==VIEW_TYPE_SELECT_MENU){
            ImageButton imageButton = new ImageButton(parent.getContext());
            imageButton.setBackground(null);
            return new SelectMenuViewHolder(imageButton);
        }
        return new ListRecyclerViewAdapter.VH( LayoutInflater.from(context).inflate(R.layout.item_recyclerview_product2, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SelectMenuViewHolder){
            GlideUtil.loadImageWithDimen(R.mipmap.icon_menu_more, ((SelectMenuViewHolder) holder).imageButton,imgSize);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(),CustomMenuSelectActivity.class));
                }
            });
        }else {
            VH holder1 = (VH) holder;
            final CustomMenuBean customMenuBean = list.get(position);
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mGlobalCallBack != null) {
                        mGlobalCallBack.callBack(customMenuBean);
                    }
                }
            });

            holder1.name.setText(customMenuBean.title);
            GlideUtil.loadImageWithDimen(customMenuBean.iconId, holder1.productImg,imgSize);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==size){
            return VIEW_TYPE_SELECT_MENU;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return size+1;
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

    static class SelectMenuViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton;
        SelectMenuViewHolder(ImageButton imageButton) {
            super(imageButton);
            this.imageButton = imageButton;
        }
    }
}

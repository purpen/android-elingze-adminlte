package com.thn.erp.sale.adapter;

/**
 * Created by Marshal Chen on 3/8/2016.
 */

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;
import com.thn.erp.sale.bean.GoodsData;

import java.security.SecureRandom;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DXOrderGoodsAdapter extends UltimateViewAdapter {

    private List<GoodsData.DataBean.ProductsBean> list;
    public DXOrderGoodsAdapter(List<GoodsData.DataBean.ProductsBean> list) {
        this.list = list;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= list.size() : position < list.size()) && (customHeaderView != null ? position > 0 : true)) {
            GoodsData.DataBean.ProductsBean goods = list.get(customHeaderView != null ? position - 1 : position);
            DXOrderGoodsAdapter.ViewHolder viewHolder = ((DXOrderGoodsAdapter.ViewHolder) holder);
            viewHolder.goodsName.setText(goods.name);
            THNGlideUtil.displayImage(goods.cover,viewHolder.ivCover,R.mipmap.ic_launcher);
            viewHolder.tvNum.setText("编号："+goods.rid);
            viewHolder.price.setText(""+goods.sale_price);
            viewHolder.stockNum.setText(""+position);
        }

    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_edit_goods_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(GoodsData.DataBean.ProductsBean goods, int position) {
        insertInternal(list, goods, position);
    }

    public void remove(int position) {
        removeInternal(list, position);
    }

    public void clear() {
        clearInternal(list);
    }


    public void swapPositions(int from, int to) {
        swapPositions(list, from, to);
    }


    @Override
    public long generateHeaderId(int position) {
        // URLogs.d("position--" + position + "   " + getItem(position));
//        if (getItem(position).length() > 0)
//            return getItem(position).charAt(0);
//        else return -1;
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stick_header_item, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.stick_text);
//        textView.setText(String.valueOf(getItem(position).charAt(0)));
//        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AAffffff"));
        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.stick_img);

        SecureRandom imgGen = new SecureRandom();
        switch (imgGen.nextInt(3)) {
            case 0:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
        }

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition > 0 && toPosition > 0) {
            swapPositions(fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
            super.onItemMove(fromPosition, toPosition);
        }

    }

    @Override
    public void onItemDismiss(int position) {
        if (position > 0) {
            remove(position);
            // notifyItemRemoved(position);
//        notifyDataSetChanged();
            super.onItemDismiss(position);
        }

    }


    public void setOnDragStartListener(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

    }

    class ViewHolder extends UltimateRecyclerviewViewHolder {

        @BindView(R.id.ivCover)
        ImageView ivCover;
        @BindView(R.id.goodsName)
        TextView goodsName;
        @BindView(R.id.tvNum)
        TextView tvNum;
        @BindView(R.id.price)
        EditText price;
        @BindView(R.id.stockNum)
        EditText stockNum;
        @BindView(R.id.itemview)
        RelativeLayout itemview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public GoodsData.DataBean.ProductsBean getItem(int position) {
        if (customHeaderView != null)
            position--;
        // URLogs.d("position----"+position);
        if (position >= 0 && position < list.size())
            return list.get(position);
        else return null;
    }

}
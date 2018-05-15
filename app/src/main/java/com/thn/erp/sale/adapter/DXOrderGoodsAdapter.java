package com.thn.erp.sale.adapter;

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
import com.thn.erp.R;
import com.thn.erp.sale.bean.SKUListData;
import com.thn.erp.utils.GlideUtil;
import com.thn.erp.view.CounterView;

import java.security.SecureRandom;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DXOrderGoodsAdapter extends UltimateViewAdapter {
    private DXOrderGoodsAdapter.ViewHolder viewHolder;
    private List<SKUListData.DataBean.ItemsBean> list;
    public DXOrderGoodsAdapter(List<SKUListData.DataBean.ItemsBean> list) {
        this.list = list;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= list.size() : position < list.size()) && (customHeaderView != null ? position > 0 : true)) {
            final SKUListData.DataBean.ItemsBean goods = list.get(customHeaderView != null ? position - 1 : position);
            viewHolder = ((DXOrderGoodsAdapter.ViewHolder) holder);
            viewHolder.goodsName.setText(goods.product_name);
            GlideUtil.loadImage(goods.cover,viewHolder.ivCover);
            viewHolder.tvNum.setText("编号："+goods.rid);
            viewHolder.price.setText(""+goods.sale_price);
            viewHolder.counterView.setStorageNum(goods.stock_count);
            viewHolder.counterView.setValue(""+goods.buyNum);
            viewHolder.counterView.setOnValueChangedListener(new CounterView.OnValueChangedListener() {
                @Override
                public void onChange(int value) {
                    goods.buyNum = value;
                }
            });
        }

    }

    /**
     * 获得单价
     * @return
     */
    public String getPerPrice(){
        return viewHolder.price.getText().toString();
    }

    /**
     * 获得数量
     * @return
     */
    public int getQuantity(){
        return viewHolder.counterView.getNum();
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


    public void insert(SKUListData.DataBean.ItemsBean goods, int position) {
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
            super.onItemMove(fromPosition, toPosition);
        }

    }

    @Override
    public void onItemDismiss(int position) {
        if (position > 0) {
            remove(position);
            super.onItemDismiss(position);
        }

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
        @BindView(R.id.counterView)
        CounterView counterView;
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

    public SKUListData.DataBean.ItemsBean getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position >= 0 && position < list.size())
            return list.get(position);
        else return null;
    }

}
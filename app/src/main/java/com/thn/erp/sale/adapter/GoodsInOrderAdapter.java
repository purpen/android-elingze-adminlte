package com.thn.erp.sale.adapter;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.sale.bean.OrderData;
import com.thn.erp.utils.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单商品列表适配器
 */
public class GoodsInOrderAdapter extends BaseUltimateViewAdapter<OrderData.DataBean.OrdersBean.ItemsBean> {


    public GoodsInOrderAdapter(List<OrderData.DataBean.OrdersBean.ItemsBean> list) {
        super(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.layout_goods_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position) {
        OrderData.DataBean.OrdersBean.ItemsBean goods = list.get(position);
        ViewHolder viewHolder = (ViewHolder) ultimateRecyclerviewViewHolder;
        viewHolder.goodsName.setText(goods.product_name);
        GlideUtil.loadImage(goods.cover,viewHolder.ivCover);
        viewHolder.tvNum.setText("编号："+goods.rid);
        viewHolder.price.setText("￥"+goods.deal_price);
        viewHolder.stockNum.setText("数量："+goods.quantity);
    }

    class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.itemview)
        RelativeLayout itemView;
        @BindView(R.id.ivCover)
        ImageView ivCover;
        @BindView(R.id.goodsName)
        TextView goodsName;
        @BindView(R.id.tvNum)
        TextView tvNum;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.stockNum)
        TextView stockNum;

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
}
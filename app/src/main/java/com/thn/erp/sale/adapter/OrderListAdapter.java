package com.thn.erp.sale.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.sale.bean.OrderData;
import com.thn.erp.utils.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单列表适配器
 */
public class OrderListAdapter extends BaseUltimateViewAdapter<OrderData.DataBean.OrdersBean> {
    private Activity activity;
    public OrderListAdapter(Activity activity,List list) {
        super(list);
        this.activity = activity;
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position) {
        OrderData.DataBean.OrdersBean ordersBean = list.get(position);
        ViewHolder viewHolder = (ViewHolder) ultimateRecyclerviewViewHolder;
        viewHolder.tvOrderStatus.setText(""+ordersBean.status);
        viewHolder.tvTime.setText(DateUtil.getDateByTimestamp(ordersBean.created_at));
        viewHolder.tvOrderNum.setText("订单号："+ordersBean.rid);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_goods_adapter, null);
        GoodsItemHolder itemHolder = new GoodsItemHolder(view);
        viewHolder.llGoods.removeAllViews();
        for (OrderData.DataBean.OrdersBean.ItemsBean item:ordersBean.items){
            viewHolder.llGoods.addView(view);
            itemHolder.goodsName.setText(item.product_name);
            THNGlideUtil.displayImage(item.cover,itemHolder.ivCover,R.mipmap.ic_launcher);
            itemHolder.tvNum.setText("编号："+item.rid);
            itemHolder.price.setText("￥"+item.sale_price);
            itemHolder.stockNum.setText("数量："+item.quantity);
        }
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_adapter, parent, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.tvOrderStatus)
        TextView tvOrderStatus;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvOrderNum)
        TextView tvOrderNum;
        @BindView(R.id.llGoods)
        LinearLayout llGoods;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 订单包含的商品项
     */
    class GoodsItemHolder {
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
        public GoodsItemHolder(View itemView) {
            ButterKnife.bind(this,itemView);
        }
    }
}
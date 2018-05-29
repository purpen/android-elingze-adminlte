package com.thn.erp.more.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.more.bean.PrepareExportStockBean;

import java.util.List;

/**
 * Created by Stephen on 2018/4/17 10:50
 * Email: 895745843@qq.com
 */

public class PrepareExportStockAdapter extends BaseUltimateViewAdapter {
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public PrepareExportStockAdapter(List list) {
        super(list);
    }

    public PrepareExportStockAdapter(Context context, List list) {
        super(list);
        mContext = context;
    }

    public PrepareExportStockAdapter(Context context, List list, View.OnClickListener onClickListener) {
        super(list);
        mContext = context;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position) {
        ViewHoler viewHoler = (ViewHoler) ultimateRecyclerviewViewHolder;
        PrepareExportStockBean bean = (PrepareExportStockBean) list.get(position);
        viewHoler.textViewStockOrderId.setText(bean.getOrderId());
        viewHoler.textViewStockOrderCusomerName.setText(bean.getCustomerName());
        viewHoler.textViewStockOrderAmount.setText(bean.getOrderAmount());
        viewHoler.textViewStockOrderProduct.setText(bean.getOrderProduct());
        viewHoler.textViewStockOrderTime.setText(bean.getOrderingTime());
        viewHoler.textViewStockOrderExport.setOnClickListener(mOnClickListener);
        viewHoler.textViewStockOrderExport.setTag(R.id.textView_stock_order_export, bean);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        return new ViewHoler(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_more_stock_export, null));
    }

    class ViewHoler extends UltimateRecyclerviewViewHolder{
        private TextView textViewStockOrderId;
        private TextView textViewStockOrderCusomerName;
        private TextView textViewStockOrderAmount;
        private TextView textViewStockOrderProduct;
        private TextView textViewStockOrderTime;
        private TextView textViewStockOrderExport;

        public ViewHoler(View itemView) {
            super(itemView);
            textViewStockOrderId = (TextView) itemView.findViewById(R.id.textView_stock_order_id);
            textViewStockOrderCusomerName = (TextView) itemView.findViewById(R.id.textView_stock_order_cusomer_name);
            textViewStockOrderAmount = (TextView) itemView.findViewById(R.id.textView_stock_order_amount);
            textViewStockOrderProduct = (TextView) itemView.findViewById(R.id.textView_stock_order_product);
            textViewStockOrderTime = (TextView) itemView.findViewById(R.id.textView_stock_order_time);
            textViewStockOrderExport = (TextView) itemView.findViewById(R.id.textView_stock_order_export);
        }
    }
}

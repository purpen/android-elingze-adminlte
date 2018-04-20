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
import com.thn.erp.utils.ToastUtils;

import java.util.List;

/**
 * Created by Stephen on 2018/4/17 10:50
 * Email: 895745843@qq.com
 */

public class PrepareOrderingAdapter extends BaseUltimateViewAdapter {
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public PrepareOrderingAdapter(Context context, List list) {
        super(list);
        mContext = context;
    }

    public PrepareOrderingAdapter(List list) {
        super(list);
    }

    public PrepareOrderingAdapter(Context context, List list, View.OnClickListener onClickListener) {
        super(list);
        mContext = context;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position) {
        ViewHoler viewHoler = (ViewHoler) ultimateRecyclerviewViewHolder;
        PrepareExportStockBean bean = (PrepareExportStockBean) list.get(position);
        viewHoler.textViewStockOrderCusomerName.setText(bean.getCustomerName());
        viewHoler.textViewStockOrderAmount.setText(bean.getOrderAmount());
        viewHoler.textViewStockOrderProduct.setText(bean.getOrderProduct());
        viewHoler.textViewStockOrderExport.setOnClickListener(mOnClickListener);
        viewHoler.textViewStockOrderExport.setTag(R.id.textView_stock_order_export, bean);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        return new ViewHoler(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_more_stock_customer_list, null));
    }

    class ViewHoler extends UltimateRecyclerviewViewHolder{
        private TextView textViewStockOrderCusomerName;
        private TextView textViewStockOrderAmount;
        private TextView textViewStockOrderProduct;
        private TextView textViewStockOrderExport;

        public ViewHoler(View itemView) {
            super(itemView);
            textViewStockOrderCusomerName = (TextView)itemView. findViewById(R.id.textView_stock_order_cusomer_name);
            textViewStockOrderAmount = (TextView) itemView.findViewById(R.id.textView_stock_order_amount);
            textViewStockOrderProduct = (TextView) itemView.findViewById(R.id.textView_stock_order_product);
            textViewStockOrderExport = (TextView)itemView. findViewById(R.id.textView_stock_order_export);
        }
    }
}

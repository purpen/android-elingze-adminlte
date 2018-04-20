package com.thn.erp.more.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.more.bean.PrepareExportStockBean;

import java.util.List;

/**
 * Created by Stephen on 2018/4/18 16:16
 * Email: 895745843@qq.com
 */

public class StockProductListAdapter extends BaseUltimateViewAdapter {

    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public StockProductListAdapter(List list) {
        super(list);
    }

    public StockProductListAdapter(Context context, List list) {
        super(list);
        mContext = context;
    }

    public StockProductListAdapter(Context context, List list, View.OnClickListener onClickListener) {
        super(list);
        mContext = context;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position) {
//        StockProductListAdapter.ViewHoler viewHoler = (StockProductListAdapter.ViewHoler) ultimateRecyclerviewViewHolder;
//        PrepareExportStockBean bean = (PrepareExportStockBean) list.get(position);
////        viewHoler.imageView.setText(bean.getOrderId());
//        viewHoler.textView1.setText(bean.getCustomerName());
//        viewHoler.textView2.setText(bean.getOrderAmount());
//        viewHoler.textView3.setText(bean.getOrderProduct());
//        viewHoler.textView4.setText(bean.getOrderingTime());
//        viewHoler.editText1.setOnClickListener(mOnClickListener);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        initContext(parent);
        return new StockProductListAdapter.ViewHoler(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_more_stock_product_list, null));
    }

    class ViewHoler extends UltimateRecyclerviewViewHolder{
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private EditText editText1;

        public ViewHoler(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView. findViewById(R.id.imageView);
            textView1 = (TextView) itemView.findViewById(R.id.textView1);
            textView2 = (TextView) itemView.findViewById(R.id.textView2);
            textView3 = (TextView) itemView.findViewById(R.id.textView3);
            textView4 = (TextView) itemView.findViewById(R.id.textView4);
            editText1 = (EditText) itemView.findViewById(R.id.editText1);
        }
    }

    private void initContext(ViewGroup parent){
        if (mContext != null) {
            return;
        }
        mContext = parent.getContext();
    }
}

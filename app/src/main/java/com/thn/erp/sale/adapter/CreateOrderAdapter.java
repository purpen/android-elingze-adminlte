package com.thn.erp.sale.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.sale.bean.SKUListData;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateOrderAdapter extends BaseUltimateViewAdapter<SKUListData.DataBean.ItemsBean> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onClick(View view, int i);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private List<SKUListData.DataBean.ItemsBean> list;

    public CreateOrderAdapter(List<SKUListData.DataBean.ItemsBean> list) {
        super(list);
        this.list = list;
    }


    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_goods_adapter, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, final int position) {
        SKUListData.DataBean.ItemsBean goods = list.get(position);
        ViewHolder viewHolder = ((ViewHolder) ultimateRecyclerviewViewHolder);
        viewHolder.goodsName.setText(goods.product_name);
        THNGlideUtil.displayImage(goods.cover,viewHolder.ivCover,R.mipmap.ic_launcher);
        viewHolder.tvNum.setText("编号："+goods.rid);
        viewHolder.price.setText("￥"+goods.sale_price);
        viewHolder.stockNum.setText("数量："+goods.buyNum);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onClick(view,position);
                }
            }
        });
    }
    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_goods_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    static class ViewHolder extends UltimateRecyclerviewViewHolder {
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

    }

}
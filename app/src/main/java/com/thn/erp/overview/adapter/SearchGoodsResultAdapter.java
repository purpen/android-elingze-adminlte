package com.thn.erp.overview.adapter;
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
import com.thn.erp.overview.bean.SearchResultData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜索商品结果列表
 */
public class SearchGoodsResultAdapter extends BaseUltimateViewAdapter<SearchResultData.DataBean.ProductsBean> {
    public SearchGoodsResultAdapter(List<SearchResultData.DataBean.ProductsBean> list) {
        super(list);
        this.list = list;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_goods_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position) {
        SearchResultData.DataBean.ProductsBean goods = list.get(position);
        ViewHolder viewHolder = ((ViewHolder) ultimateRecyclerviewViewHolder);
        viewHolder.goodsName.setText(goods.name);
        THNGlideUtil.displayImage(goods.cover,viewHolder.ivCover,R.mipmap.default_load);
        viewHolder.tvNum.setText("编号："+goods.rid);
        viewHolder.price.setText("￥"+goods.sale_price);
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
    }

}
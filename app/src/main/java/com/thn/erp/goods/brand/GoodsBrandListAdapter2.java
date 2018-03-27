package com.thn.erp.goods.brand;

import android.graphics.Color;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/26 18:03
 * Email: 895745843@qq.com
 */

public class GoodsBrandListAdapter2<T> extends BaseUltimateViewAdapter {

    public GoodsBrandListAdapter2(List<T> list) {
        super(list);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder viewHolder2, int position) {
        GoodsBrandData.DataEntity.BrandsEntity goods = (GoodsBrandData.DataEntity.BrandsEntity) list.get(position);
        GoodsBrandListAdapter2.ViewHolder viewHolder = ((GoodsBrandListAdapter2.ViewHolder) viewHolder2);
        viewHolder.goodsName.setText(goods.getName());
        THNGlideUtil.displayImage(goods.getLogo(),viewHolder.ivCover, R.mipmap.ic_launcher);
        viewHolder.tvNum.setText("编号："+goods.getRid());
        viewHolder.price.setText(goods.getFeatures());
        viewHolder.stockNum.setText("");
        viewHolder.stockNum.setVisibility(View.GONE);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.item_recyclerview_goods_brand, parent, false);
        return new ViewHolder(v);
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

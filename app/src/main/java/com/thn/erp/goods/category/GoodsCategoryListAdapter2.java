package com.thn.erp.goods.category;

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
 * Created by Stephen on 2018/3/26 21:17
 * Email: 895745843@qq.com
 */

public class GoodsCategoryListAdapter2 extends BaseUltimateViewAdapter {

    public GoodsCategoryListAdapter2(List list) {
        super(list);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position) {
        GoodsCategoryData.DataEntity.CategoriesEntity goods = (GoodsCategoryData.DataEntity.CategoriesEntity) list.get(position);
        GoodsCategoryListAdapter2.ViewHolder viewHolder = ((GoodsCategoryListAdapter2.ViewHolder) ultimateRecyclerviewViewHolder);
        viewHolder.goodsName.setText(goods.getName());
        THNGlideUtil.displayImage(goods.getCover(),viewHolder.ivCover, R.mipmap.ic_launcher);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.item_recyclerview_goods_category, parent, false);
        return new GoodsCategoryListAdapter2.ViewHolder(v);
    }

    class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.itemview)
        RelativeLayout itemView;
        @BindView(R.id.ivCover)
        ImageView ivCover;
        @BindView(R.id.goodsName)
        TextView goodsName;

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

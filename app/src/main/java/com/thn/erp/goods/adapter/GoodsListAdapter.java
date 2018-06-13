package com.thn.erp.goods.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.basemodule.tools.GlideUtil;
import com.thn.erp.R;
import com.thn.erp.sale.bean.GoodsData;

/**
 * Created by Stephen on 2018/3/26 22:17
 * Email: 895745843@qq.com
 */

public class GoodsListAdapter extends BaseQuickAdapter<GoodsData.DataBean.ProductsBean, BaseViewHolder> {

    public GoodsListAdapter(@LayoutRes int layoutId) {
        super(layoutId);
    }


    @Override
    protected void convert(BaseViewHolder helper, GoodsData.DataBean.ProductsBean item) {
        helper.setText(R.id.goodsName, item.name);
        helper.setText(R.id.tvNum, "编号："+item.rid);
        helper.setText(R.id.price,"￥"+item.sale_price);
        View view = helper.getView(R.id.ivCover);
        GlideUtil.loadImageWithDimen(item.cover,(ImageView) view, view.getContext().getResources().getDimensionPixelSize(R.dimen.dp90));
    }
}

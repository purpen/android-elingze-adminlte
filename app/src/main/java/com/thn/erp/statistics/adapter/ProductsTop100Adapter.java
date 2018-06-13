package com.thn.erp.statistics.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.erp.R;
import com.thn.erp.bean.SaleTop100Bean;

/**
 * Created by lilin on 2017/7/6.
 */

public class ProductsTop100Adapter extends BaseQuickAdapter<SaleTop100Bean.DataBean.SaleLogStatisticsBean,BaseViewHolder> {

    public ProductsTop100Adapter(@LayoutRes int resId) {
        super(resId);
    }


    @Override
    protected void convert(BaseViewHolder helper, SaleTop100Bean.DataBean.SaleLogStatisticsBean item) {
        helper.setText(R.id.order,String.valueOf(helper.getAdapterPosition()+1));
        helper.setText(R.id.productsName, item.name);
        helper.setText(R.id.saleNum, item.quantity);
        helper.setText(R.id.saleAmount, item.sale_amount);
        helper.setText(R.id.percentage, String.format("%s%%",item.proportion));
    }
}

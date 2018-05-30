package com.thn.erp.statistics.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.base.CommonBaseAdapter;
import com.thn.erp.bean.SaleTop100Bean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lilin on 2017/7/6.
 */

public class ProductsTop100Adapter extends CommonBaseAdapter<SaleTop100Bean.DataBean.SaleLogStatisticsBean> {

    public ProductsTop100Adapter(List<SaleTop100Bean.DataBean.SaleLogStatisticsBean> list, Activity activity) {
        super(list, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SaleTop100Bean.DataBean.SaleLogStatisticsBean item=list.get(position);
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(activity, R.layout.item_sale_top100, null);
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, activity.getResources().getDimensionPixelSize(R.dimen.dp30)));
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.order.setText(String.valueOf(position+1));
//        holder.id.setText(item.sku_id);
        holder.productsName.setText(item.name);
        holder.saleNum.setText(item.quantity);
        holder.saleAmount.setText(item.sale_amount);
        holder.percentage.setText(String.format("%s%%",item.proportion));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.order)
        TextView order;
        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.productsName)
        TextView productsName;
        @BindView(R.id.saleNum)
        TextView saleNum;
        @BindView(R.id.saleAmount)
        TextView saleAmount;
        @BindView(R.id.percentage)
        TextView percentage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

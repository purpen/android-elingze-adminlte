package com.thn.erp.sale.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.sale.bean.SKUListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/9/27 9:37
 */

public class SKUAdapter extends BaseUltimateViewAdapter<SKUListData.DataBean.ItemsBean> {
    private Activity activity;
    public SKUAdapter(Activity activity, List<SKUListData.DataBean.ItemsBean> list) {
        super(list);
        this.activity = activity;
        this.list = list;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sku, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position) {
        SKUListData.DataBean.ItemsBean sku = list.get(position);
        ViewHolder viewHolder = (ViewHolder) ultimateRecyclerviewViewHolder;
        viewHolder.tvSku.setText(sku.s_color);
    }

    static class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.tvSku)
        TextView tvSku;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

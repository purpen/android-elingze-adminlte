package com.thn.erp.sale.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.erp.AppApplication;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.sale.bean.SKUListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lilin
 * created at 2016/9/27 9:37
 */

public class SpecificationAdapter extends BaseUltimateViewAdapter<SKUListData.DataBean.ModesBean> {
    private Activity activity;
    private SparseArray<TextView> mViews;

    public SpecificationAdapter(Activity activity, List<SKUListData.DataBean.ModesBean> list) {
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
        SKUListData.DataBean.ModesBean modesBean = list.get(position);
        ViewHolder viewHolder = (ViewHolder) ultimateRecyclerviewViewHolder;
        if (modesBean.valid){
            viewHolder.tvSku.setEnabled(true);
            if (modesBean.selected){
                viewHolder.tvSku.setBackgroundResource(R.drawable.corner_bg_27ae59);
                viewHolder.tvSku.setTextColor(AppApplication.getContext().getResources().getColor(android.R.color.white));
            }else {
                viewHolder.tvSku.setTextColor(activity.getResources().getColor(R.color.color_27AE59));
                viewHolder.tvSku.setBackgroundResource(R.drawable.corner_border_27ae59);
            }
        }else {
            viewHolder.tvSku.setEnabled(false);
            viewHolder.tvSku.setTextColor(activity.getResources().getColor(R.color.color_ddd));
            viewHolder.tvSku.setBackgroundResource(R.drawable.corner_bg_eee);
        }
        viewHolder.tvSku.setText(modesBean.name);
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
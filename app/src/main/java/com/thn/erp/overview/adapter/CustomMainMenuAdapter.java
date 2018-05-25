package com.thn.erp.overview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.basemodule.tools.GlideUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.overview.bean.CustomMenuBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomMainMenuAdapter extends BaseUltimateViewAdapter<CustomMenuBean> {
    public CustomMainMenuAdapter(List<CustomMenuBean> list) {
        super(list);

    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_class, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, final int position) {
        CustomMenuBean customMenuBean = list.get(position);
        ViewHolder viewHolder = ((ViewHolder) ultimateRecyclerviewViewHolder);
        viewHolder.name.setText(customMenuBean.title);
        viewHolder.checkBox.setChecked(customMenuBean.selected);
    }

    static class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
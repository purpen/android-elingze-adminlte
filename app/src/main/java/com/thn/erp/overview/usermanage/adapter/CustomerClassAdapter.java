package com.thn.erp.overview.usermanage.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.overview.usermanage.bean.CustomerClassData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 客户等级
 */
public class CustomerClassAdapter extends BaseUltimateViewAdapter<CustomerClassData.DataBean.GradesBean> {

    public CustomerClassAdapter(List<CustomerClassData.DataBean.GradesBean> list) {
        super(list);
    }


    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.item_customer_class, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, final int position) {
        CustomerClassData.DataBean.GradesBean grade = list.get(position);
        ViewHolder viewHolder = ((ViewHolder) ultimateRecyclerviewViewHolder);
        viewHolder.name.setText(grade.name);
        viewHolder.checkBox.setChecked(grade.checked);
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
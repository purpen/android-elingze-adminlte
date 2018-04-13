package com.thn.erp.overview.usermanage.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.overview.usermanage.bean.CustomerData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomerListAdapter extends BaseUltimateViewAdapter<CustomerData.DataBean.CustomersBean> {
    private List<CustomerData.DataBean.CustomersBean> list;

    public CustomerListAdapter(List<CustomerData.DataBean.CustomersBean> list) {
        super(list);
        this.list = list;
    }


    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.layout_customer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, final int position) {
        CustomerData.DataBean.CustomersBean customerBean = list.get(position);
        ViewHolder viewHolder = ((ViewHolder) ultimateRecyclerviewViewHolder);
        viewHolder.company.setText(customerBean.name);
        viewHolder.name.setText(customerBean.name);
        viewHolder.classify.setText(customerBean.grade);
    }

    static class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.classify)
        TextView classify;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
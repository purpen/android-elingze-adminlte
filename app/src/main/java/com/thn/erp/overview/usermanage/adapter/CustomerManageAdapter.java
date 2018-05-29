package com.thn.erp.overview.usermanage.adapter;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.AddCustomActivity;
import com.thn.erp.overview.usermanage.bean.CustomerData;
import com.thn.erp.overview.usermanage.bean.CustomerDeleteData;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.erp.view.dialog.DefaultDialog;
import com.thn.erp.view.dialog.IDialogListenerConfirmBack;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomerManageAdapter extends BaseUltimateViewAdapter<CustomerData.DataBean.CustomersBean> {
    private List<CustomerData.DataBean.CustomersBean> list;
    private Activity activity;
    public CustomerManageAdapter(Activity activity,List<CustomerData.DataBean.CustomersBean> list) {
        super(list);
        this.activity = activity;
        this.list = list;
    }


    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.layout_manage_customer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, final int position) {
        final CustomerData.DataBean.CustomersBean customerBean = list.get(position);
        ViewHolder viewHolder = ((ViewHolder) ultimateRecyclerviewViewHolder);
        viewHolder.company.setText(customerBean.name);
        viewHolder.name.setText(customerBean.name);
        viewHolder.classify.setText(customerBean.grade);
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCustomActivity.class);
                intent.putExtra(AddCustomActivity.class.getSimpleName(), customerBean);
                v.getContext().startActivity(intent);
            }
        });

        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Resources resources = v.getContext().getResources();
                final WaitingDialog dialog = new WaitingDialog(activity);
                new DefaultDialog(v.getContext(), resources.getString(R.string.hint_dialog_delete_customer_title), resources.getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {

                    @Override
                    public void clickRight() {
                        HashMap<String, String> params = ClientParamsAPI.getDefaultParams();
                        String url = URL.BASE_URL + "customers/" + customerBean.rid;
                        HttpRequest.sendRequest(HttpRequest.DELETE, url, params,new HttpRequestCallback() {
                            @Override
                            public void onStart() {
                                if (dialog != null) dialog.show();
                            }

                            @Override
                            public void onSuccess(String json) {
                                dialog.dismiss();
                                CustomerDeleteData data = JsonUtil.fromJson(json, CustomerDeleteData.class);
                                if (data.success) {
                                    CustomerManageAdapter.this.remove(position);
                                    ToastUtil.showSuccess("删除成功");
                                } else {
                                    ToastUtil.showError(data.status.message);
                                }
                            }

                            @Override
                            public void onFailure(IOException e) {
                                dialog.dismiss();
                                ToastUtil.showError(R.string.network_err);
                            }
                        });
                    }
                });
            }
        });

    }

    static class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.classify)
        TextView classify;
        @BindView(R.id.tvEdit)
        TextView tvEdit;
        @BindView(R.id.tvDelete)
        TextView tvDelete;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
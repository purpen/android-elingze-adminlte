package com.thn.erp.overview.usermanage.adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.stephen.taihuoniaolibrary.utils.THNToastUtil;
import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.AddGradeActivity;
import com.thn.erp.overview.usermanage.bean.CustomerClassData;
import com.thn.erp.overview.usermanage.bean.DeleteGradeData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.dialog.DefaultDialog;
import com.thn.erp.view.dialog.IDialogListenerConfirmBack;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 客户等级
 */
public class ManageCustomerClassAdapter extends BaseUltimateViewAdapter<CustomerClassData.DataBean.GradesBean> {
    public ManageCustomerClassAdapter(List<CustomerClassData.DataBean.GradesBean> list) {
        super(list);
    }


    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.item_manage_customer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, final int position) {
        final CustomerClassData.DataBean.GradesBean grade = list.get(position);
        ViewHolder viewHolder = ((ViewHolder) ultimateRecyclerviewViewHolder);
        viewHolder.name.setText(grade.name);
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddGradeActivity.class);
                intent.putExtra(AddGradeActivity.class.getSimpleName(), grade);
                v.getContext().startActivity(intent);
            }
        });

        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = v.getContext().getResources();
                final THNWaittingDialog dialog = new THNWaittingDialog(v.getContext());
                new DefaultDialog(v.getContext(), resources.getString(R.string.hint_dialog_delete_grade_title), resources.getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {

                    @Override
                    public void clickRight() {
                        HashMap<String, String> params = ClientParamsAPI.getDefaultParams();
                        String url = URL.BASE_URL + "customer_grades/" + grade.rid;
                        HttpRequest.sendRequest(HttpRequest.DELETE, url, params,new HttpRequestCallback() {
                            @Override
                            public void onStart() {
                                if (dialog != null) dialog.show();
                            }

                            @Override
                            public void onSuccess(String json) {
                                dialog.dismiss();
                                DeleteGradeData data = JsonUtil.fromJson(json, DeleteGradeData.class);
                                if (data.success) {
                                    ManageCustomerClassAdapter.this.remove(position);
                                    THNToastUtil.showSuccess("删除成功");
                                } else {
                                    ToastUtils.showError(data.status.message);
                                }
                            }

                            @Override
                            public void onFailure(IOException e) {
                                dialog.dismiss();
                                ToastUtils.showError(R.string.network_err);
                            }
                        });
                    }
                });
            }
        });
    }

    static class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.tvDelete)
        TextView tvDelete;
        @BindView(R.id.tvEdit)
        TextView tvEdit;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
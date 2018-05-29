package com.thn.erp.overview.usermanage;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.adapter.ManageCustomerClassAdapter;
import com.thn.erp.overview.usermanage.bean.CustomerClassData;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.erp.view.CustomHeadView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 管理客户分类
 */

public class ManageCustomerClassActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ManageCustomerClassAdapter adapter;
    private List<CustomerClassData.DataBean.GradesBean> list;
    private int page=1;
    private boolean isRefreshing =false;
    private WaitingDialog dialog;
    private boolean isLoadMore=false;

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_customer_class;
    }

    @Override
    protected void initView() {
        dialog =new WaitingDialog(this);
        customHeadView.setHeadCenterTxtShow(true, R.string.manage_class_title);
        list = new ArrayList<>();
        adapter = new ManageCustomerClassAdapter(this,list);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.custom_bottom_progressbar, null));

        ultimateRecyclerView.setEmptyView(
                R.layout.empty_view,
                UltimateRecyclerView.EMPTY_CLEAR_ALL,
                UltimateRecyclerView.STARTWITH_ONLINE_ITEMS);
        ultimateRecyclerView.reenableLoadmore();
        ultimateRecyclerView.setAdapter(adapter);
        ultimateRecyclerView.addItemDividerDecoration(activity);
    }

    @Override
    protected void installListener() {
        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getCustomerGrades();
            }
        });


        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isRefreshing = false;
                isLoadMore = true;
                page++;
                getCustomerGrades();
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                int size = list.size();
                if (size==0) return;
                CustomerClassData.DataBean.GradesBean gradesBean;
                for (int j=0;j<size;j++){
                    gradesBean = list.get(j);
                    if (j==i){
                        gradesBean.checked=true;
                    }else {
                        gradesBean.checked=false;
                    }
                }
                adapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra(ManageCustomerClassActivity.class.getSimpleName(),list.get(i));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void requestNet() {
        getCustomerGrades();
    }

    private void getCustomerGrades() {
        HashMap<String, String> params = ClientParamsAPI.getCustomerGradeParams(page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.CUSTOMER_GRADE_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                if (!isRefreshing || !isLoadMore) dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                CustomerClassData customerClassData = JsonUtil.fromJson(json, CustomerClassData.class);
                if (customerClassData.success == true) {
                    List<CustomerClassData.DataBean.GradesBean> gradesBeans = customerClassData.data.grades;
                    if (gradesBeans.size()==0) ultimateRecyclerView.disableLoadmore();
                    updateData(gradesBeans);
                } else {
                    ToastUtil.showError(customerClassData.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<CustomerClassData.DataBean.GradesBean> gradesBeans) {
        if (isRefreshing) {
            adapter.clear();
            for (CustomerClassData.DataBean.GradesBean gradesBean : gradesBeans) {
                adapter.insert(gradesBean, 0);
            }
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
        } else {
            for (CustomerClassData.DataBean.GradesBean gradesBean : gradesBeans) {
                adapter.insert(gradesBean, adapter.getAdapterItemCount());
            }
        }
        if (adapter.getAdapterItemCount()==0) ultimateRecyclerView.showEmptyView();
    }

    @OnClick(R.id.addClassBtn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addClassBtn:
                startActivity(new Intent(this,AddGradeActivity.class));
                break;
            default:
                break;
        }
    }

}

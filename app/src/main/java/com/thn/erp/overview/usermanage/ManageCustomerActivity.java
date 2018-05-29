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
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.adapter.CustomerManageAdapter;
import com.thn.erp.overview.usermanage.bean.CustomerData;
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

public class ManageCustomerActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomerManageAdapter adapter;
    private List<CustomerData.DataBean.CustomersBean> list;
    private int page=1;
    private boolean isRefreshing =false;
    private WaitingDialog dialog;
    private boolean isLoadMore=false;
    private boolean isFirstCreateActivity =true;

    @Override
    protected int getLayout() {
        return R.layout.activity_manage_customer;
    }

    @Override
    protected void initView() {
        dialog =new WaitingDialog(this);
        customHeadView.setHeadCenterTxtShow(true, R.string.manage_customer_title);
        list = new ArrayList<>();
        adapter = new CustomerManageAdapter(this,list);
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
    protected void onResume() {
        super.onResume();
        if (isFirstCreateActivity) return;
        page = 1;
        isRefreshing = true;
        getCustomers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isFirstCreateActivity = false;
    }

    @Override
    protected void installListener() {
        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getCustomers();
            }
        });


        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isRefreshing = false;
                isLoadMore = true;
                page++;
                getCustomers();
            }
        });

    }

    @Override
    protected void requestNet() {
        getCustomers();
    }

    private void getCustomers() {
        HashMap<String, String> params = ClientParamsAPI.getCustomerList(page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.CUSTOMER_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                if (!isRefreshing && !isLoadMore) dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                CustomerData customerBean = JsonUtil.fromJson(json, CustomerData.class);
                if (customerBean.success == true) {
                    List<CustomerData.DataBean.CustomersBean> customers = customerBean.data.customers;
                    if (customers.size() == 0) ultimateRecyclerView.disableLoadmore();
                    updateData(customers);
                } else {
                    ToastUtil.showError(customerBean.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<CustomerData.DataBean.CustomersBean> customers) {
        if (isRefreshing) {
            adapter.clear();
            for (CustomerData.DataBean.CustomersBean customer : customers) {
                adapter.insert(customer, 0);
            }
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            for (CustomerData.DataBean.CustomersBean customer : customers) {
                adapter.insert(customer, adapter.getAdapterItemCount());
            }
        }
        if (adapter.getAdapterItemCount() == 0) ultimateRecyclerView.showEmptyView();
    }



    @OnClick(R.id.addBtn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addBtn:
                startActivity(new Intent(this,AddCustomActivity.class));
                break;
            default:
                break;
        }
    }

}

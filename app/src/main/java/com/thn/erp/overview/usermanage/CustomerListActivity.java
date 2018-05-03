package com.thn.erp.overview.usermanage;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.adapter.CustomerListAdapter;
import com.thn.erp.overview.usermanage.bean.CustomerData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.SearchView;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 用户管理
 */

public class CustomerListActivity extends BaseActivity {
    public static final int REQUEST_EDIT_CUSTOMER = 0x00010;
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.addBtn)
    Button addBtn;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;

    private WaitingDialog dialog;
    private int page;
    private List<CustomerData.DataBean.CustomersBean> list;
    private Boolean isRefreshing = false;
    private CustomerListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    //    是否是第一次创建
    private boolean isFirstCreateActivity = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_select_customer;
    }

    @Override
    protected void initView() {
        page = 1;
        dialog = new WaitingDialog(activity);
        addBtn.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        customHeadView.setHeadCenterTxtShow(true, R.string.user_manage_title);
        customHeadView.setHeadRightTxtShow(true, R.string.manage_customer);
        adapter = new CustomerListAdapter(list);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.custom_bottom_progressbar, null));
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.WHITE);
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
        // 实现TextWatcher监听即可
        searchView.setOnSearchClickListener(new SearchView.OnSearchClickListener() {
            @Override
            public void onSearchClick(String s) {
                ToastUtils.showInfo("going search");
            }
        });
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity,ManageCustomerActivity.class));
            }
        });

        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getCustomers();
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                if (list.size() == 0) return;
                CustomerData.DataBean.CustomersBean customersBean = list.get(i);
                Intent intent = new Intent(activity, AddCustomActivity.class);
                intent.putExtra(AddCustomActivity.class.getSimpleName(), customersBean);
                startActivityForResult(intent, REQUEST_EDIT_CUSTOMER);
            }
        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isRefreshing = false;
                page++;
                getCustomers();
            }
        });
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
    protected void requestNet() {
        getCustomers();
    }

    private void getCustomers() {
        HashMap<String, String> params = ClientParamsAPI.getCustomerList(page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.CUSTOMER_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                if (!isRefreshing) dialog.show();
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
                    ToastUtils.showError(customerBean.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
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
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn: //添加客户
                startActivity(new Intent(this, AddCustomActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_EDIT_CUSTOMER:
                    CustomerData.DataBean.CustomersBean customersBean = data.getParcelableExtra(AddCustomActivity.class.getSimpleName());
//                    TODO
                    break;
            }
        }
    }
}

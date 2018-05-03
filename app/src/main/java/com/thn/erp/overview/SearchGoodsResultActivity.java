package com.thn.erp.overview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.stephen.taihuoniaolibrary.utils.THNLogUtil;
import com.stephen.taihuoniaolibrary.utils.THNToastUtil;
import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.adapter.SearchGoodsResultAdapter;
import com.thn.erp.overview.bean.SearchResultData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.view.CustomSearchHeadView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索结果页
 */
public class SearchGoodsResultActivity extends BaseActivity {

    @BindView(R.id.customSearchHeadView)
    CustomSearchHeadView customSearchHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    private String keyWords;
    private THNWaittingDialog dialog;
    private int page=1;
    private ArrayList<SearchResultData.DataBean.ProductsBean> list;
    private SearchGoodsResultAdapter adapter;
    private boolean isRefreshing;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void getIntentData() {
        keyWords=getIntent().getStringExtra(SearchGoodsResultActivity.class.getSimpleName());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_goods_result;
    }

    @Override
    protected void initView() {
        dialog =new THNWaittingDialog(this);
        list=new ArrayList<>();
        adapter = new SearchGoodsResultAdapter(list);

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
    protected void requestNet() {
        if (TextUtils.isEmpty(keyWords)){
            THNLogUtil.e("搜索关键为空");
            return;
        }
        getSearchResult();
    }

    /**
     * 获取搜索结果
     */
    private void getSearchResult() {
        HashMap<String, String> params = ClientParamsAPI.searchResultParam(page,keyWords);
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                SearchResultData data = JsonUtil.fromJson(json, SearchResultData.class);
                if (data.success) {
                    updateData(data.data.products);
                } else {
                    THNToastUtil.showError(data.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                THNToastUtil.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<SearchResultData.DataBean.ProductsBean> products) {
        if (isRefreshing) {
            adapter.clear();
            for (SearchResultData.DataBean.ProductsBean product : products) {
                adapter.insert(product, adapter.getAdapterItemCount());
            }
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            for (SearchResultData.DataBean.ProductsBean product : products) {
                adapter.insert(product, adapter.getAdapterItemCount());
            }
        }
        if (adapter.getAdapterItemCount() == 0) ultimateRecyclerView.showEmptyView();
    }

    @Override
    protected void installListener() {
        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getSearchResult();
            }
        });
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isRefreshing = false;
                page++;
                getSearchResult();
            }
        });

        customSearchHeadView.setOnLeftButtonClickListener(new CustomSearchHeadView.OnLeftButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        customSearchHeadView.setOnSearchClickListener(new CustomSearchHeadView.OnSearchClickListener() {
            @Override
            public void onSearch(String s) {
                if(TextUtils.isEmpty(s)){
                    THNToastUtil.showInfo("请输入关键字");
                }
            }
        });
    }
}

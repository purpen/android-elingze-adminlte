package com.thn.erp.goods.brand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.base.BaseUltimateRecyclerView;
import com.thn.erp.common.constant.ExtraKey;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.goods.GoodsListActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.SearchView;
import com.thn.erp.view.common.PublicTopBar;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/26 15:57
 * Email: 895745843@qq.com
 */

public class GoodsBrandActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.ultimateRecyclerView)
    BaseUltimateRecyclerView ultimateRecyclerView;
    private WaitingDialog dialog;
    private int page;
    private List<GoodsBrandData.DataEntity.BrandsEntity> list;
    private Boolean isRefreshing = false;
    private GoodsBrandListAdapter2<GoodsBrandData.DataEntity.BrandsEntity> adapter;
    private LinearLayoutManager linearLayoutManager;
    private String cid = "";

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_brand;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        initTopbar();
        initRecyclerView();
        dialog = new WaitingDialog(activity);
    }

    private void initRecyclerView() {
        page = 1;
        list = new ArrayList<>();
        adapter = new GoodsBrandListAdapter2<>(list);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(adapter);
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("品牌列表", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    protected void installListener() {
        // 实现TextWatcher监听即可
        searchView.setOnSearchClickListener(new SearchView.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                ToastUtils.showInfo("going search");
            }
        });

        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getGoodsList();
            }
        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                ultimateRecyclerView.disableLoadmore();
                isRefreshing = false;
                page++;
                getGoodsList();
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(){
            @Override
            public void onClick(View view, int i) {
                Intent intent = new Intent(GoodsBrandActivity.this, GoodsListActivity.class);
                intent.putExtra(ExtraKey.BRAND_ID,list.get(i).getRid());
                intent.putExtra(ExtraKey.BRAND_NAME,list.get(i).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void requestNet() {
        getGoodsList();
    }

    private void getGoodsList() {
        HashMap<String, String> params = ClientParamsAPI.getGoodsList(cid,page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.PRODUCT_BRAND, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
                GoodsBrandData customerBean = JsonUtil.fromJson(json, GoodsBrandData.class);
                if (customerBean.getSuccess()) {
                    updateData(customerBean.getData().getBrands());
                } else {
                    ToastUtils.showError(customerBean.getStatus().getMessage());
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<GoodsBrandData.DataEntity.BrandsEntity> goodses) {
        if (isRefreshing) {
//            for (GoodsBrandData.DataEntity.BrandsEntity goodsBrand : goodses) {
//                adapter.insert(goodsBrand, 0);
//            }
            adapter.setList(goodses);
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
//            for (GoodsBrandData.DataEntity.BrandsEntity goods : goodses) {
//                adapter.insert(goods, adapter.getAdapterItemCount());
//            }
            adapter.addList(goodses);
        }
        dialog.dismiss();
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }
}

package com.thn.erp.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.stephen.taihuoniaolibrary.utils.THNLogUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.base.BaseUltimateRecyclerView;
import com.thn.erp.common.constant.ExtraKey;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Stephen on 2018/4/4 16:46
 * Email: 895745843@qq.com
 */

public class GoodsListFragment extends BaseFragment {

    @BindView(R.id.ultimateRecyclerView)
    BaseUltimateRecyclerView ultimateRecyclerView;
    Unbinder unbinder;


    private List<GoodsData.DataBean.ProductsBean> list;
    private LinearLayoutManager linearLayoutManager;
    private Boolean isRefreshing = false;
    private GoodsListAdapter adapter;
    private WaitingDialog dialog;
    private int page;

    private String brandId;

    @Override
    protected int getLayout() {
        return R.layout.fragment_goods_list;
    }

    public static GoodsListFragment newInstance(String brandId) {
        Bundle args = new Bundle();
        args.putString(ExtraKey.BRAND_ID, brandId);
        GoodsListFragment fragment = new GoodsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brandId = getArguments().getString(ExtraKey.BRAND_ID, null);
    }

    @Override
    protected void initView() {
        dialog = new WaitingDialog(getActivity());
        initListAdapter();
        getGoodsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initListAdapter() {
        page = 1;
        dialog = new WaitingDialog(activity);
        list = new ArrayList<>();
        adapter = new GoodsListAdapter(list);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(adapter);

        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getGoodsList();
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                GoodsData.DataBean.ProductsBean productsBean = list.get(i);
                Intent intent = new Intent();
                intent.setClass(getActivity(), GoodsDetailsActivity.class);
                intent.putExtra("Extra",productsBean);
                getActivity().startActivity(intent);
            }
        } );

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                ultimateRecyclerView.disableLoadmore();
                isRefreshing = false;
                page++;
                getGoodsList();
            }
        });
    }

    private void getGoodsList() {
        HashMap<String, String> params = ClientParamsAPI.getGoodsList(brandId, page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
//                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                GoodsData customerBean = JsonUtil.fromJson(json, GoodsData.class);
                if (customerBean.success) {
                    updateData(customerBean.data.products);
                } else {
                    ToastUtils.showError(customerBean.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
//                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<GoodsData.DataBean.ProductsBean> goodses) {
        if (isRefreshing) {
            adapter.setList(goodses);
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            adapter.addList(goodses);
        }
        dialog.dismiss();
    }
}

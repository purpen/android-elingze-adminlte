package com.thn.erp.goods.category;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.base.BaseUltimateRecyclerView;
import com.thn.erp.common.constant.ExtraKey;
import com.thn.erp.common.constant.RequestCode;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.common.interfaces.OnRecyclerViewItemLongClickListener;
import com.thn.erp.goods.GoodsListActivity;
import com.thn.erp.goods.brand.entity.BrandResultBean;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.PopupWindowUtil;
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
 * Created by Stephen on 2018/3/26 17:35
 * Email: 895745843@qq.com
 */

public class GoodsCategoryActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.ultimateRecyclerView)
    BaseUltimateRecyclerView ultimateRecyclerView;
    private WaitingDialog dialog;
    private int page;
    private List<GoodsCategoryData.DataEntity.CategoriesEntity> list;
    private Boolean isRefreshing = false;
    private GoodsCategoryListAdapter2 adapter;
    private GridLayoutManager linearLayoutManager;
    private String cid = "";

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_category;
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
        adapter = new GoodsCategoryListAdapter2(list);
        linearLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(adapter);
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("分类列表", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarRightTextView("添加", Color.parseColor("#27AE59"));
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

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                Intent intent = new Intent(GoodsCategoryActivity.this, GoodsListActivity.class);
                intent.putExtra(ExtraKey.CATEGORY_ID,list.get(i).getId());
                intent.putExtra(ExtraKey.CATEGORY_NAME,list.get(i).getName());
                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
            @Override
            public void onLongClick(View view, int i) {

                // TODO: 2018/4/11 编辑和删除
                View layoutView = LayoutInflater.from(GoodsCategoryActivity.this).inflate(R.layout.popup_goods_category_edit, null);
                final GoodsCategoryData.DataEntity.CategoriesEntity categoriesEntity = list.get(i);
                textViewGoodsCategoryEditDelete = (TextView) layoutView.findViewById(R.id.textView_goods_category_edit_delete);
                textViewGoodsCategoryEditSave = (TextView) layoutView.findViewById(R.id.textView_goods_category_edit_modify);
                textViewGoodsCategoryEditDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delete(categoriesEntity.getId());
                    }
                });

                textViewGoodsCategoryEditSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupWindowUtil.dismiss();
                        Intent intent = new Intent(GoodsCategoryActivity.this, GoodsCategoryAddActivity.class);
                        intent.putExtra(ExtraKey.CATEGORY_BEAN, categoriesEntity);
                        startActivityForResult(intent, RequestCode.CODE_GOODS_CATEGORY_ADD);
                    }
                });
                PopupWindowUtil.show(GoodsCategoryActivity.this, layoutView);
            }
        });

    }

    @Override
    protected void requestNet() {
        getGoodsList();
    }

    private void getGoodsList() {
        HashMap<String, String> params = ClientParamsAPI.getGoodsList(cid,page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.PRODUCT_CATEGORY, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
                GoodsCategoryData customerBean = JsonUtil.fromJson(json, GoodsCategoryData.class);
                if (customerBean.getStatus().getCode() == 200) {
                    updateData(customerBean.getData().getCategories());
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

    private void updateData(List<GoodsCategoryData.DataEntity.CategoriesEntity> goodses) {
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

    @Override
    public void onTopBarClick(View view, int position) {
        switch (position) {
            case ImpTopbarOnClickListener.LEFT:
                this.finish();
                break;
            case ImpTopbarOnClickListener.CENTER:
                break;
            case ImpTopbarOnClickListener.RIGHT:
                Intent intent = new Intent(GoodsCategoryActivity.this, GoodsCategoryAddActivity.class);
                startActivityForResult(intent, RequestCode.CODE_GOODS_CATEGORY_ADD);
                break;
        }
    }

    private TextView textViewGoodsCategoryEditDelete;
    private TextView textViewGoodsCategoryEditSave;


    /**
     * 删除
     */
    private void delete(int categoryId) {
        HashMap<String, String> params = ClientParamsAPI.getDefaultParams();
        String url = URL.PRODUCT_CATEGORY + "/" + categoryId;
        HttpRequest.sendRequest(HttpRequest.DELETE, url, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
                BrandResultBean customerBean = JsonUtil.fromJson(json, BrandResultBean.class);
                if (customerBean.getSuccess()) {
                    ToastUtils.showSuccess(customerBean.getStatus().getMessage());
                    PopupWindowUtil.dismiss();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.CODE_GOODS_CATEGORY_ADD) {
            requestNet();
        }
    }
}

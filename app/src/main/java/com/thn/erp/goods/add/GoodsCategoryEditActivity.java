package com.thn.erp.goods.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.goods.category.GoodsCategoryData;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.common.PublicTopBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/29 16:43
 * Email: 895745843@qq.com
 */

public class GoodsCategoryEditActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;

    private RecyclerView recyclerView1;
    private RecyclerViewArrayAdapter arrayAdapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_goods_add_category;
    }

    @Override
    protected void initView() {
        initTopbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);
//        String[] stringArray = getResources().getStringArray(R.array.GoodsCategory);
//        final List<String> strings = Arrays.asList(stringArray);
        arrayAdapter = new RecyclerViewArrayAdapter( null, new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                Intent intent = new Intent();
                intent.putExtra(GoodsAddActivity.class.getSimpleName(), arrayAdapter.getItem(i));
                setResult(-1, intent);
                GoodsCategoryEditActivity.this.finish();
            }
        });
        recyclerView1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView1.setAdapter(arrayAdapter);
    }

    private void initTopbar() {
        publicTopBar.setTopBarCenterTextView("商品分类", getResources().getColor(R.color.white));
//        publicTopBar.setTopBarRightTextView("保存", getResources().getColor(R.color.THN_color_fontColor_assist));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {
        switch (position) {
            case ImpTopbarOnClickListener.RIGHT:
                break;
        }
    }


    @Override
    protected void requestNet() {
        getGoodsList();
    }

    private THNWaittingDialog dialog;
    private void getGoodsList() {
        HashMap<String, String> params = ClientParamsAPI.getGoodsList("",1);
        HttpRequest.sendRequest(HttpRequest.GET, URL.PRODUCT_CATEGORY, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog = new THNWaittingDialog(GoodsCategoryEditActivity.this);
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
                GoodsCategoryData customerBean = JsonUtil.fromJson(json, GoodsCategoryData.class);
                if (customerBean.getStatus().getCode() == 200) {
                    refreshRecyclerView(customerBean.getData().getCategories());
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

    public void refreshRecyclerView(List<GoodsCategoryData.DataEntity.CategoriesEntity> getCategories) {
        if (getCategories == null || getCategories.size() <= 0) {
            return;
        }
        List<String> list = new ArrayList<>();
        for(int i = 0; i < getCategories.size(); i++) {
            GoodsCategoryData.DataEntity.CategoriesEntity categoriesEntity = getCategories.get(i);
            String name = categoriesEntity.getName();
            list.add(name);
        }
        arrayAdapter.setList(list);
    }
}

package com.thn.erp.goods;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.autoScrollViewpager.ScrollableView;
import com.thn.erp.view.common.LinearLayoutArrowTextView;
import com.thn.erp.view.common.PublicTopBar;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/26 10:47
 * Email: 895745843@qq.com
 */

public class GoodsDetailsActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.scrollableView)
    ScrollableView scrollableView;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.linearLoutArrowTextView1)
    LinearLayoutArrowTextView linearLoutArrowTextView1;
    @BindView(R.id.linearLoutCommonTextViewTextView1)
    com.thn.erp.view.common.LinearLayoutCommonTextView linearLoutCommonTextViewTextView1;
    @BindView(R.id.linearLoutCommonTextViewTextView2)
    com.thn.erp.view.common.LinearLayoutCommonTextView linearLoutCommonTextViewTextView2;
    @BindView(R.id.linearLoutArrowTextView2)
    LinearLayoutArrowTextView linearLoutArrowTextView2;
    @BindView(R.id.linearLoutCommonTextViewTextView3)
    com.thn.erp.view.common.LinearLayoutCommonTextView linearLoutCommonTextViewTextView3;
    @BindView(R.id.linearLoutCommonTextViewTextView4)
    com.thn.erp.view.common.LinearLayoutCommonTextView linearLoutCommonTextViewTextView4;
    @BindView(R.id.textView5)
    TextView textView5;

    private WaitingDialog dialog;
    private GoodsData.DataBean.ProductsBean mProductsBean; //基本信息

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_details;
    }

    @Override
    protected void getIntentData() {
        Parcelable extra = getIntent().getParcelableExtra("Extra");
        if (extra instanceof GoodsData.DataBean.ProductsBean) {
            mProductsBean = (GoodsData.DataBean.ProductsBean) extra;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        // TODO: 2018/3/26
        initTopbar();
        dialog = new WaitingDialog(this);
        setBasicDat0a();
    }

    private void setBasicDat0a() {
        textView1.setText(mProductsBean.name);
        textView2.setText("编号: " +mProductsBean.rid);
        textView3.setText("单位: 件");
        textView4.setText("￥ " +String.valueOf(mProductsBean.sale_price));
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("商品详情", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    protected void requestNet() {
        getGoodsList();
    }

    private void getGoodsList() {
        HashMap<String, String> params = ClientParamsAPI.generateCommonParams();
        HttpRequest.sendRequest(HttpRequest.GET, URL.PRODUCT_DETAILS, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
//                GoodsData customerBean = JsonUtil.fromJson(json, GoodsData.class);
//                if (customerBean.success == true) {
//                    updateData(customerBean.data.products);
//                } else {
//                    ToastUtils.showError(customerBean.status.message);
//                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    @Override
    public void onTopBarClick(View view, int position) {
        // TODO: 2018/3/26 to do something
    }
}

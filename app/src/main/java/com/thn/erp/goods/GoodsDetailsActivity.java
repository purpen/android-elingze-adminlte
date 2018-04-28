package com.thn.erp.goods;

import android.view.View;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.goods.beans.GoodsDetailData;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.autoScrollViewpager.ScrollableView;
import com.thn.erp.view.autoScrollViewpager.ViewPagerAdapter;
import com.thn.erp.view.common.LinearLayoutArrowTextView;
import com.thn.erp.view.common.PublicTopBar;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        mProductsBean = getIntent().getParcelableExtra(GoodsDetailsActivity.class.getSimpleName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (scrollableView!=null) scrollableView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scrollableView!=null) scrollableView.stop();
    }

    @Override
    protected void initView() {
        initTopbar();
        dialog = new WaitingDialog(this);
        setBasicDat0a();
    }

    private void initTopbar() {
        publicTopBar.setTopBarCenterTextView("商品详情", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    private void setBasicDat0a() {
        textView1.setText(mProductsBean.name);
        textView2.setText("编号: " + mProductsBean.rid);
        textView3.setText("单位: 件");
        textView4.setText("￥ " + String.valueOf(mProductsBean.sale_price));
    }


    @Override
    protected void requestNet() {
        getGoodsDetail();
    }

    private void getGoodsDetail() {
        String url = URL.BASE_URL + "products/" + mProductsBean.rid + "/detail";
        HttpRequest.sendRequest(HttpRequest.GET, url, new HttpRequestCallback() {

            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                GoodsDetailData data = JsonUtil.fromJson(json, GoodsDetailData.class);
                if (data.success == true) {
                    updateData(data);
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

    private void updateData(GoodsDetailData data) {
        List<GoodsDetailData.DataBean.ImagesBean> images = data.data.images;
        List<String> list = new ArrayList<>();
        for (GoodsDetailData.DataBean.ImagesBean image : images) {
            list.add(image.view_url);
        }
        scrollableView.setAdapter(new ViewPagerAdapter(activity, list));
        scrollableView.setAutoScrollDurationFactor(8);
        scrollableView.showIndicators();
        scrollableView.start();
    }


    @Override
    public void onTopBarClick(View view, int position) {
        // TODO: 2018/3/26 to do something
    }
}

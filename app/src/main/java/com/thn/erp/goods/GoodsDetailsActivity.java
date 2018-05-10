package com.thn.erp.goods;

import android.view.View;
import android.widget.ExpandableListView;
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
import com.thn.erp.view.MyExpandableListAdapter;
import com.thn.erp.view.autoScrollViewpager.ScrollableView;
import com.thn.erp.view.autoScrollViewpager.ViewPagerAdapter;
import com.thn.erp.view.common.PublicTopBar;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    @BindView(R.id.expandableListView0)
    ExpandableListView expandableListView0;
    @BindView(R.id.expandableListView1)
    ExpandableListView expandableListView1;

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
        if (scrollableView != null) scrollableView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scrollableView != null) scrollableView.stop();
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
        if (mProductsBean==null) return;
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
        initExpandable();
    }

    public static class HomeType {
        public String name;
        public String value;

        public HomeType(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    private void initExpandable() {
        ArrayList<String> groupArray0 = new ArrayList<>();
        groupArray0.add("库存100件");
        HashMap<String, ArrayList<HomeType>> hashMap0 = new HashMap<>();
        ArrayList<HomeType> ta0 = new ArrayList<>();
        ta0.add(new HomeType("默认仓", "25"));
        ta0.add(new HomeType("北京仓", "35"));
        ta0.add(new HomeType("杭州仓", "40"));
        hashMap0.put("库存100件", ta0);
        MyExpandableListAdapter adapter0 = new MyExpandableListAdapter(groupArray0, hashMap0);
        expandableListView0.setAdapter(adapter0);


        ArrayList<String> groupArray1 = new ArrayList<>();
        groupArray1.add("预购数量100件");
        HashMap<String, ArrayList<HomeType>> hashMap1 = new HashMap<>();
        ArrayList<HomeType> ta1 = new ArrayList<>();
        ta1.add(new HomeType("默认仓", "25"));
        ta1.add(new HomeType("北京仓", "35"));
        ta1.add(new HomeType("杭州仓", "40"));
        hashMap1.put("预购数量100件", ta1);

        MyExpandableListAdapter adapter1 = new MyExpandableListAdapter(groupArray1, hashMap1);
        expandableListView1.setAdapter(adapter1);
    }

    @Override
    public void onTopBarClick(View view, int position) {
        // TODO: 2018/5/9
    }
}

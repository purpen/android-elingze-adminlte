package com.thn.erp.goods;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.constant.ExtraKey;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/4/4 17:28
 * Email: 895745843@qq.com
 */

public class GoodsListActivity extends BaseActivity {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
//    @BindView(R.id.list_fragment)
//    GoodsListFragment listFragment;
    @BindView(R.id.linearLayout_container)
    LinearLayout linearLayoutContainer;

    private FragmentManager supportFragmentManager;
    private String titleName, cid;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void getIntentData() {
        String brandId = getIntent().getStringExtra(ExtraKey.BRAND_ID);
        String brandName = getIntent().getStringExtra(ExtraKey.BRAND_NAME);
        String categoryId = getIntent().getStringExtra(ExtraKey.CATEGORY_ID);
        String categoryName = getIntent().getStringExtra(ExtraKey.CATEGORY_NAME);

        titleName = TextUtils.isEmpty(brandName) ? categoryName : brandName;
        cid = TextUtils.isEmpty(brandId) ? categoryId : brandId;
    }

    @Override
    protected void initView() {
        initTopbar();
        addFragment();
    }

    private void addFragment() {
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        GoodsListFragment goodsListFragment = GoodsListFragment.newInstance(cid);
        fragmentTransaction.add(R.id.linearLayout_container, goodsListFragment).commit();
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView(titleName, getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(new ImpTopbarOnClickListener() {
            @Override
            public void onTopBarClick(View view, int position) {

            }
        });
    }
}

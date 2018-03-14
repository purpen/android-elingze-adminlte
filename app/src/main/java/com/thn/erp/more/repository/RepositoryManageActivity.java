package com.thn.erp.more.repository;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.common.ImpTopbarOnClickListener;
import com.thn.erp.view.common.PublicTopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryManageActivity extends BaseActivity implements ViewPager.OnPageChangeListener, ImpTopbarOnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.tabLayout_repository_tab)
    android.support.design.widget.TabLayout tabLayoutRepositoryTab;
    @BindView(R.id.viewPager_repository_list)
    ViewPager viewPagerRepositoryList;

    private List<String> mStringList;
    private List<BaseFragment> mFragments;
    private SlideViewPagerAdapter mSlideViewPagerAdapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_repository_manage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        initTopBar();
        getTitles();
        getFragments();
        mSlideViewPagerAdapter = new SlideViewPagerAdapter(getSupportFragmentManager(), mStringList, mFragments);
        viewPagerRepositoryList.setAdapter(mSlideViewPagerAdapter);
        viewPagerRepositoryList.addOnPageChangeListener(this);
        tabLayoutRepositoryTab.setupWithViewPager(viewPagerRepositoryList, true);
    }

    private void initTopBar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("仓库管理", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarLeftImageView(R.mipmap.ic_launcher);
        publicTopBar.setTopBarRightTextView("添加", Color.parseColor("#27AE59"));
        publicTopBar.setTopBarOnClickListener(this);
    }

    private void getTitles() {
        mStringList = new ArrayList<>();
        mStringList.add("已启用");
        mStringList.add("已禁用");
    }

    private void getFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new RepositoryManageFragmentLeft ());
        mFragments.add(new RepositoryManageFragmentRight ());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
                Toast.makeText(activity, "Right", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AddRepositoryActivity.class));
                break;
        }
    }
}

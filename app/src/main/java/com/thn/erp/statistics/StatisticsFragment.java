package com.thn.erp.statistics;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.base.BaseViewPagerAdapter;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.CustomViewPager;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by lilin on 2018/3/7.
 */

public class StatisticsFragment extends BaseFragment {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    private BaseViewPagerAdapter adapter;
    @Override
    protected int getLayout() {
        return R.layout.fragment_statistics;
    }



    @Override
    protected void initView() {
        customHeadView.setHeadGoBackShow(false);
        customHeadView.setCenterTxtShow(getString(R.string.statistics_title),getResources().getColor(android.R.color.white));
        ArrayList<Class> classes = new ArrayList<>();
        classes.add(StatisticSaleAmountFragment.class);
        classes.add(StatisticsGoodsFragment.class);
        classes.add(StatisticSaleAmountFragment.class);
        classes.add(StatisticOrderFragment.class);
        classes.add(StatisticSaleAmountFragment.class);
        String[] titles = getResources().getStringArray(R.array.statistics_titles);
        adapter=new BaseViewPagerAdapter(getChildFragmentManager(),classes, Arrays.asList(titles));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(classes.size());
        viewPager.setPagingEnabled(false);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    protected void installListener() {
        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
}

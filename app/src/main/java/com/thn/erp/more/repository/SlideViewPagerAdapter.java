package com.thn.erp.more.repository;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.thn.erp.base.BaseFragment;

import java.util.List;

/**
 * Created by Stephen on 2018/3/12 17:34
 * Email: 895745843@qq.com
 */

public class SlideViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;
    private List<String> titleList;

    public SlideViewPagerAdapter(FragmentManager fm, List<String> titleList, List<BaseFragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}

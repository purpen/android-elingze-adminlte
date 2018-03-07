package com.thn.erp.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author lilin
 *         created at 2016/8/8 13:03
 */
public class BaseViewPagerAdapter extends FragmentPagerAdapter {
    protected List<Class> classes;
    protected List<String> titles;
    public BaseViewPagerAdapter(FragmentManager fm, List<Class> classes, List<String> titles) {
        super(fm);
        if (null==classes) throw new IllegalArgumentException("argument classes must not be null");
        if (null==titles) throw new IllegalArgumentException("argument titles must not be null");
        this.classes = classes;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        try {
            fragment=(Fragment) classes.get(position).newInstance();
            return fragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

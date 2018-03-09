package com.thn.erp.user.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.thn.erp.base.BaseViewPagerAdapter;

import java.util.List;

/**
 * Created by lilin on 2017/7/17.
 */

public class LoginRegisterViewPagerAdapter extends BaseViewPagerAdapter {
    public LoginRegisterViewPagerAdapter(FragmentManager fm, List<Class> classes, List<String> titles, String productId){
        super(fm,classes,titles);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment  = super.getItem(position);
        Bundle bundle = new Bundle();
//        bundle.putInt(POSITION,position);
//        bundle.putString(ScrollTabHolderFragment.class.getSimpleName(),productId);
        fragment.setArguments(bundle);
        return fragment;
    }
}

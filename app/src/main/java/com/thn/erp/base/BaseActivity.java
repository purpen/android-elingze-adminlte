package com.thn.erp.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thn.erp.utils.LogUtil;

import butterknife.ButterKnife;


/**
 * Created by lilin on 2017/6/16.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected Activity activity;
//    private AppApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("onCreate()" + TAG);
//        application = (AppApplication) getApplication();
        getIntentData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        ShareSDK.initSDK(this);
        this.activity = this;
        setContentView(getLayout());
        ButterKnife.bind(this);
        initView();
        installListener();
        requestNet();
    }

    protected void getIntentData() {
    }

    protected abstract int getLayout();

    protected void initView() {

    }

    protected void installListener() {
    }

    protected void requestNet() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

}

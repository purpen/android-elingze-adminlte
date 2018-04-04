package com.thn.erp.zxing.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;


import com.thn.erp.R;
import com.thn.erp.common.interfaces.BaseViewInterface;
import com.thn.erp.common.interfaces.DialogControl;
import com.thn.erp.utils.DialogHelp;
import com.thn.erp.zxing.CommonToast;
import com.thn.erp.zxing.TDevice;

import butterknife.ButterKnife;

/**
 * baseActionBar Activity
 */
public abstract class ZxingBaseActivity extends AppCompatActivity implements DialogControl, View.OnClickListener, BaseViewInterface {
    protected final String TAG = getClass().getSimpleName();
    protected LayoutInflater mInflater;
    protected ActionBar mActionBar;
    private ProgressDialog waitDialog;
    private boolean isVisible;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TDevice.hideSoftKeyboard(getCurrentFocus());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mActionBar = getSupportActionBar();
        mInflater = getLayoutInflater();
        if (hasActionBar()) {
            initActionBar(mActionBar);
        }

        // 通过注解绑定控件
        ButterKnife.bind(this);

        init(savedInstanceState);
        initView();
        initData();
        isVisible = true;
    }

    protected void onBeforeSetContentLayout() {}

    protected boolean hasActionBar() {
        return true;
    }

    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }

    protected int getActionBarTitle() {
        return R.string.app_name;
    }

    protected boolean hasBackButton() {
        return false;
    }

    protected void init(Bundle savedInstanceState) {}

    protected void initActionBar(ActionBar actionBar) {
        if (actionBar == null)
            return;
        if (hasBackButton()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        } else {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setDisplayUseLogoEnabled(false);
            int titleRes = getActionBarTitle();
            if (titleRes != 0) {
                actionBar.setTitle(titleRes);
            }
        }
    }

    public void setActionBarTitle(int resId) {
        if (resId != 0) {
            setActionBarTitle(getString(resId));
        }
    }

    public void setActionBarTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        if (hasActionBar() && mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(TAG);
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(TAG);
//        MobclickAgent.onResume(this);
    }

    public void showToast(int msgResid, int icon, int gravity) {
        showToast(getString(msgResid), icon, gravity);
    }

    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(this);
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }

    @Override
    public ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public ProgressDialog showWaitDialog(String message) {
        if (isVisible) {
            if (waitDialog == null) {
                waitDialog = DialogHelp.getWaitDialog(this, message);
            }
            if (waitDialog != null) {
                waitDialog.setMessage(message);
                waitDialog.show();
            }
            return waitDialog;
        }
        return null;
    }

    @Override
    public void hideWaitDialog() {
        if (isVisible && waitDialog != null) {
            try {
                waitDialog.dismiss();
                waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

package com.thn.erp.overview.usermanage;

import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.SearchView;

import butterknife.BindView;


/**
 * 用户管理
 */

public class UserManageActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    @BindView(R.id.searchView)
    SearchView searchView;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_manage;
    }

    @Override
    protected void initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.user_manage_title);
        customHeadView.setHeadRightTxtShow(true,R.string.add_customer);
    }

    @Override
    protected void installListener() {
        // 实现TextWatcher监听即可
        searchView.setOnSearchClickListener(new SearchView.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                ToastUtils.showInfo("going search");
            }
        });
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showInfo("添加客户");
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //    @OnClick({R.id.btnCheckCode, R.id.btnSubmit})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btnCheckCode:
//                break;
//            case R.id.btnSubmit:
//                updatePassword();
//                break;
//        }
//    }

    /**
     * 重置密码
     */
//    private void updatePassword() {
//        HashMap<String, String> params = ClientParamsAPI.getResetPasswordRequestParams(account, userPsw, checkCode);
//        HttpRequest.sendRequest(HttpRequest.POST, URL.RESET_PASSWORD, params, new HttpRequestCallback() {
//            @Override
//            public void onStart() {
//                btnSubmit.setEnabled(false);
//            }
//
//            @Override
//            public void onSuccess(String json) {
//                btnSubmit.setEnabled(true);
//                ResetPasswordBean resetPasswordBean = JsonUtil.fromJson(json, ResetPasswordBean.class);
//                if (resetPasswordBean.meta.status_code == Constants.SUCCESS) {
//                    ToastUtils.showSuccess(resetPasswordBean.meta.message);
//                    finish();
//                } else {
//                    ToastUtils.showError(resetPasswordBean.meta.message);
//                }
//
//            }
//
//            @Override
//            public void onFailure(IOException e) {
//                btnSubmit.setEnabled(true);
//                ToastUtils.showError(R.string.network_err);
//            }
//        });
//    }


}

package com.thn.erp.user;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thn.erp.Constants;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.user.bean.AuthCheckCodeBean;
import com.thn.erp.user.bean.ResetPasswordBean;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.utils.Util;
import com.thn.erp.view.CustomHeadView;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lilin on 2017/7/17.
 */

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.et_phone_reg)
    EditText etPhoneReg;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btnCheckCode)
    Button btnCheckCode;
    @BindView(R.id.et_password_reg)
    EditText etPasswordReg;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    private String account;
    private String userPsw;
    private String checkCode;

    @Override
    protected int getLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.forget_password);
    }


    @OnClick({R.id.btnCheckCode, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCheckCode:
                getCheckCode();
                break;
            case R.id.btnSubmit:
                if (!checkUserInput()) return;
                updatePassword();
                break;
        }
    }

    private boolean checkUserInput() {
        account = etPhoneReg.getText().toString();

        if (TextUtils.isEmpty(account)) {
            ToastUtils.showInfo("手机号不能为空");
            return false;
        }

        if (!Util.isMobileNO(account)) {
            ToastUtils.showInfo("请输入正确手机号");
            return false;
        }

        checkCode = etCode.getText().toString();
        if (TextUtils.isEmpty(checkCode)) {
            return false;
        }

        userPsw = etPasswordReg.getText().toString();
        if (TextUtils.isEmpty(userPsw)) {
            ToastUtils.showInfo("请输入密码");
            return false;
        }

        if (userPsw.length()<6){
            ToastUtils.showInfo("密码长度最少为6位");
            return false;
        }

        return true;
    }


    /**
     * 获得验证码
     */
    private void getCheckCode() {
        account = etPhoneReg.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showInfo("请输入手机号");
            return;
        }

        if (!Util.isMobileNO(account)) {
            ToastUtils.showInfo("请输入正确手机号");
            return;
        }
        btnCheckCode.setEnabled(false);
        HashMap<String, String> params = ClientParamsAPI.getResetPasswordCodeRequestParams(account);
        HttpRequest.sendRequest(HttpRequest.POST, URL.FORGET_PASSWORD_CHECK_CODE, params, new HttpRequestCallback() {
            @Override
            public void onSuccess(String json) {
                AuthCheckCodeBean authCheckCodeBean = JsonUtil.fromJson(json, AuthCheckCodeBean.class);
                if (authCheckCodeBean.meta.status_code == Constants.SUCCESS) {
                    if (null == thread) {
                        thread = new Thread(new MyThread());
                        thread.start();
                    } else {
                        timeInterval = Constants.CHECK_CODE_INTERVAL;
                    }
                } else {
                    btnCheckCode.setEnabled(true);
                    ToastUtils.showError(authCheckCodeBean.meta.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                btnCheckCode.setEnabled(true);
                ToastUtils.showError(R.string.network_err);
            }
        });
    }


    /**
     * 重置密码
     */
    private void updatePassword() {
        HashMap<String, String> params = ClientParamsAPI.getResetPasswordRequestParams(account, userPsw, checkCode);
        HttpRequest.sendRequest(HttpRequest.POST, URL.RESET_PASSWORD, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                btnSubmit.setEnabled(false);
            }

            @Override
            public void onSuccess(String json) {
                btnSubmit.setEnabled(true);
                ResetPasswordBean resetPasswordBean = JsonUtil.fromJson(json, ResetPasswordBean.class);
                if (resetPasswordBean.meta.status_code == Constants.SUCCESS) {
                    ToastUtils.showSuccess(resetPasswordBean.meta.message);
                    finish();
                } else {
                    ToastUtils.showError(resetPasswordBean.meta.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                btnSubmit.setEnabled(true);
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    private Thread thread;
    private boolean isWorking=true;
    private int timeInterval = Constants.CHECK_CODE_INTERVAL;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                timeInterval--;
                btnCheckCode.setText(timeInterval + "s");
                if (timeInterval < 0) {
                    btnCheckCode.setEnabled(true);
                    btnCheckCode.setText(R.string.get_code);
                }
            }
        }
    };

    private class MyThread implements Runnable {
        @Override
        public void run() {
            while (isWorking) {
                try {
                    Thread.sleep(1000);
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }
            }
        }
    }
}

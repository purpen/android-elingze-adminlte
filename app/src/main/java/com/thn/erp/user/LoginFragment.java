package com.thn.erp.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.taihuoniao.banmen.Constants;
import com.taihuoniao.banmen.R;
import com.taihuoniao.banmen.base.BaseFragment;
import com.taihuoniao.banmen.main.MainActivity;
import com.taihuoniao.banmen.network.ClientParamsAPI;
import com.taihuoniao.banmen.network.HttpRequest;
import com.taihuoniao.banmen.network.HttpRequestCallback;
import com.taihuoniao.banmen.network.URL;
import com.taihuoniao.banmen.useraccount.bean.LoginBean;
import com.taihuoniao.banmen.utils.JsonUtil;
import com.taihuoniao.banmen.utils.SPUtil;
import com.taihuoniao.banmen.utils.ToastUtils;
import com.taihuoniao.banmen.utils.Util;
import com.taihuoniao.banmen.views.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment {
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    private String userName;
    private String userPsw;
    private String page;
    private WaitingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        page = bundle.getString(LoginActivity.class.getSimpleName());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }


    @Override
    protected void initView() {
        dialog = new WaitingDialog(activity);
    }


    @OnClick({R.id.tv_forget_password, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                startActivity(new Intent(activity, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                if (!checkUserInput()) return;
                loginUser();
                break;
        }
    }

    private void loginUser() {
        if (TextUtils.isEmpty(userName)) return;
        if (TextUtils.isEmpty(userPsw)) return;
        HashMap<String, String> params = ClientParamsAPI.getLoginRequestParams(userName, userPsw);
        HttpRequest.sendRequest(HttpRequest.POST, URL.AUTH_LOGIN, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                LoginBean loginBean = JsonUtil.fromJson(json, LoginBean.class);
                if (loginBean.meta.status_code == Constants.SUCCESS) {
                    SPUtil.write(Constants.TOKEN, loginBean.data.token);
                    jump2MainPage();
                }else {
                    ToastUtils.showError(loginBean.meta.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    /**
     * 跳转主界面
     */
    private void jump2MainPage() {
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
        activity.finish();
    }

    private boolean checkUserInput() {
        userName = etPhone.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showInfo("请输入手机号");
            return false;
        }
        if (!Util.isMobileNO(userName)) {
            ToastUtils.showInfo("请输入正确手机号");
            return false;
        }

        userPsw = etPassword.getText().toString();
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

}

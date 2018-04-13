package com.thn.erp.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thn.erp.Constants;
import com.thn.erp.MainActivity;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.user.bean.LoginBean;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.SPUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String userName;
    private String userPsw;
    private String page;
    private WaitingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
//        page = bundle.getString(LoginActivity.class.getSimpleName());
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
        final String authorzationCode = getTempAuthorzationCode(params);
        HttpRequest.sendRequest(HttpRequest.POST, URL.AUTH_LOGIN, authorzationCode, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                LoginBean loginBean = JsonUtil.fromJson(json, LoginBean.class);
                if (loginBean.success) {
                    SPUtil.write(Constants.TOKEN, loginBean.data.token);
                    SPUtil.write(Constants.AUTHORIZATION, authorzationCode);
                    jump2MainPage();
                }else {
                    ToastUtils.showError(loginBean.status.message);
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
//        if (!Util.isMobileNO(userName)) {
//            ToastUtils.showInfo("请输入正确手机号");
//            return false;
//        }

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

    @NonNull
    private String getTempAuthorzationCode(HashMap<String, String> params) {
        String str = params.get("email") + ":" + params.get("password");
        str = "Basic  " + Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        return str.trim();
    }
}

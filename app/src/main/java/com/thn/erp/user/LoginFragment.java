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

import com.thn.basemodule.tools.LogUtil;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.erp.Constants;
import com.thn.erp.MainActivity;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.user.bean.AppKeyData;
import com.thn.erp.user.bean.LoginBean;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.basemodule.tools.SPUtil;

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
        dialog = new WaitingDialog(getActivity());
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
        final HashMap<String, String> params = ClientParamsAPI.getLoginRequestParams(userName, userPsw);
        final String authorzationCode = getTempAuthorzationCode(params);
        //换成商家登录URL,根据返回的storeid换app_key和app_secrete
        HttpRequest.sendRequest(HttpRequest.POST, URL.AUTH_LOGIN,authorzationCode,params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
                LoginBean loginBean = JsonUtil.fromJson(json, LoginBean.class);
                if (loginBean.success) {
                    getAppKeyAndSecret(loginBean.data.store_rid,authorzationCode);
                }else {
                    ToastUtil.showError(loginBean.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
    }

    private void getAppKeyAndSecret(String storeId, final String authorzationCode) {
        final HashMap<String, String> params = ClientParamsAPI.appKeyAndSecretParams(storeId);

        //换成商家登录URL,根据返回的storeid换app_key和app_secrete
        HttpRequest.sendRequest(HttpRequest.POST, URL.APPKEY_APPSECRET,authorzationCode,params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                AppKeyData appKeyData = JsonUtil.fromJson(json, AppKeyData.class);
                if (appKeyData.success) {
                    SPUtil.write(Constants.AUTHORIZATION, authorzationCode);
                    SPUtil.write(Constants.APP_KEY,appKeyData.data.app_key);
                    SPUtil.write(Constants.APP_SECRET,appKeyData.data.access_token);
                    jump2MainPage();
                }else {
                    ToastUtil.showError(appKeyData.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
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
            ToastUtil.showInfo("请输入手机号");
            return false;
        }
//        if (!Util.isMobileNO(userName)) {
//            ToastUtil.showInfo("请输入正确手机号");
//            return false;
//        }

        userPsw = etPassword.getText().toString();
        if (TextUtils.isEmpty(userPsw)) {
            ToastUtil.showInfo("请输入密码");
            return false;
        }

        if (userPsw.length()<6){
            ToastUtil.showInfo("密码长度最少为6位");
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

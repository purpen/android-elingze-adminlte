package com.thn.erp.user;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import com.thn.erp.user.bean.AuthCheckCodeBean;
import com.thn.erp.user.bean.RegisterBean;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.erp.utils.LogUtil;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment {
    @BindView(R.id.et_phone_reg)
    EditText etPhoneReg;
    @BindView(R.id.et_password_reg)
    EditText etPasswordReg;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btnCheckCode)
    Button btnCheckCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private String account;
    private String userPsw;
    private String checkCode;
    private WaitingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {
        dialog = new WaitingDialog(activity);
    }


    @OnClick({R.id.btn_register, R.id.btnCheckCode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckCode:
                getCheckCode();
                break;
            case R.id.btn_register:
                if (!checkUserInput()) return;
                registerUser();
                break;
            default:
                break;
        }
    }

    /**
     * 获得验证码
     */
    private void getCheckCode() {
        account = etPhoneReg.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showInfo("请输入手机号");
            return;
        }

//        if (!Util.isMobileNO(account)) {
//            ToastUtil.showInfo("请输入正确手机号");
//            return;
//        }

        btnCheckCode.setEnabled(false);
        HashMap<String, String> params = ClientParamsAPI.getCheckCodeRequestParams(account);
        HttpRequest.sendRequest(HttpRequest.POST, URL.AUTH_CHECK_CODE, params, new HttpRequestCallback() {
            @Override
            public void onSuccess(String json) {
                AuthCheckCodeBean authCheckCodeBean = JsonUtil.fromJson(json, AuthCheckCodeBean.class);
                if (authCheckCodeBean.meta.status_code == Constants.SUCCESS) {
                    if (null == thread) {
//                        LogUtil.e(authCheckCodeBean.data.code);
                        thread = new Thread(new MyThread());
                        thread.start();
                    } else {
                        timeInterval = Constants.CHECK_CODE_INTERVAL;
                    }
                } else {
                    btnCheckCode.setEnabled(true);
                    ToastUtil.showError(authCheckCodeBean.meta.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                LogUtil.e(e.toString());
                btnCheckCode.setEnabled(true);
                ToastUtil.showError(R.string.network_err);
            }
        });
    }


    private void registerUser() {
        HashMap<String, String> params = ClientParamsAPI.getRegisterRequestParams(account, userPsw, checkCode);
        HttpRequest.sendRequest(HttpRequest.POST, URL.AUTH_REGISTER, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                RegisterBean registerBean = JsonUtil.fromJson(json, RegisterBean.class);
                if (registerBean.success == true) {
                    ((LoginRegisterActivity)activity).viewPager.setCurrentItem(0,true);
//                    TODO 切换到登录界面
//                    SPUtil.write(Constants.TOKEN, loginBean.data.token);
//                    jump2MainPage();
                } else {
                    ToastUtil.showError(registerBean.status.message);
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
        isWorking=false;
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
        activity.finish();
    }

    private boolean checkUserInput() {
        account = etPhoneReg.getText().toString();

        if (TextUtils.isEmpty(account)) {
            ToastUtil.showInfo("手机号不能为空");
            return false;
        }

//        if (!Util.isMobileNO(account)) {
//            ToastUtil.showInfo("请输入正确手机号");
//            return false;
//        }

        checkCode = etCode.getText().toString();
        if (TextUtils.isEmpty(checkCode)) {
            return false;
        }

        userPsw = etPasswordReg.getText().toString();
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

    private Thread thread;
    private boolean isWorking=true;
    private int timeInterval = Constants.CHECK_CODE_INTERVAL;

    private  final Handler handler = new Handler() {
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

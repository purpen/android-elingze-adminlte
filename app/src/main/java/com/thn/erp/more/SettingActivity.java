package com.thn.erp.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thn.basemodule.tools.WaitingDialog;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.Constants;
import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.user.LoginRegisterActivity;
import com.thn.erp.user.bean.LogoutBean;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.erp.utils.SPUtil;
import com.thn.erp.view.common.LinearLayoutArrowTextView;
import com.thn.erp.view.common.PublicTopBar;

import java.io.IOException;

import am.example.printer.PrinterActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/3/21 15:22
 * Email: 895745843@qq.com
 */

public class SettingActivity extends BaseStyle2Activity {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.linearLayoutArrowTextView1)
    LinearLayoutArrowTextView linearLayoutArrowTextView1;
    @BindView(R.id.linearLayoutArrowTextView2)
    LinearLayoutArrowTextView linearLayoutArrowTextView2;
    @BindView(R.id.linearLayoutArrowTextView3)
    LinearLayoutArrowTextView linearLayoutArrowTextView3;
    @BindView(R.id.linearLayoutArrowTextView4)
    LinearLayoutArrowTextView linearLayoutArrowTextView4;
    @BindView(R.id.textView_more_setting_loginOut)
    TextView textViewMoreSettingLoginOut;

    @Override
    protected int getLayout() {
        return R.layout.activity_more_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        initTopbar();
        initOtherLinearLayout();
    }

    private void initOtherLinearLayout() {
        linearLayoutArrowTextView1.setName("账号安全");
        linearLayoutArrowTextView2.setName("手势密码设置");
        linearLayoutArrowTextView3.setName("打印设置");
        linearLayoutArrowTextView4.setName("关于" + getString(R.string.app_name));
    }

    private void initTopbar() {

    }

    @OnClick({R.id.linearLayoutArrowTextView1, R.id.linearLayoutArrowTextView2, R.id.linearLayoutArrowTextView3, R.id.linearLayoutArrowTextView4, R.id.textView_more_setting_loginOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearLayoutArrowTextView1:
                break;
            case R.id.linearLayoutArrowTextView2:
                break;
            case R.id.linearLayoutArrowTextView3:
                startActivity(new Intent(SettingActivity.this, PrinterActivity.class));
                break;
            case R.id.linearLayoutArrowTextView4:
                break;
            case R.id.textView_more_setting_loginOut: //退出登录
                logoutUser();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logoutUser() {
        final WaitingDialog dialog = new WaitingDialog(this);
        HttpRequest.sendRequest(HttpRequest.POST, URL.LOGOUT_USER,new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                LogoutBean bean = JsonUtil.fromJson(json, LogoutBean.class);
                if (bean.success == true) {
                    ToastUtil.showSuccess("已退出登录");
                } else {
                    ToastUtil.showError(bean.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
        SPUtil.clear(Constants.AUTHORIZATION);
        startActivity(new Intent(activity, LoginRegisterActivity.class));
        activity.finish();
    }

}

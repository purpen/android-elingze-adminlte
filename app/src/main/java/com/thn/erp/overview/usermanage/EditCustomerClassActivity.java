package com.thn.erp.overview.usermanage;

import android.view.View;
import android.widget.Button;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomHeadView;

import butterknife.BindView;


/**
 * 编辑客户分类
 */

public class EditCustomerClassActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    @BindView(R.id.addClassBtn)
    Button addClassBtn;

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_customer_class;
    }

    @Override
    protected void initView() {
//        customHeadView.setBackgroundColor(getResources().getColor(android.R.color.white));
        customHeadView.setHeadCenterTxtShow(true, R.string.select_class_title);
        customHeadView.setHeadRightTxtShow(true, R.string.save);
    }

    @Override
    protected void installListener() {
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showInfo("保存");
            }
        });


    }


//    @OnClick({R.id.itemUserClass})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.itemUserClass:
//
//                break;
//            default:
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

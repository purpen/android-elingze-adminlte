package com.thn.erp.overview.usermanage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.thn.basemodule.tools.WaitingDialog;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.bean.AddCustomerGradeData;
import com.thn.erp.overview.usermanage.bean.CustomerClassData;
import com.thn.erp.overview.usermanage.bean.EditGradeData;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.erp.view.CustomHeadView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.BindView;

/**
 *  新增客户等级
 */

public class AddGradeActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.et)
    EditText et;
    WaitingDialog dialog;
    private MyHandler myHandler;
    private CustomerClassData.DataBean.GradesBean grade;
    @Override
    protected void getIntentData() {
        grade = getIntent().getParcelableExtra(AddGradeActivity.class.getSimpleName());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_grade;
    }

    @Override
    protected void initView() {
        dialog = new WaitingDialog(this);
        myHandler = new MyHandler(this);
        int titleId;
        if (grade==null){
            titleId = R.string.add_customer_grade;
        }else {
            titleId = R.string.edit_customer_grade;
            et.setText(grade.name);
            et.setSelection(grade.name.length());
        }
        customHeadView.setHeadCenterTxtShow(true, titleId);
        customHeadView.setHeadRightTxtShow(true, R.string.save);
    }

    @Override
    protected void installListener() {
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = et.getText();
                if (TextUtils.isEmpty(text)){
                    ToastUtil.showInfo("请输入客户分类");
                    return;
                }

                if (grade==null){
                    addGrade(text.toString());
                }else {
                    editGrade(text.toString());
                }

            }
        });
    }

    /**
     * 添加分类
     */
    private void addGrade(String text){
        HashMap<String, String> params = ClientParamsAPI.getAddGradeParams(text);
        HttpRequest.sendRequest(HttpRequest.POST, URL.CUSTOMER_GRADE_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                AddCustomerGradeData data = JsonUtil.fromJson(json, AddCustomerGradeData.class);
                if (data.success == true) {
                    ToastUtil.showSuccess(R.string.submit_success);
                    myHandler.sendEmptyMessageDelayed(0, 1000);
                } else {
                    ToastUtil.showError(data.status.message);
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
     * 编辑分类
     */
    private void editGrade(String text){
        HashMap<String, String> params = ClientParamsAPI.getAddGradeParams(text);
        String url=URL.BASE_URL+"customer_grades/"+grade.rid;
        HttpRequest.sendRequest(HttpRequest.PUT, url, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                EditGradeData data = JsonUtil.fromJson(json, EditGradeData.class);
                if (data.success == true) {
                    ToastUtil.showSuccess(R.string.submit_success);
                    myHandler.sendEmptyMessageDelayed(0, 1000);
                } else {
                    ToastUtil.showError(data.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
    }

    private static class MyHandler extends Handler{
        private WeakReference<Activity> weakReference;
        public MyHandler(Activity activity){
            weakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = weakReference.get();
            if (activity!=null){
                activity.finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}

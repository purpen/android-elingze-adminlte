package com.thn.erp.overview.usermanage;

import android.content.Intent;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.bean.AddCustomerData;
import com.thn.erp.overview.usermanage.bean.CustomerData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.CustomItemLayout;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 新增客户
 */

public class AddCustomActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.itemUserName)
    CustomItemLayout itemUserName;
    @BindView(R.id.itemContact)
    CustomItemLayout itemContact;
    @BindView(R.id.itemPhone)
    CustomItemLayout itemPhone;
    @BindView(R.id.itemUserClass)
    CustomItemLayout itemUserClass;
    @BindView(R.id.switchCompat)
    SwitchCompat switchCompat;
    @BindView(R.id.itemTel)
    CustomItemLayout itemTel;
    @BindView(R.id.itemEmail)
    CustomItemLayout itemEmail;
    @BindView(R.id.itemFoxmail)
    CustomItemLayout itemFoxmail;
    @BindView(R.id.itemGender)
    CustomItemLayout itemGender;
    @BindView(R.id.itemBirth)
    CustomItemLayout itemBirth;
    @BindView(R.id.itemProvince)
    CustomItemLayout itemProvince;
    @BindView(R.id.itemCity)
    CustomItemLayout itemCity;
    @BindView(R.id.itemCounty)
    CustomItemLayout itemCounty;
    @BindView(R.id.itemDetail)
    CustomItemLayout itemDetail;
    @BindView(R.id.itemComment)
    CustomItemLayout itemComment;
    private WaitingDialog dialog;
    private String name="";
    private String province="";
    private String city="";
    private String area="";
    private String street_address="";
    private String mobile="";
    private String phone="";
    private String email="";
    private CustomerData.DataBean.CustomersBean customerBean;
    @Override
    protected void getIntentData() {
        Intent intent =getIntent();
        customerBean=intent.getParcelableExtra(AddCustomActivity.class.getSimpleName());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_custom;
    }


    @Override
    protected void initView() {
        dialog = new WaitingDialog(this);
        customHeadView.setHeadCenterTxtShow(true, R.string.add_customer_title);
        customHeadView.setHeadRightTxtShow(true, R.string.save);

        itemUserName.setTVStyle(0, R.string.custom_name, R.color.color_222);
        itemUserName.setRightMoreImgStyle(false);
        itemUserName.setRightETStyle(R.string.please_input, R.color.color_666, InputType.TYPE_CLASS_TEXT, true);

        itemContact.setTVStyle(0, R.string.custom_contact, R.color.color_222);
        itemContact.setRightMoreImgStyle(false);
        itemContact.setRightETStyle(R.string.please_input, R.color.color_666, InputType.TYPE_CLASS_TEXT, true);

        itemPhone.setTVStyle(0, R.string.custom_phone, R.color.color_222);
        itemPhone.setRightMoreImgStyle(false);
        itemPhone.setRightETStyle(R.string.please_input, R.color.color_666, InputType.TYPE_CLASS_TEXT, true);

        itemUserClass.setTVStyle(0, R.string.custom_class, R.color.color_222);
        itemUserClass.setTvArrowLeftStyle(true, R.string.please_select);


        itemTel.setTVStyle(0, R.string.custom_tel, R.color.color_222);
        itemTel.setRightMoreImgStyle(false);
        itemTel.setRightETStyle(R.string.please_input, R.color.color_666, InputType.TYPE_CLASS_TEXT, true);

        itemEmail.setTVStyle(0, R.string.custom_mail, R.color.color_222);
        itemEmail.setRightMoreImgStyle(false);
        itemEmail.setRightETStyle(R.string.please_input, R.color.color_666, InputType.TYPE_CLASS_TEXT, true);

        itemFoxmail.setTVStyle(0, R.string.custom_fox, R.color.color_222);
        itemFoxmail.setRightMoreImgStyle(false);
        itemFoxmail.setRightETStyle(R.string.please_input, R.color.color_666, InputType.TYPE_CLASS_TEXT, true);

        itemGender.setTVStyle(0, R.string.custom_gender, R.color.color_222);
        itemGender.setTvArrowLeftStyle(true, R.string.please_select);

        itemBirth.setTVStyle(0, R.string.custom_birth, R.color.color_222);
        itemBirth.setTvArrowLeftStyle(true, R.string.please_select);

        itemProvince.setTVStyle(0, R.string.custom_province, R.color.color_222);
        itemProvince.setTvArrowLeftStyle(true, R.string.please_select);

        itemCity.setTVStyle(0, R.string.custom_city, R.color.color_222);
        itemCity.setTvArrowLeftStyle(true, R.string.please_select);

        itemCounty.setTVStyle(0, R.string.custom_county, R.color.color_222);
        itemCounty.setTvArrowLeftStyle(true, R.string.please_select);

        itemDetail.setTVStyle(0, R.string.custom_detail_addr, R.color.color_222);
        itemDetail.setRightMoreImgStyle(false);
        itemDetail.setRightETStyle(R.string.please_input, R.color.color_666, InputType.TYPE_CLASS_TEXT, true);

        itemComment.setTVStyle(0, R.string.custom_comment, R.color.color_222);
        itemComment.setRightMoreImgStyle(false);
        itemComment.setRightETStyle(R.string.please_input, R.color.color_666, InputType.TYPE_CLASS_TEXT, true);


        if (customerBean!=null){
            itemUserName.setEditTextContent(customerBean.name);
            itemPhone.setEditTextContent(customerBean.mobile);
        }
    }

    @Override
    protected void installListener() {
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomer();
            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ToastUtils.showInfo("开");
                } else {
                    ToastUtils.showInfo("关");
                }
            }
        });
    }


    @OnClick({R.id.itemUserClass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itemUserClass:
                startActivity(new Intent(activity,EditCustomerClassActivity.class));
                break;
            default:
                break;
        }
    }


    private void addCustomer() {
        if (!checkUserInput()) return;
        HashMap<String, String> params = ClientParamsAPI.addCustomer(1,name,province,city,area,street_address,mobile,phone,email);
        HttpRequest.sendRequest(HttpRequest.POST, URL.CUSTOMER_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                AddCustomerData data = JsonUtil.fromJson(json, AddCustomerData.class);
                if (data.success == true) {
                    ToastUtils.showSuccess(R.string.submit_success);
                } else {
                    ToastUtils.showError(data.status.message);
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
     * 检查用户输入
     */
    private boolean checkUserInput() {
        name=itemUserName.getRightETTxt();
        if (TextUtils.isEmpty(name)){
            ToastUtils.showInfo(R.string.input_username);
            return false;
        }
//        手机
        mobile = itemPhone.getRightETTxt();
        if (TextUtils.isEmpty(mobile)){
            ToastUtils.showInfo(R.string.input_phone);
            return false;
        }

        province = itemProvince.getTvarrowLeftTxt();
        city = itemCity.getTvarrowLeftTxt();
        area = itemCounty.getTvarrowLeftTxt();
        street_address = itemDetail.getRightETTxt();
        email = itemEmail.getRightETTxt();
//        座机
        phone = itemTel.getRightETTxt();
        return true;
    }
}

package com.thn.erp.sale;

import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.thn.basemodule.tools.WaitingDialog;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.AddressData;
import com.thn.erp.sale.bean.CommitConsigneeData;
import com.thn.erp.sale.bean.ProvinceCityRestrict;
import com.thn.erp.sale.bean.TownsData;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.erp.utils.Util;
import com.thn.erp.view.CustomHeadView;
import java.io.IOException;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lilin
 *         created at 2016/10/26 14:11
 */
public class AddNewAddressActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.et_consignee_name)
    EditText etConsigneeName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_zip_code)
    EditText etZipCode;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address_details)
    EditText etAddressDetails;
    @BindView(R.id.switchCompat)
    SwitchCompat switchCompat;
    //从选择地址界面传递过来的数据
    private AddressData.DataBean addressBean;
    private String provinceId;
    private String cityId;
    private String countyId;
    private String townId;
    private WaitingDialog dialog;
    private AddressSelectFragment addressSelectFragment;
    private ProvinceCityRestrict.DataBean curProvince;
    private ProvinceCityRestrict.DataBean curCity;
    private ProvinceCityRestrict.DataBean curCounty;
    private TownsData.DataBean curTown;


    @Override
    protected int getLayout() {
        return R.layout.activity_add_new_address;
    }

    @Override
    protected void getIntentData() {
        AddressData.DataBean extra= getIntent().getParcelableExtra(AddNewAddressActivity.class.getSimpleName());
        if (extra!=null) {
            addressBean = extra;
        }
    }

    @Override
    protected void initView() {
        dialog = new WaitingDialog(this);
        if (addressBean != null) {
            customHeadView.setHeadCenterTxtShow(true, R.string.edit_address);
            customHeadView.setHeadRightTxtShow(true, R.string.save);
            customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//保存地址
                    commitConsigneeAddress();
                }
            });
            etConsigneeName.setText(addressBean.full_name);
            etPhone.setText(addressBean.mobile);
            String builder = addressBean.province +
                    " " +
                    addressBean.city +
                    " " +
                    addressBean.town +
                    " " +
                    addressBean.area;
            tvAddress.setText(builder);
            etAddressDetails.setText(addressBean.street_address);
//            etZipCode.setText(addressBean.);
            if (addressBean.is_default) {
                switchCompat.setChecked(true);
            } else {
                switchCompat.setChecked(false);
            }
        } else {
            customHeadView.setHeadCenterTxtShow(true, R.string.new_consignee_address);
            customHeadView.setHeadRightTxtShow(true,R.string.save);
            customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    保存地址
                    commitConsigneeAddress();
                }
            });
        }
    }

    @Override
    protected void requestNet() {

    }

    @OnClick({R.id.tv_address,R.id.switchCompat})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                if (addressSelectFragment == null) {
                    addressSelectFragment = new AddressSelectFragment();
                    addressSelectFragment.show(activity.getFragmentManager(), AddressSelectFragment.class.getSimpleName());
                    addressSelectFragment.setOnAddressChoosedListener(new AddressSelectFragment.OnAddressChoosedListener() {
                        @Override
                        public void onAddressChoosed(ProvinceCityRestrict.DataBean province, ProvinceCityRestrict.DataBean city, ProvinceCityRestrict.DataBean county, TownsData.DataBean towns) {
                            StringBuilder builder = new StringBuilder();
                            if (province != null) {
                                builder.append(province.name);
                                builder.append(" ");
                                curProvince = province;
                            }
                            if (city != null) {
                                builder.append(city.name);
                                builder.append(" ");
                                curCity = city;
                            }
                            if (county != null) {
                                builder.append(county.name);
                                builder.append(" ");
                                curCounty = county;
                            }
                            if (towns != null) {
                                builder.append(towns.name);
                                builder.append(" ");
                                curTown = towns;
                            }
                            tvAddress.setText(builder.toString());
                        }
                    });
                } else {
                    addressSelectFragment.show(activity.getFragmentManager(), AddressSelectFragment.class.getSimpleName());
                }
                break;
            default:
                break;
        }
    }


    /**
     * 保存地址信息
     */
    private void commitConsigneeAddress() {
        String consigneeName = etConsigneeName.getText().toString().trim();
        if (TextUtils.isEmpty(consigneeName)) {
            ToastUtil.showInfo(R.string.name_is_empty);
            return;
        }

        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showInfo(R.string.phone_is_empty);
            return;
        }

        if (!Util.isMobileNO(etPhone.getText().toString())) {
            ToastUtil.showInfo(R.string.phone_is_err);
            return;
        }

        if (TextUtils.equals(tvAddress.getText(), getResources().getString(R.string.please_select_address))) {
            ToastUtil.showInfo(R.string.please_select_address);
            return;
        }

        String addressDetail = etAddressDetails.getText().toString().trim();
        if (TextUtils.isEmpty(addressDetail)) {
            ToastUtil.showInfo(R.string.address_details_is_empty);
            return;
        }
        String method;
        if (addressBean==null){ //新增地址
            if (curProvince != null) {
                provinceId = String.valueOf(curProvince.oid);
            } else {
                provinceId = "0";
            }
            if (curCity != null) {
                cityId = String.valueOf(curCity.oid);
            } else {
                cityId = "0";
            }
            if (curCounty != null) {
                countyId = String.valueOf(curCounty.oid);
            } else {
                countyId = "0";
            }
            if (curTown != null) {
                townId = String.valueOf(curTown.oid);
            }else {
                townId = "0";
            }
            method=HttpRequest.POST;
        }else { //编辑地址
            method = HttpRequest.DELETE;
            ToastUtil.showInfo("缺少省ID");
//            TODO 缺少省市区ID
//                provinceId=addressBean.;
//                cityId=addressBean.city_id;
//                countyId=addressBean.county_id;
//                townId=addressBean.town_id;
        }

        HashMap<String, String> params = ClientParamsAPI.getCommitAddressParams(consigneeName, phone, provinceId, cityId, countyId, townId, addressDetail, etZipCode.getText().toString(), switchCompat.isChecked());
        HttpRequest.sendRequest(method,URL.COMMIT_ADDRESS,params, new HttpRequestCallback(){
            @Override
            public void onStart() {
                if (!activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                CommitConsigneeData commitConsigneeData = JsonUtil.fromJson(json, CommitConsigneeData.class);
                if (commitConsigneeData.success) {
                    ToastUtil.showSuccess(commitConsigneeData.status.message);
//                    Intent intent = new Intent();
//                    intent.putExtra("address", 1);
//                    setResult(DataConstants.RESULTCODE_ADDNEWADDRESS, intent);
                    finish();
                } else {
                    ToastUtil.showError(commitConsigneeData.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
    }
}

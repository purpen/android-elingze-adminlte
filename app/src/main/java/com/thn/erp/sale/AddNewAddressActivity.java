package com.thn.erp.sale;

import android.content.Intent;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.DataConstants;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.AddressData;
import com.thn.erp.sale.bean.CommitConsigneeData;
import com.thn.erp.sale.bean.DeleteAddressData;
import com.thn.erp.sale.bean.ProvinceCityRestrict;
import com.thn.erp.sale.bean.TownsData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.utils.Util;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.dialog.DefaultDialog;
import com.thn.erp.view.dialog.IDialogListenerConfirmBack;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
    @BindView(R.id.btn_commit)
    Button btnCommit;
    //从选择地址界面传递过来的数据
    private AddressData.DataBean addressBean;
    private String provinceId;
    private String cityId;
    private String countyId;
    private String townId;
//    private boolean isdefault = true;//设置此地址是否为默认地址
    private THNWaittingDialog dialog;
    private AddressSelectFragment addressSelectFragment;
    private ProvinceCityRestrict.DataBean curProvince;
    private ProvinceCityRestrict.DataBean curCity;
    private ProvinceCityRestrict.DataBean curCounty;
    private TownsData.DataBean curTown;
    private boolean isAddressSelected;


    @Override
    protected int getLayout() {
        return R.layout.activity_add_new_address;
    }

    @Override
    protected void getIntentData() {
        AddressData.DataBean extra= getIntent().getParcelableExtra(CreateOrderActivity.class.getSimpleName());
        if (extra!=null) {
            addressBean = extra;
        }
    }

    @Override
    protected void initView() {
        dialog = new THNWaittingDialog(this);
        if (addressBean != null) {
            customHeadView.setHeadCenterTxtShow(true, R.string.edit_address);
            customHeadView.setHeadRightTxtShow(true, R.string.delete);
            customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DefaultDialog(AddNewAddressActivity.this, getResources().getString(R.string.hint_dialog_delete_address_title), getResources().getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {

                        @Override
                        public void clickRight() {
                            HashMap<String, String> params = ClientParamsAPI.deleteAddressParams();
                            Call httpHandler = HttpRequest.sendRequest(HttpRequest.DELETE,URL.DELETE_ADDRESS, params,new HttpRequestCallback() {
                                @Override
                                public void onStart() {
                                    if (dialog != null && !activity.isFinishing()) dialog.show();
                                }

                                @Override
                                public void onSuccess(String json) {
                                    DeleteAddressData deleteAddressData = JsonUtil.fromJson(json, DeleteAddressData.class);
                                    if (deleteAddressData.success) {
                                        ToastUtils.showSuccess("删除成功");
                                        Intent intent = new Intent();
                                        intent.putExtra("address", 1);
                                        setResult(DataConstants.RESULTCODE_ADDNEWADDRESS, intent);
                                        finish();
                                    } else {
                                        ToastUtils.showError(deleteAddressData.status.message);
                                    }
                                }

                                @Override
                                public void onFailure(IOException e) {
                                    dialog.dismiss();
                                    ToastUtils.showError(R.string.network_err);
                                }
                            });
                        }
                    });
                }
            });
            etConsigneeName.setText(addressBean.full_name);
            etPhone.setText(addressBean.phone);
            String builder = addressBean.province +
                    " " +
                    addressBean.city +
                    " " +
                    addressBean.town +
                    " " +
                    addressBean.area;
            tvAddress.setText(builder);
//            provinceId = addressBean.province_id;
//            cityId = addressBean.city_id;
//            townId = addressBean.town_id;
            etAddressDetails.setText(addressBean.street_address);
//            etZipCode.setText(addressBean.);
            if (!addressBean.is_default) {
//                isdefault = false;
                switchCompat.setChecked(false);
            } else {
//                isdefault = true;
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

    @OnClick({R.id.tv_address, R.id.btn_commit, R.id.switchCompat})
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
                            isAddressSelected=true;
                        }
                    });
                } else {
                    addressSelectFragment.show(activity.getFragmentManager(), AddressSelectFragment.class.getSimpleName());
                }
                break;
//            case R.id.switchCompat:
//                点击后设置为相反状态
//                if (switchCompat.isChecked()) {
//                    switchCompat.setChecked(false);
//                } else {
//                    switchCompat.setChecked(true);
//                }
//                break;
            case R.id.btn_commit:
                commitConsigneeAddress();
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
            ToastUtils.showInfo(R.string.name_is_empty);
            return;
        }

        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showInfo(R.string.phone_is_empty);
            return;
        }

        if (!Util.isMobileNO(etPhone.getText().toString())) {
            ToastUtils.showInfo(R.string.phone_is_err);
            return;
        }

        if (TextUtils.equals(tvAddress.getText(), getResources().getString(R.string.please_select_address))) {
            ToastUtils.showInfo(R.string.please_select_address);
            return;
        }

        String addressDetail = etAddressDetails.getText().toString().trim();
        if (TextUtils.isEmpty(addressDetail)) {
            ToastUtils.showInfo(R.string.address_details_is_empty);
            return;
        }

        if (isAddressSelected){
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
        }else {
            if (addressBean!=null){
//                provinceId=addressBean.;
//                cityId=addressBean.city_id;
//                countyId=addressBean.county_id;
//                townId=addressBean.town_id;
            }else {
                ToastUtils.showError(R.string.network_err);
            }
        }
//
//        String id;
//        if (addressBean == null) {
//            id = "";
//        } else {
//            id = addressBean._id;
//        }
//
        HashMap<String, String> params = ClientParamsAPI.getCommitAddressParams(consigneeName, phone, provinceId, cityId, countyId, townId, addressDetail, etZipCode.getText().toString(), switchCompat.isChecked());
        HttpRequest.sendRequest(HttpRequest.POST,URL.COMMIT_ADDRESS,params, new HttpRequestCallback(){
            @Override
            public void onStart() {
                if (!activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                CommitConsigneeData commitConsigneeData = JsonUtil.fromJson(json, CommitConsigneeData.class);
                if (commitConsigneeData.success) {
                    ToastUtils.showSuccess(commitConsigneeData.status.message);
//                    Intent intent = new Intent();
//                    intent.putExtra("address", 1);
//                    setResult(DataConstants.RESULTCODE_ADDNEWADDRESS, intent);
                    finish();
                } else {
                    ToastUtils.showError(commitConsigneeData.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }
}

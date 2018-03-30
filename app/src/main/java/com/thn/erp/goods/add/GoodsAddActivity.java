package com.thn.erp.goods.add;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.NetReadyHandler;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;
import com.stephen.taihuoniaolibrary.utils.THNToastUtil;
import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.AppApplication;
import com.thn.erp.Constants;
import com.thn.erp.R;
import com.thn.erp.album.PicturePickerUtils;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.ImpTopbarOnClickListener;
import com.thn.erp.common.OnRecyclerViewItemClickListener;
import com.thn.erp.common.RequestCode;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.utils.FileUtil;
import com.thn.erp.utils.ImageUtils;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.PopupWindowUtil;
import com.thn.erp.view.common.LinearLayoutCustomerAddArrowView;
import com.thn.erp.view.common.LinearLayoutCustomerAddSwitchView;
import com.thn.erp.view.common.PublicTopBar;
import com.yanzhenjie.permission.AndPermission;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/26 22:33
 * Email: 895745843@qq.com
 */

public class GoodsAddActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.layoutItemView1)
    LinearLayoutCustomerAddArrowView layoutItemView1;
    @BindView(R.id.layoutItemView2)
    LinearLayoutCustomerAddArrowView layoutItemView2;
    @BindView(R.id.layoutItemView3)
    LinearLayoutCustomerAddArrowView layoutItemView3;
    @BindView(R.id.layoutItemView4)
    LinearLayoutCustomerAddArrowView layoutItemView4;
    @BindView(R.id.layoutItemView5)
    LinearLayoutCustomerAddArrowView layoutItemView5;
    @BindView(R.id.layoutItemView6)
    LinearLayoutCustomerAddArrowView layoutItemView6;
    @BindView(R.id.layoutItemView7)
    LinearLayoutCustomerAddArrowView layoutItemView7;
    @BindView(R.id.layoutItemView8)
    LinearLayoutCustomerAddArrowView layoutItemView8;
    @BindView(R.id.layoutItemView9)
    LinearLayoutCustomerAddSwitchView layoutItemView9;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;

    private GoodsAddRecyclerViewAdapter mGoodsAddRecyclerViewAdapter;
    private THNWaittingDialog dialog;
    private File mCurrentPhotoFile;
    private static final int REQUEST_NICK_NAME = 3;
    private static final int REQUEST_SIGNATURE = 4;
    private static final int SECRET = 0;
    private static final int MAN = 1;
    private static final int WOMAN = 2;
    private Bitmap bitmap;
    private LayoutInflater layoutInflater;
    private List<String> coverIds = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_add;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        initTopbar();
        initRecyclerView();
        initLinearLayaout();
        dialog = new THNWaittingDialog(this);
    }

    private void initLinearLayaout() {
        String[] goodsKey1 = getResources().getStringArray(R.array.GoodsKey1);
        layoutItemView1.setInitKeyAndHint(goodsKey1[0], mOnClickListener);
        layoutItemView2.setInitKeyAndHint(goodsKey1[1], mOnClickListener);
        layoutItemView3.setInitKeyAndHint(goodsKey1[2], mOnClickListener);
        String[] goodsKey2 = getResources().getStringArray(R.array.GoodsKey2);
        layoutItemView4.setInitKeyAndHint(goodsKey2[0], mOnClickListener);
        layoutItemView5.setInitKeyAndHint(goodsKey2[1], mOnClickListener);
        layoutItemView6.setInitKeyAndHint(goodsKey2[2], mOnClickListener);
        layoutItemView7.setInitKeyAndHint(goodsKey2[3], mOnClickListener);
        layoutItemView8.setInitKeyAndHint(goodsKey2[4], mOnClickListener);
        layoutItemView9.setInitKeyAndHint("是否上架", null);

        layoutInflater = LayoutInflater.from(this);
        String[] mVals = {"新品上架", "热卖推荐", "清仓优惠"};
        idFlowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) layoutInflater.inflate(R.layout.layout_flowlayout_tv, idFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        idFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                LogUtil.e("choose: " + selectPosSet.toString());
//                getActivity().setTitle("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        mGoodsAddRecyclerViewAdapter = new GoodsAddRecyclerViewAdapter(this, new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        PopupWindowUtil.show(GoodsAddActivity.this, initPopView(R.layout.popup_upload_avatar, "添加商品"));
                        break;
                }
            }
        });
        recyclerView1.setAdapter(mGoodsAddRecyclerViewAdapter);
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("添加商品", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarRightTextView("保存", getResources().getColor(R.color.THN_color_fontColor_assist));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {
        switch (position) {
            case ImpTopbarOnClickListener.RIGHT:
                if (coverIds.size() > 0 && layoutItemView1.getValue() != null && layoutItemView4.getValue() != null) {
                    uploadGoods(layoutItemView1.getValue().toString(), coverIds.get(0), layoutItemView4.getValue().toString());
                }
                break;
        }
    }


    private View initPopView(int layout, String title) {
        View view = View.inflate(GoodsAddActivity.this, layout, null);
        ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        View iv_take_photo = view.findViewById(R.id.tv_take_photo);
        View iv_take_album = view.findViewById(R.id.tv_album);
        View iv_close = view.findViewById(R.id.tv_cancel);
        iv_take_photo.setOnClickListener(onClickListener);
        iv_take_album.setOnClickListener(onClickListener);
        iv_close.setOnClickListener(onClickListener);
        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_take_photo:
                    PopupWindowUtil.dismiss();
                    if (AndPermission.hasPermission(activity, Manifest.permission.CAMERA)) {
                        openCamera();
                    } else {
                        // 申请权限。
                        AndPermission.with(activity)
                                .requestCode(Constants.REQUEST_PERMISSION_CODE)
                                .permission(Manifest.permission.CAMERA)
                                .send();
                    }
                    break;
                case R.id.tv_album:
                    PopupWindowUtil.dismiss();
                    if (AndPermission.hasPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ImageUtils.getImageFromAlbum(activity, 1);
                    } else {
                        // 申请权限。
                        AndPermission.with(activity)
                                .requestCode(Constants.REQUEST_PERMISSION_CODE)
                                .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .send();
                    }
                    break;
                case R.id.tv_cancel:
                default:
                    PopupWindowUtil.dismiss();
                    break;
            }
        }
    };

    protected void openCamera() {
        mCurrentPhotoFile = ImageUtils.getDefaultFile();
        if (null == mCurrentPhotoFile) return;
        ImageUtils.getImageFromCamera(activity, ImageUtils.getUriForFile(mCurrentPhotoFile));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CODE_PICK_IMAGE:
                    List<Uri> mSelected = PicturePickerUtils.obtainResult(data);
                    if (mSelected == null || mSelected.size() == 0) {
                        return;
                    }
                    toCropActivity(mSelected.get(0));
//                    mGoodsAddRecyclerViewAdapter.addList(mSelected.get(0));
                    break;
                case Constants.REQUEST_CODE_CAPTURE_CAMERA:
                    if (null == mCurrentPhotoFile) return;
                    toCropActivity(ImageUtils.getUriForFile(mCurrentPhotoFile));
//                    mGoodsAddRecyclerViewAdapter.addList(ImageUtils.getUriForFile(mCurrentPhotoFile));
                    break;
                case com.thn.erp.common.RequestCode.CODE_GOODS_NAME: layoutItemView1.setValue(data.getStringExtra(getClass().getSimpleName()));
                    break;
                case com.thn.erp.common.RequestCode.CODE_GOODS_CATEGORY: layoutItemView2.setValue(data.getStringExtra(getClass().getSimpleName()));
                    break;
                case com.thn.erp.common.RequestCode.CODE_GOODS_INTRODUCTION:  layoutItemView3.setValue(data.getStringExtra(getClass().getSimpleName()));
                    break;
                case com.thn.erp.common.RequestCode.CODE_GOODS_PRICE:  layoutItemView4.setValue(data.getStringExtra(getClass().getSimpleName()));
                    break;
                case com.thn.erp.common.RequestCode.CODE_GOODS_SPECIFICATION: layoutItemView5.setValue(data.getStringExtra(getClass().getSimpleName()));
                    break;
                case com.thn.erp.common.RequestCode.CODE_GOODS_CODE: layoutItemView6.setValue(data.getStringExtra(getClass().getSimpleName()));
                    break;
                case com.thn.erp.common.RequestCode.CODE_GOODS_UNIT: layoutItemView7.setValue(data.getStringExtra(getClass().getSimpleName()));
                    break;
                case com.thn.erp.common.RequestCode.CODE_GOODS_KEYWORDS: layoutItemView8.setValue(data.getStringExtra(getClass().getSimpleName()));
                    break;
            }
        }
    }


    private void toCropActivity(Uri uri) {
        ImageCropActivity.setOnClipCompleteListener(new ImageCropActivity.OnClipCompleteListener() {
            @Override
            public void onClipComplete(final Bitmap bitmap) {
//                custom_user_avatar.getAvatarIV().setImageBitmap(bitmap);
                String imgPath = absolutePath + File.separator + "elingze-adminlte" + File.separator + "products" + File.separator + System.currentTimeMillis() + ".png";
                boolean bitmapToFile = FileUtil.bitmapToFile(bitmap, imgPath);
                if (bitmapToFile) {
                    getQiNiuToken(imgPath, new UpLoadCallBack() {
                        @Override
                        public void complete() {
                            mGoodsAddRecyclerViewAdapter.addList(bitmap);
                        }
                    });
                }
            }
        });
        Intent intent = new Intent(activity, ImageCropActivity.class);
        intent.putExtra(ImageCropActivity.class.getSimpleName(), uri);
        intent.putExtra(ImageCropActivity.class.getName(), TAG);
        startActivity(intent);
    }

    private void getQiNiuToken(final String imgPath, final UpLoadCallBack globalCallBack) {

        HashMap<String, String> params = ClientParamsAPI.generateCommonParams();
        HttpRequest.sendRequest(HttpRequest.GET, URL.QINIU_TOKEN, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
                QiniuTokenData customerBean = JsonUtil.fromJson(json, QiniuTokenData.class);
                if (customerBean.getSuccess()) {
                    String upToken = customerBean.getData().getUp_token();
                    String userId = customerBean.getData().getUser_id();
                    String directoryId = customerBean.getData().getDirectory_id();
                    uploadFile(imgPath, upToken,new String[]{userId, directoryId}, globalCallBack);
                } else {
                    ToastUtils.showError(customerBean.getStatus().getMessage());
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    private void uploadFile(String imgPath, String token, String[] strings, final UpLoadCallBack upLoadCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("x:user_id",strings[0] );
        map.put("x:directory_id", strings[1]);
        UploadOptions uploadOptions = new UploadOptions(map, "image", false, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {

            }
        }, new UpCancellationSignal() {
            @Override
            public boolean isCancelled() {
                return false;
            }
        }, new NetReadyHandler() {
            @Override
            public void waitReady() {

            }
        });
        AppApplication.getUploadManager().put(imgPath, null, token, new UpCompletionHandler() {
            @Override
            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                if (responseInfo.isOK()) {
                    LogUtil.e("qiniu", "Upload Success");
                    upLoadCallBack.complete();
                    try {
                        JSONArray ids = jsonObject.getJSONArray("ids");
                        String string = ids.getString(0);
                        coverIds.add(string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LogUtil.e("qiniu", "Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }
        }, uploadOptions);
    }

    private String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();

    interface UpLoadCallBack {
        void complete();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == layoutItemView1) {
                Intent intent = new Intent(GoodsAddActivity.this, GoodsNameEditActivity.class);
                startActivityForResult(intent, RequestCode.CODE_GOODS_NAME);
            } else if (view == layoutItemView2) {
                Intent intent = new Intent(GoodsAddActivity.this, GoodsCategoryEditActivity.class);
                startActivityForResult(intent, RequestCode.CODE_GOODS_CATEGORY);
            }else if (view == layoutItemView3) {
                Intent intent = new Intent(GoodsAddActivity.this, GoodsIntroductionEditActivity.class);
                startActivityForResult(intent, RequestCode.CODE_GOODS_INTRODUCTION);
            }else if (view == layoutItemView4) {
                Intent intent = new Intent(GoodsAddActivity.this, GoodsPriceEditActivity.class);
                startActivityForResult(intent, RequestCode.CODE_GOODS_PRICE);
            }else if (view == layoutItemView5) {
                Intent intent = new Intent(GoodsAddActivity.this, GoodsSpecificationEditActivity.class);
                startActivityForResult(intent, RequestCode.CODE_GOODS_SPECIFICATION);
            }else if (view == layoutItemView6) {
                Intent intent = new Intent(GoodsAddActivity.this, GoodsCodeEditActivity.class);
                startActivityForResult(intent, RequestCode.CODE_GOODS_CODE);
            }else if (view == layoutItemView7) {
                Intent intent = new Intent(GoodsAddActivity.this, GoodsUnitEditActivity.class);
                startActivityForResult(intent, RequestCode.CODE_GOODS_UNIT);
            }else if (view == layoutItemView8) {
                Intent intent = new Intent(GoodsAddActivity.this, GoodsKeywordsEditActivity.class);
                startActivityForResult(intent, RequestCode.CODE_GOODS_KEYWORDS);
            }
        }
    };

    private void uploadGoods(String name, String coverId, String price) {
        HashMap<String, String> params = ClientParamsAPI.generateCommonParams();
        params.put("name",name );
        params.put("cover_id", coverId);
        params.put("price", price);
        HttpRequest.sendRequest(HttpRequest.POST, URL.PRODUCT_ADD, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
                AddGoodsData customerBean = JsonUtil.fromJson(json, AddGoodsData.class);
                if (customerBean.getSuccess()) {
                    THNToastUtil.showSuccess("上传成功");
                    GoodsAddActivity.this.finish();
                } else {
                    ToastUtils.showError(customerBean.getStatus().getMessage());
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }
//    private String[] GOODS = {"西瓜", "苹果", "香蕉", "梨子", "辣条", "啤酒", "泡面", "饼干", "鸡爪"};
}

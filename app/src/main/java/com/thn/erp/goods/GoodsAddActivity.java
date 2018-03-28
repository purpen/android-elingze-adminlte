package com.thn.erp.goods;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.Constants;
import com.thn.erp.R;
import com.thn.erp.album.PicturePickerUtils;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.ImpTopbarOnClickListener;
import com.thn.erp.common.OnRecyclerViewItemClickListener;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.utils.ImageUtils;
import com.thn.erp.view.PopupWindowUtil;
import com.thn.erp.view.common.LinearLayoutCustomerAddArrowView;
import com.thn.erp.view.common.LinearLayoutCustomerAddSwitchView;
import com.thn.erp.view.common.PublicTopBar;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.List;

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

    private GoodsAddRecyclerViewAdapter mGoodsAddRecyclerViewAdapter;
    private THNWaittingDialog dialog;
    private File mCurrentPhotoFile;
    private static final int REQUEST_NICK_NAME = 3;
    private static final int REQUEST_SIGNATURE = 4;
    private static final int SECRET = 0;
    private static final int MAN = 1;
    private static final int WOMAN = 2;
    private Bitmap bitmap;

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
        dialog = new THNWaittingDialog(this);
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
        if (null==mCurrentPhotoFile) return;
        ImageUtils.getImageFromCamera(activity, ImageUtils.getUriForFile(mCurrentPhotoFile));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
//                case REQUEST_NICK_NAME:
//                    user = (User) data.getSerializableExtra(User.class.getSimpleName());
//                    custom_nick_name.setTvArrowLeftStyle(true, user.nickname, R.color.color_333);
//                    break;
//                case REQUEST_SIGNATURE:
//                    user = (User) data.getSerializableExtra(User.class.getSimpleName());
////                    custom_signature.setTvArrowLeftStyle(true,user.summary,R.color.color_333);
//                    setLabelSignatrue();
//                    break;
                case Constants.REQUEST_CODE_PICK_IMAGE:
                    List<Uri> mSelected = PicturePickerUtils.obtainResult(data);
                    if (mSelected == null || mSelected.size() == 0) {
                        return;
                    }
                    toCropActivity(mSelected.get(0));
//                    mGoodsAddRecyclerViewAdapter.addList(mSelected.get(0));
                    break;
                case Constants.REQUEST_CODE_CAPTURE_CAMERA:
                    if (null==mCurrentPhotoFile) return;
                    toCropActivity(ImageUtils.getUriForFile(mCurrentPhotoFile));
//                    mGoodsAddRecyclerViewAdapter.addList(ImageUtils.getUriForFile(mCurrentPhotoFile));
                    break;
            }
        }
    }


    private void toCropActivity(Uri uri) {
        ImageCropActivity.setOnClipCompleteListener(new ImageCropActivity.OnClipCompleteListener() {
            @Override
            public void onClipComplete(Bitmap bitmap) {
//                custom_user_avatar.getAvatarIV().setImageBitmap(bitmap);
                mGoodsAddRecyclerViewAdapter.addList(bitmap);
            }
        });
        Intent intent = new Intent(activity, ImageCropActivity.class);
        intent.putExtra(ImageCropActivity.class.getSimpleName(), uri);
        intent.putExtra(ImageCropActivity.class.getName(), TAG);
        startActivity(intent);
    }

    private void getQiNiuToken(){
//        HttpRequest.
    }
}

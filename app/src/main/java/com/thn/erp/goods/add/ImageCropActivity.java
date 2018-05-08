package com.thn.erp.goods.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.view.ImageCrop.ClipSquareImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lilin
 * created at 2016/5/18 18:01
 */
public class ImageCropActivity extends BaseActivity {
    /**
     * 消息
     */
    public static class Message {
        public final Bitmap bitmap;

        public Message(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }

    @BindView(R.id.csiv)
    ClipSquareImageView csiv;
    @BindView(R.id.bt_cancel)
    Button bt_cancel;
    @BindView(R.id.bt_clip)
    Button btClip;
    @BindView(R.id.rl)
    RelativeLayout rl;

    private Uri uri;

    Button bt_clip;
    private String page;
    private String zoneId;
    private THNWaittingDialog dialog;


    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(ImageCropActivity.class.getSimpleName())) {
            uri = intent.getParcelableExtra(ImageCropActivity.class.getSimpleName());
        }

        if (intent.hasExtra(ImageCropActivity.class.getName())) { //区分界面
            page = intent.getStringExtra(ImageCropActivity.class.getName());
//            zoneId = intent.getStringExtra(ZoneManagementActivity.class.getSimpleName());
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_image_crop;
    }

    @Override
    protected void initView() {
        if (uri == null) return;
        dialog = new THNWaittingDialog(this);
        THNGlideUtil.displayImage(uri, csiv);
    }


    @OnClick({R.id.bt_cancel, R.id.bt_clip})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cancel:
//                NetworkManager.getInstance().cancel(URL.UPLOAD_BG_URL);
                finish();
                break;
            case R.id.bt_clip:
                Bitmap bitmap = csiv.clip();
                EventBus.getDefault().post(new Message(bitmap));
                finish();
                /*
                if (TextUtils.isEmpty(page)) {//认证图片
                    if (listener != null) {
                        listener.onClipComplete(bitmap);
                        finish();
                    }
                } else if (TextUtils.equals(EditUserInfoActivity.class.getSimpleName(), page) || TextUtils.equals(CompleteUserInfoActivity.class.getSimpleName(), page)) {//上传头像
                    uploadUserAvatar(bitmap);
                } else if (TextUtils.equals(ZoneManagementActivity.class.getSimpleName(), page)) { //上传地盘封面
                    uploadZoneCover(zoneId, bitmap);
                } else if (TextUtils.equals(ZoneBaseInfoActivity.class.getSimpleName(), page)) {
                    uploadZoneLogo(zoneId, bitmap);
                } else {//上传背景封面
                    uploadFile(bitmap);
                }*/
                break;
        }
    }

    /**
     * 上传地盘封面
     */
    private void uploadZoneCover(String id, Bitmap bitmap) {
//        if (bitmap == null) return;
//        if (dialog != null && !activity.isFinishing()) dialog.show();
//        String imgStr = Util.saveBitmap2Base64Str(bitmap, 550);
//        bitmap.recycle();
//        HashMap params = ClientDiscoverAPI.getZoneCoverParams(id, imgStr, "1");
//        HttpRequest.post(params, URL.ZONE_ADD_COVER, new GlobalDataCallBack() {
//            @Override
//            public void onStart() {
//                setViewEnable(false);
//            }
//
//            @Override
//            public void onSuccess(String json) {
//                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//                HttpResponse httpResponse = JsonUtil.fromJson(json, HttpResponse.class);
//                setViewEnable(true);
//                if (httpResponse.isSuccess()) {
//                    ZoneUploadCoverBean response = JsonUtil.fromJson(json, new TypeToken<HttpResponse<ZoneUploadCoverBean>>() {
//                    });
//                    ToastUtils.showSuccess(httpResponse.getMessage());
//                    finish();
//                    return;
//                }
//                ToastUtils.showError(httpResponse.getMessage());
//            }
//
//            @Override
//            public void onFailure(String error) {
//                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//                setViewEnable(true);
//                ToastUtils.showError(R.string.network_err);
//            }
//        });
    }


    private void uploadFile(Bitmap bitmap) { //换个人中心背景图
        if (bitmap == null) return;
        if (dialog != null && !activity.isFinishing()) dialog.show();
//        String imgStr = Util.saveBitmap2Base64Str(bitmap, 512);
//        bitmap.recycle();
//        try {
//            HashMap<String, String> params = ClientDiscoverAPI.getuploadBgImgRequestParams(imgStr);
//            HttpRequest.post(params, URL.UPLOAD_BG_URL, new GlobalDataCallBack() {
//                @Override
//                public void onStart() {
//                    setViewEnable(false);
//                }
//
//                @Override
//                public void onSuccess(String json) {
//                    if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//                    HttpResponse response = JsonUtil.fromJson(json, HttpResponse.class);
//                    setViewEnable(true);
//                    if (response.isSuccess()) {
//                        ToastUtils.showSuccess("背景图上传成功");
//                        activity.finish();
//                        return;
//                    }
//                    ToastUtils.showError(response.getMessage());
//                }
//
//                @Override
//                public void onFailure(String error) {
//                    setViewEnable(true);
//                    if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//                    ToastUtils.showError(R.string.network_err);
//                }
//            });
//        } catch (Exception e) {
//            bitmap.recycle();
//            e.printStackTrace();
//        } finally {
//            setViewEnable(true);
//            if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//        }

    }

    private void setViewEnable(boolean enable) {
        bt_clip.setEnabled(enable);
        csiv.setEnabled(enable);
    }

    private void uploadUserAvatar(final Bitmap bitmap) {
        if (bitmap == null) return;
        String type = "3"; //上传头像
        if (dialog != null && !activity.isFinishing()) dialog.show();
//        String imgStr = Util.saveBitmap2Base64Str(bitmap, 200);
//        try {
//            HashMap<String, String> params = ClientDiscoverAPI.getuploadImgRequestParams(imgStr, type);
//            HttpRequest.post(params, URL.UPLOAD_IMG_URL, new GlobalDataCallBack() {
//                @Override
//                public void onStart() {
//                    setViewEnable(false);
//                }
//
//                @Override
//                public void onSuccess(String json) {
//                    if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//                    HttpResponse response = JsonUtil.fromJson(json, HttpResponse.class);
//                    setViewEnable(true);
//                    if (response.isSuccess()) {
//                        ToastUtils.showSuccess("头像上传成功");
//                        if (listener != null) {
//                            listener.onClipComplete(bitmap);
//                        }
//                        finish();
//                        return;
//                    }
//                    ToastUtils.showError(response.getMessage());
//                }
//
//                @Override
//                public void onFailure(String error) {
//                    if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//                    setViewEnable(true);
//                    ToastUtils.showError(R.string.network_err);
//                }
//            });
//        } catch (Exception e) {
//            bitmap.recycle();
//            e.printStackTrace();
//        } finally {
//            setViewEnable(true);
//            if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//        }
    }

    //上传地盘logo
    private void uploadZoneLogo(String id, final Bitmap bitmap) {
        if (bitmap == null) return;
//        try {
//            if (dialog != null && !activity.isFinishing()) dialog.show();
//            String imgStr = Util.saveBitmap2Base64Str(bitmap, 200);
//            HashMap<String, String> params = new HashMap<>();
//            params.put("id", id);
//            params.put("avatar_tmp", imgStr);
//            HttpRequest.post(params, URL.SCENE_SCENE_SAVE_URL, new GlobalDataCallBack() {
//                @Override
//                public void onStart() {
//                    setViewEnable(false);
//                }
//
//                @Override
//                public void onSuccess(String json) {
//                    if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//                    HttpResponse response = JsonUtil.fromJson(json, HttpResponse.class);
//                    setViewEnable(true);
//                    if (response.isSuccess()) {
//                        ToastUtils.showSuccess("地盘logo上传成功");
//                        if (listener != null) {
//                            listener.onClipComplete(bitmap);
//                        }
//                        finish();
//                        return;
//                    }
//                    ToastUtils.showError(response.getMessage());
//                }
//
//                @Override
//                public void onFailure(String error) {
//                    if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//                    setViewEnable(true);
//                    ToastUtils.showError(R.string.network_err);
//                }
//            });
//        } catch (Exception e) {
//            bitmap.recycle();
//            e.printStackTrace();
//        } finally {
//            setViewEnable(true);
//            if (dialog != null && !activity.isFinishing()) dialog.dismiss();
//        }
    }

}


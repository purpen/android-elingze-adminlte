/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thn.erp.zxing.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.Result;
import com.thn.basemodule.tools.BaseModuleContext;
import com.thn.erp.R;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.SPUtil;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.dialog.DefaultDialog;
import com.thn.erp.view.dialog.IDialogListener;
import com.thn.erp.zxing.camera.CameraManager;
import com.thn.erp.zxing.utils.BeepManager;
import com.thn.erp.zxing.utils.CaptureActivityHandler;
import com.thn.erp.zxing.utils.InactivityTimer;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;


/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivityZxing extends ZxingBaseActivity implements
        SurfaceHolder.Callback {
    private static final String INFO_TYPE_QJ = "10";
    private static final int REQUEST_CODE = 100;
    private static final String INFO_TYPE_CJ = "11";
    private static final String INFO_TYPE_CP = "1";
    private static final String INFO_TYPE_USER = "13";
    private static final String TAG = CaptureActivityZxing.class.getSimpleName();
    private static final int REQUEST_CODE_SETTING = 12365556;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private Button mFlash;

    private Rect mCropRect = null;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private boolean isHasSurface = false;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_qr_scan);
        ((CustomHeadView) findViewById(R.id.custom_head)).setHeadCenterTxtShow(true, "扫一扫");
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        TextView tv_bar = (TextView) findViewById(R.id.tv_bar);
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        ImageView scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        mFlash = (Button) findViewById(R.id.capture_flash);
        mFlash.setOnClickListener(this);
        tv_bar.setOnClickListener(this);
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
//        cameraManager = new CameraManager(getApplication());
//
//        handler = null;
//
//        if (isHasSurface) {
//            // The activity was paused but not stopped, so the surface still
//            // exists. Therefore
//            // surfaceCreated() won't be called, so init the camera here.
//            initCamera(scanPreview.getHolder());
//        } else {
//            // Install the callback and wait for surfaceCreated() to init the
//            // camera.
//            scanPreview.getHolder().addCallback(this);
//        }
//
//        inactivityTimer.onResume();
        if (AndPermission.hasPermission(this, Manifest.permission.CAMERA)){
            initCamera();
        } else {
            AndPermission.with(this)
                    .requestCode(REQUEST_CODE)
                    .permission(Manifest.permission.CAMERA)
                    .send();
        }
    }

    public void initCamera(){
        cameraManager = new CameraManager(getApplication());
        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，第一个参数是当前Acitivity/Fragment，回调方法写在当前Activity/Framgent。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    // 成功回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionYes(REQUEST_CODE)
    private void getRequestYes(List<String> grantedPermissions) {
        initCamera();
//        onResume();
    }

    // 失败回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionNo(REQUEST_CODE)
    private void getPhoneStatusNo(List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this,REQUEST_CODE_SETTING).show();
        }else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        if (cameraManager!=null) cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        cameraManager.stopPreview();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
//            Log.e(TAG,
//                    "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(final Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        handleText(rawResult.getText());
    }

    private void handleText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (text.contains("taihuoniao.com")) {
            goTHNInternalPage(text);
        } else if (text.startsWith("http")) {//扫描的不是咱们网站的二维码
            Uri uri = Uri.parse(text);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else { // 返回文本
            Intent intent = new Intent();
            intent.putExtra("text", text);
            setResult(-1, intent);
            CaptureActivityZxing.this.finish();
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(CaptureActivityZxing.this, cameraManager);
            }

            initCrop();
        } catch (IOException ioe) {
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        new DefaultDialog(CaptureActivityZxing.this, BaseModuleContext.getString(R.string.app_name), BaseModuleContext.getString(R.string.hint_dialog_open_camera_fail_content), BaseModuleContext.getStringArray(R.array.text_dialog_button), new IDialogListener() {
            @Override
            public void click(View view, int index) {
                finish();
            }
        });

    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_flash:
                light();
                break;
            case R.id.tv_bar:
//                startActivity(new Intent(CaptureActivityZxing.this, MyBarCodeActivity.class));
                break;
            default:
                break;
        }
    }

    private boolean flag;

    protected void light() {
        if (flag) {
            flag = false;
            // 开闪光灯
            cameraManager.openLight();
            mFlash.setBackgroundResource(R.mipmap.flash_open);
        } else {
            flag = true;
            // 关闪光灯
            cameraManager.offLight();
            mFlash.setBackgroundResource(R.mipmap.flash_default);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    /**
     * 处理THN 内部链接
     * @param text
     */
    private void goTHNInternalPage(String text) {
        Intent intent;
        if (!text.contains("?")) {//是咱们的域名但没参数
            return;
        }
        if (text.contains("infoType") && text.contains("infoId")) { //能正确获取到参数
            String params = text.substring(text.indexOf("?") + 1, text.length());
            String[] args = params.split("&");

            HashMap<String, String> map = new HashMap<>();
            for (String str: args ) {
                if((!TextUtils.isEmpty(str)) || str.contains("=")){
                    map.put(str.split("=")[0],str.split("=")[1]);
                }
            }

            String infoType = map.get("infoType");
            String infoId = map.get("infoId");
            String storage_id = map.get("storage_id");

            //获取推广码 字段referral_code
            String referral_code = map.get("referral_code");
            if (!TextUtils.isEmpty(referral_code)) {
                SPUtil.write("referral_code", referral_code);
            }
//                LogUtil.e("text", String.format("infoType=%s;infoId=%s", infoType, infoId));
            if (TextUtils.isEmpty(infoType) || TextUtils.isEmpty(infoId)) {
                LogUtil.e("TextUtils.isEmpty(infoType) || TextUtils.isEmpty(infoId)", "参数为空");
                return;
            }
//            if (TextUtils.equals(INFO_TYPE_USER, infoType)) {//跳转个人中心
//                intent=new Intent(CaptureActivityZxing.this, UserCenterActivity.class);
//                intent.putExtra(FocusActivity.USER_ID_EXTRA, Long.valueOf(infoId));
//                startActivity(intent);
//            } else if (TextUtils.equals(INFO_TYPE_QJ, infoType)) {//跳转地盘详情
//                // 跳入地盘
//                intent = new Intent(CaptureActivityZxing.this, ZoneDetailActivity.class);
//                intent.putExtra("id",infoId);
//                intent.putExtra("title","地盘详情");
//                startActivity(intent);
//            } else if (TextUtils.equals(INFO_TYPE_CJ, infoType)) {//跳转情境详情
//                intent = new Intent(CaptureActivityZxing.this, QJDetailActivity2.class);
//                intent.putExtra("id",infoId);
//                startActivity(intent);
//            } else if (TextUtils.equals(INFO_TYPE_CP, infoType)) {//跳转产品详情
//                intent=new Intent(CaptureActivityZxing.this, BuyGoodsDetailsActivity.class);
//                intent.putExtra("id",infoId);
//                intent.putExtra("storage_id",storage_id);
//                startActivity(intent);
//            }
        }
    }
}
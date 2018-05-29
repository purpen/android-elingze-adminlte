package com.thn.erp.more;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.MainActivity;
import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.base.BaseUltimateRecyclerView;
import com.thn.erp.common.constant.ExtraKey;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.more.adapter.StockProductListAdapter;
import com.thn.erp.more.bean.PrepareExportStockBean;
import com.thn.erp.utils.DialogHelp;
import com.thn.erp.view.common.PublicTopBar;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/4/17 11:03
 * Email: 895745843@qq.com
 */

public class ScanOrderingActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    private static final String TAG = ScanOrderingActivity.class.getSimpleName();
    @BindView(R.id.textView_more_can_export_stock_total)
    TextView textViewMoreCanExportStockTotal;
    @BindView(R.id.textView_more_can_export_stock_confirm)
    TextView textViewMoreCanExportStockConfirm;
    @BindView(R.id.textView_more_scan_export_list_title)
    TextView textViewMoreScanExportListTitle;
    private PrepareExportStockBean bean;
    private PublicTopBar publicTopBar;
    private BaseUltimateRecyclerView recyclerview1;
    private StockProductListAdapter mStockProductListAdapter;
    private List<Object> mObjects;
    private CaptureFragment captureFragment;
    private AlertDialog confirmDialog;
    private ProgressDialog waitDialog;

    @Override

    protected int getLayout() {
        return R.layout.activity_more_scan_export_stock;
    }

    @Override
    protected void getIntentData() {
        Parcelable parcelableExtra = getIntent().getParcelableExtra(ExtraKey.PARCElABLE);
        if (parcelableExtra instanceof PrepareExportStockBean) {
            bean = (PrepareExportStockBean) parcelableExtra;
        }
    }

    @Override
    protected void initView() {
        initTopBar();
        initRecyclerview();
    }

    private void initRecyclerview() {
        recyclerview1 = (BaseUltimateRecyclerView) findViewById(R.id.recyclerview1);
        mObjects = new ArrayList<>();
        mStockProductListAdapter = new StockProductListAdapter(mObjects);
        setLinearLayoutManagerVertical(recyclerview1);
        recyclerview1.setAdapter(mStockProductListAdapter);
        textViewMoreScanExportListTitle.setText("待下单商品清单");
        textViewMoreCanExportStockConfirm.setText("确认下单");
    }

    private void initTopBar() {
        publicTopBar = (PublicTopBar) findViewById(R.id.publicTopBar);
        publicTopBar.setTopBarCenterTextView("选择客户下单", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    private void initCamera() {
        captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera); // 为二维码扫描界面设置定制化界面
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout_container, captureFragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            List<Object> objects = new ArrayList<>();
            objects.add(result);
            mStockProductListAdapter.addList(objects);
            ToastUtil.showSuccess("扫码成功");
            textViewMoreCanExportStockTotal.setText("数量：" + String.valueOf(mObjects.size()));
            resendScanRequest();
        }

        @Override
        public void onAnalyzeFailed() {
            ToastUtil.showError("扫码失败，重新扫描");
            resendScanRequest();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (AndPermission.hasPermission(this, Manifest.permission.CAMERA)) {
            initCamera();
        } else {
            AndPermission.with(this)
                    .requestCode(REQUEST_CODE)
                    .permission(Manifest.permission.CAMERA)
                    .send();
        }
    }

    private static final int REQUEST_CODE = 100;
    private static final int REQUEST_CODE_SETTING = 12365556;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，第一个参数是当前Acitivity/Fragment，回调方法写在当前Activity/Framgent。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    // 成功回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionYes(REQUEST_CODE)
    private void getRequestYes(List<String> grantedPermissions) {
        initCamera();
    }

    // 失败回调的方法，用注解即可，里面的数字是请求时的requestCode。
    @PermissionNo(REQUEST_CODE)
    private void getPhoneStatusNo(List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING).show();
        } else {
            finish();
        }
    }

    private void resendScanRequest() {
        Message obtain = Message.obtain();
        obtain.what = R.id.restart_preview;
        captureFragment.getHandler().sendMessageDelayed(obtain, 500);
    }

    @OnClick(R.id.textView_more_can_export_stock_confirm)
    public void onViewClicked() {
        confirmDialog = DialogHelp.getConfirmDialog(ScanOrderingActivity.this, "1. 客户名称：佛山市伟志成诚机械有限公司" + "\n2. 本次出库：商品 1 数量 " + mObjects.size(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmDialog.dismiss();
                waitDialog = DialogHelp.getWaitDialog(ScanOrderingActivity.this, "下单中···");
                waitDialog.show();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waitDialog.cancel();
                        ToastUtil.showSuccess("下单成功");
                        ScanOrderingActivity.this.startActivity(new Intent(ScanOrderingActivity.this, MainActivity.class));
                    }
                }, 3000);
            }
        }, null).create();

        confirmDialog.show();
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }
}

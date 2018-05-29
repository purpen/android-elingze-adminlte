package com.thn.erp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.DataConstants;
import com.thn.erp.overview.bean.CustomMenuBean;
import com.thn.erp.user.LoginRegisterActivity;
import com.thn.basemodule.tools.SPUtil;
import com.thn.erp.view.autoScrollViewpager.ScrollableView;
import com.thn.erp.view.autoScrollViewpager.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author lilin
 * created at 2016/4/18 16:10
 */
public class UserGuideActivity extends BaseActivity {
    @BindView(R.id.scrollableView)
    ScrollableView scrollableView;
    private Intent intent;
    private List<Integer> list;
    private boolean flag = false;
    public static String fromPage;
    private boolean empty;
    private boolean taskRoot;
    private boolean readBool;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_guide;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && TextUtils.equals(Intent.ACTION_MAIN, intent.getAction())) {
                finish();
            }
        }
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        intent = getIntent();
//        if (intent.hasExtra(MineFragment.class.getSimpleName())) {
//            fromPage = intent.getStringExtra(MineFragment.class.getSimpleName());
//        }
        empty = TextUtils.isEmpty(SPUtil.read(DataConstants.GUIDE_TAG));
        taskRoot = isTaskRoot();
    }

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(fromPage)) {
            scrollableView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (empty) {
                        initGuide();
                    } else {
                        if (taskRoot) {
                            goMainPage();
                        }
                    }
                }
            }, DataConstants.GUIDE_INTERVAL);
        } else {
            initGuide();
        }

        initHomeMenuData();
    }

    private void initHomeMenuData() {
        SqliteHelper helper = new SqliteHelper(getApplicationContext());
        //
        long count = helper.getCountOfRecordInTable();
        if (count>0) return;
        int length = menuTitles.length;
        ArrayList<CustomMenuBean> list = new ArrayList<>();
        CustomMenuBean bean;
//        long startTime = System.currentTimeMillis();
        for (int i = 0; i < length; i++) {
            bean = new CustomMenuBean();
            bean.pos = i;
            bean.selected = false;
            bean.iconId = menuIcons[i];
            bean.title = menuTitles[i];
            list.add(bean);
        }
//        LogUtil.e("startTime:"+startTime);
        helper.insert(list);
//        long endTime = System.currentTimeMillis();
//        LogUtil.e("endTime:"+endTime);
//        LogUtil.e("endTime - startTime:"+(endTime-startTime));
    }

    private void initGuide() {
        scrollableView.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        list.add(R.mipmap.guide0);
        list.add(R.mipmap.guide1);
        list.add(R.mipmap.guide2);
        list.add(R.mipmap.guide3);
        scrollableView.setAdapter(new ViewPagerAdapter<>(activity, list));
        SPUtil.write(DataConstants.GUIDE_TAG, DataConstants.GUIDE_TAG);
    }

    private void goMainPage() {
        if (TextUtils.isEmpty(SPUtil.read(Constants.AUTHORIZATION))) {
            startActivity(new Intent(activity, LoginRegisterActivity.class));
        } else {
            startActivity(new Intent(activity, MainActivity.class));
        }
        finish();
    }

    public static final String[] menuTitles = {"经营概况", "销售单", "销售退货单", "进销对比", "采购单", "采购退货单", "商品管理", "人员管理", "客户管理", "供应商管理", "出库单历史", "入库单历史", "库存查询", "入库报表", "出库报表", "物流管理"};
    public static final int[] menuIcons = {
            R.mipmap.icon_menu_status,
            R.mipmap.icon_menu_sale_order,
            R.mipmap.icon_menu_sales_return,
            R.mipmap.icon_menu_import_export_compare,
            R.mipmap.icon_menu_purchase_order,
            R.mipmap.icon_menu_purchase_return,
            R.mipmap.icon_menu_goods_manage,
            R.mipmap.icon_menu_person_manage,
            R.mipmap.icon_menu_customer_manage,
            R.mipmap.icon_menu_supplier_manage,
            R.mipmap.icon_menu_outgoing_history,
            R.mipmap.icon_menu_warehousing_history,
            R.mipmap.icon_menu_inventory_query,
            R.mipmap.icon_menu_warehousing_report,
            R.mipmap.icon_menu_outgoing_report,
            R.mipmap.icon_menu_logistics_manage,
    };
}

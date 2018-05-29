package com.thn.erp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thn.basemodule.tools.StatusBarUtil;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.bean.TabItem;
import com.thn.erp.goods.GoodsFragment;
import com.thn.erp.more.MoreFragment;
import com.thn.erp.overview.OverViewFragment;
import com.thn.erp.sale.SaleFragment;
import com.thn.erp.statistics.StatisticsFragment;
import com.thn.basemodule.tools.LogUtil;
import com.thn.basemodule.tools.ToastUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @BindView(R.id.activity_main_container)
    RelativeLayout container;
    @BindView(R.id.activity_main_fragment_group)
    FrameLayout fragmetnContainer;
    @BindView(R.id.activity_main_bottomlinear)
    LinearLayout bottomLinear;

    @BindView(R.id.image0)
    ImageView image0;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;

    @BindView(R.id.tv_nav0)
    TextView tv_nav0;
    @BindView(R.id.tv_nav1)
    TextView tv_nav1;
    @BindView(R.id.tv_nav3)
    TextView tv_nav3;
    @BindView(R.id.tv_nav2)
    TextView tv_nav2;
    @BindView(R.id.tv_nav4)
    TextView tv_nav4;
//    @Bind(R.id.tv_msg_indicator)TextView tv_msg_indicator;

    private FragmentManager fm;
    private ArrayList<TabItem> tabList;
    private ArrayList<Fragment> fragments;
    private Fragment showFragment;
    public String which = OverViewFragment.class.getSimpleName();
    private Boolean isExit = false;


    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.hasExtra(OverViewFragment.class.getSimpleName())) {
            which = OverViewFragment.class.getSimpleName();
            boolean exit = intent.getBooleanExtra("exit", false);
//            if (exit) {
//                tv_msg_indicator.setVisibility(View.GONE);
//            }
        } else if (intent.hasExtra(GoodsFragment.class.getSimpleName())) {
            which = GoodsFragment.class.getSimpleName();
        } else if (intent.hasExtra(SaleFragment.class.getSimpleName())) {
            which = SaleFragment.class.getSimpleName();
        } else if (intent.hasExtra(StatisticsFragment.class.getSimpleName())){
            which = StatisticsFragment.class.getSimpleName();
        }
        which2Switch();
        super.onNewIntent(intent);
    }

    private void which2Switch() {
        if (TextUtils.equals(OverViewFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(OverViewFragment.class);
        } else if (TextUtils.equals(GoodsFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(GoodsFragment.class);
        } else if (TextUtils.equals(SaleFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(SaleFragment.class);
        } else if (TextUtils.equals(StatisticsFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(StatisticsFragment.class);
        }else {
            switchFragmentandImg(MoreFragment.class);
        }
    }


    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(OverViewFragment.class.getSimpleName())) {
            which = OverViewFragment.class.getSimpleName();
        } else if (intent.hasExtra(GoodsFragment.class.getSimpleName())) {
            which = GoodsFragment.class.getSimpleName();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fm = getSupportFragmentManager();
        if (savedInstanceState != null) {
            recoverAllState(savedInstanceState);
        } else {
            which2Switch();
        }

//        JPushInterface.setAlias(getApplicationContext(), UserInfo.getUserId(), new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//            }
//        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            WindowUtils.showStatusBar(this);
//            firstRelative.setPadding(0, App.getStatusBarHeight(), 0, 0);
//        }
        StatusBarUtil.chenjin(this, R.color.THN_color_primaryColor);
    }

    private void recoverAllState(Bundle savedInstanceState) {
        Fragment overViewFragment = fm.getFragment(savedInstanceState, OverViewFragment.class.getSimpleName());
        addFragment2List(overViewFragment);
        Fragment goodsFragment = fm.getFragment(savedInstanceState, GoodsFragment.class.getSimpleName());
        addFragment2List(goodsFragment);
        Fragment saleFragment = fm.getFragment(savedInstanceState, SaleFragment.class.getSimpleName());
        addFragment2List(saleFragment);
        Fragment statisticsFragment = fm.getFragment(savedInstanceState, StatisticsFragment.class.getSimpleName());
        addFragment2List(statisticsFragment);
        Fragment moreFragment = fm.getFragment(savedInstanceState, MoreFragment.class.getSimpleName());
        addFragment2List(moreFragment);
        Class clazz = (Class) savedInstanceState.getSerializable(MainActivity.class.getSimpleName());
        if (clazz == null) return;
        switchFragmentandImg(clazz);
    }

    private void addFragment2List(Fragment fragment) {
        if (fragment == null || fragments.contains(fragment)) {
            LogUtil.e(TAG, "addedFragment==null || addedFragment  contains");
            return;
        }
        fragments.add(fragment);
    }

    @OnClick({R.id.ll_nav0, R.id.ll_nav1, R.id.ll_nav2, R.id.ll_nav3, R.id.ll_nav4})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_nav0:
                switchFragmentandImg(OverViewFragment.class);
                break;
            case R.id.ll_nav1:
                switchFragmentandImg(GoodsFragment.class);
                break;
            case R.id.ll_nav2:
                switchFragmentandImg(SaleFragment.class);
                break;
            case R.id.ll_nav3:
                switchFragmentandImg(StatisticsFragment.class);
                break;
            case R.id.ll_nav4:
                switchFragmentandImg(MoreFragment.class);
                break;
        }
    }

    @Override
    protected void installListener() {
//        MineFragment.setOnMessageCountChangeListener(new OnMessageCountChangeListener() {
//            @Override
//            public void onMessageCountChange(int count) {
//                if (count > 0) {
//                    tv_msg_indicator.setVisibility(View.VISIBLE);
//                } else {
//                    tv_msg_indicator.setVisibility(View.GONE);
//                }
//            }
//        });
    }

    @Override
    protected void initView() {
        if (tabList != null) {
            tabList.clear();
        } else {
            tabList = new ArrayList<>();
        }
        initTabItem(image0, tv_nav0, OverViewFragment.class, R.mipmap.home_selected, R.mipmap.home_normal);
        initTabItem(image1, tv_nav1, GoodsFragment.class, R.mipmap.goods_selected, R.mipmap.goods_normal);
        initTabItem(image2, tv_nav2, SaleFragment.class, R.mipmap.sale_selected, R.mipmap.sale_normal);
        initTabItem(image3, tv_nav3, StatisticsFragment.class, R.mipmap.statistic_selected, R.mipmap.statistic_normal);
        initTabItem(image4, tv_nav4, MoreFragment.class, R.mipmap.more_selected, R.mipmap.more_normal);
//        StatusBarUtil.showStatusBar(this);
    }

    private void initTabItem(ImageView imageView, TextView tv, Class clazz, int selId, int unselId) {
        TabItem tabItem = new TabItem();
        tabItem.imageView = imageView;
        tabItem.tv = tv;
        tabItem.clazz = clazz;
        tabItem.selId = selId;
        tabItem.unselId = unselId;
        tabList.add(tabItem);
    }

    private void switchFragmentandImg(Class clazz) {
//        if (!AndPermission.hasPermission(activity, android.Manifest.permission.READ_PHONE_STATE)) return;
        resetUI();
        try {
            switchImgAndTxtStyle(clazz);
            switchFragment(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换fragment
     *
     * @param clazz
     * @throws Exception
     */
    private void switchFragment(Class clazz) throws Exception {
        Fragment fragment = fm.findFragmentByTag(clazz.getSimpleName());
        if (fragment == null) {
            fragment = (Fragment) clazz.newInstance();
            fm.beginTransaction().add(R.id.activity_main_fragment_group, fragment, clazz.getSimpleName()).commitAllowingStateLoss();
        } else {
            fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        }
        addFragment2List(fragment);
        showFragment = fragment;
    }

    private void resetUI() {
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        for (Fragment fragment : fragments) {
            fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

    private void switchImgAndTxtStyle(Class clazz) {
        for (TabItem tabItem : tabList) {
            if (tabItem.clazz.equals(clazz)) {
                tabItem.imageView.setImageResource(tabItem.selId);
                tabItem.tv.setTextColor(getResources().getColor(R.color.color_27AE59));
            } else {
                tabItem.imageView.setImageResource(tabItem.unselId);
                tabItem.tv.setTextColor(getResources().getColor(R.color.color_A0A0A0));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int size = fragments.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                fm.putFragment(outState, fragments.get(i).getTag(), fragments.get(i));
            }
        }
        if (showFragment != null) {
            outState.putSerializable(MainActivity.class.getSimpleName(), showFragment.getClass());
        }
        super.onSaveInstanceState(outState);
    }

    public Fragment getVisibleFragment() {
        if (null == fragments) return null;
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true;
            ToastUtil.showInfo("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);

        } else {
            finish();
            System.exit(0);
        }
    }

    private void removeMineFragment(Class clazz) {
        Fragment fragment = fm.findFragmentByTag(clazz.getSimpleName());
        if (fragment != null) {
            fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) == fragment) {
                fragments.remove(i);
                break;
            }
        }
    }
}

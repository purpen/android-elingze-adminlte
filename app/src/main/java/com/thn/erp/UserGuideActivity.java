package com.thn.erp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.DataConstants;
import com.thn.erp.user.LoginRegisterActivity;
import com.thn.erp.utils.SPUtil;
import com.thn.erp.view.autoScrollViewpager.ScrollableView;
import com.thn.erp.view.autoScrollViewpager.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author lilin
 *         created at 2016/4/18 16:10
 */
public class UserGuideActivity extends BaseActivity {
    @BindView(R.id.scrollableView)
    ScrollableView scrollableView;
    @BindView(R.id.iv_welcome)
    ImageView iv_welcome;

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
            iv_welcome.setImageResource(R.mipmap.welcome);
            iv_welcome.setVisibility(View.VISIBLE);
            new MyHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv_welcome.setVisibility(View.GONE);
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
        if (TextUtils.isEmpty(SPUtil.read(Constants.AUTHORIZATION))){
            startActivity(new Intent(activity, LoginRegisterActivity.class));
        }else {
            startActivity(new Intent(activity, MainActivity.class));
        }
        finish();
    }

    static class MyHandler extends Handler {}
}

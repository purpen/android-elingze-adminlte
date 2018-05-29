package com.thn.erp.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.thn.erp.Constants;
import com.thn.erp.MainActivity;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.user.adapter.LoginRegisterViewPagerAdapter;
import com.thn.basemodule.tools.SPUtil;
import com.thn.erp.view.WrapContentHeightViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;



/**
 * @author lilin
 *         created at 2016/7/26 13:10
 */
public class LoginRegisterActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    WrapContentHeightViewPager viewPager;
    @BindView(R.id.btn_switch)
    Button btnSwitch;
    @BindView(R.id.tvLoginReg)
    TextView tvLoginReg;
    private String page;



    @Override
    protected int getLayout() {
        return R.layout.activity_login_register;
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.class.getSimpleName())){
            page = intent.getStringExtra(MainActivity.class.getSimpleName());
        }
    }

    @Override
    protected void initView() {
        btnSwitch.setText(R.string.no_account);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Bundle bundle = new Bundle();
        bundle.putString(TAG,page);
        ArrayList<Class> classes = new ArrayList<>();
        ArrayList<String> titles=new ArrayList<>();
        titles.add("login");
        titles.add("register");
        classes.add(LoginFragment.class);
        classes.add(RegisterFragment.class);
        LoginRegisterViewPagerAdapter adapter = new LoginRegisterViewPagerAdapter(getSupportFragmentManager(), classes,titles,"");
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
    }

    @Override
    protected void installListener() {

    }

    public ViewPager getViewPager(){
        return viewPager==null?null:viewPager;
    }

    @OnClick({R.id.tvLoginReg,R.id.tvForgetPassword,R.id.ibtn_close})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tvLoginReg:
                switch (viewPager.getCurrentItem()){
                    case 0:
                        viewPager.setCurrentItem(1);
                        tvLoginReg.setText(R.string.loginfiu);
                        break;
                    case 1:
                        viewPager.setCurrentItem(0);
                        tvLoginReg.setText(R.string.registerfiu);
                        break;
                }
                break;
            case R.id.tvForgetPassword:
                startActivity(new Intent(activity,ForgetPasswordActivity.class));
                break;
            case R.id.ibtn_close:
                if (TextUtils.isEmpty(SPUtil.read(Constants.TOKEN))){
                    Process.killProcess(Process.myPid());
                    System.exit(0);
                }else {
                    finish();
                }
                break;
            default:
                break;
        }
    }
//    UMAuthListener authListener = new UMAuthListener() {
//        @Override
//        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            String temp = "";
//            for (String key : data.keySet()) {
//                temp= temp+key +" : "+data.get(key)+"\n";
//            }
//            LogUtil.e("=================="+temp);
//            //获取用户信息
//            if (data==null) return;
//            switch (platform){
//                case WEIXIN:
//                    if (data.containsKey("openid")){
//                        uid= data.get("openid");
//                    }
//                    if (data.containsKey("access_token")){
//                        accessToken= data.get("access_token");
//                    }
//
//                    if (data.containsKey("screen_name")){
//                        name=data.get("screen_name");
//                    }
//                    if (data.containsKey("profile_image_url")){
//                        icon =data.get("profile_image_url");
//                    }
//                    if (data.containsKey("gender")){
//                        gender =data.get("gender");
//                    }
//                    break;
//                case FACEBOOK:
//                    break;
//                case INSTAGRAM:
//                    break;
//                default:
//                    break;
//            }
//            doThirdLogin();
//        }

//        @Override
//        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            ToastUtils.showError(R.string.third_login_error);
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform, int action) {
//            ToastUtils.showInfo(R.string.cancel_login);
//        }
//    };

    //三方登录
//    private void doThirdLogin() {
//        RequestService.doThirdLogin(uid,accessToken,name,icon,gender,new CustomCallBack(){
//            @Override
//            public void onStarted() {
//                if (dialog!=null&&!activity.isFinishing()) dialog.show();
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                LoginBean loginBean = JsonUtil.fromJson(result, LoginBean.class);
//                if (loginBean.meta.status_code== Constants.HTTP_OK){
//                    SPUtil.write(Constants.TOKEN,loginBean.data.token);
//                    getUserProfile();
//                    return;
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                LogUtil.e("login error");
//                ex.printStackTrace();
//            }
//
//            @Override
//            public void onFinished() {
//                if (dialog!=null&&!activity.isFinishing()) dialog.dismiss();
//            }
//        });
//    }

//    private void getUserProfile() {
//        RequestService.getUserProfile(new CustomCallBack() {
//            @Override
//            public void onSuccess(String result) {
//                if (TextUtils.isEmpty(result)) return;
//                try {
//                    UserProfile userInfo = JsonUtil.fromJson(result, UserProfile.class);
//                    if (userInfo.meta.status_code== Constants.HTTP_OK){
//                        if (JPushInterface.isPushStopped(AppApplication.getInstance())) JPushInterface.resumePush(AppApplication.getInstance());
//                        SPUtil.write(Constants.LOGIN_INFO,result);
//                        userId = userInfo.data.id;
//                        JPushInterface.setAlias(activity.getApplicationContext(),userId,mTagAliasCallback);
//                        Intent intent = new Intent(activity, MainActivity.class);
//                        if (TextUtils.isEmpty(page)) page = HomeFragment.class.getSimpleName();
//                        intent.putExtra(MainActivity.class.getSimpleName(),page);
//                        startActivity(intent);
//                        finish();
//                        return;
//                    }
//                }catch (JsonSyntaxException e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ex.printStackTrace();
//                ToastUtils.showError(R.string.request_error);
//            }
//        });
//    }

//    private final TagAliasCallback mTagAliasCallback = new TagAliasCallback() {
//        @Override
//        public void gotResult(int i, String s, Set<String> set) {
//            String logs;
//            switch (i) {
//                case 0:
//                    logs = "Set tag and alias success";
//                    LogUtil.e(logs);
//                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                    break;
//                case 6002:
//                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                    LogUtil.e(logs);
//                    // 延迟 60 秒来调用 Handler 设置别名
//                    mHandler.sendMessageDelayed(mHandler.obtainMessage(Constants.MSG_SET_ALIAS,userId), 1000 * 60);
//                    break;
//                default:
//                    logs = "Failed with errorCode = " + i;
//                    LogUtil.e(logs);
//            }
//        }
//    };


//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case Constants.MSG_SET_ALIAS:
//                    // 调用 JPush 接口来设置别名。
//                    JPushInterface.setAliasAndTags(AppApplication.getInstance(),
//                            (String) msg.obj,
//                            null,
//                            mTagAliasCallback);
//                    break;
//                default:
//                    LogUtil.i("Unhandled msg - " + msg.what);
//            }
//        }
//    };

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
//    }
}

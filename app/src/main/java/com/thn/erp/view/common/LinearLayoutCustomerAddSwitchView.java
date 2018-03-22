package com.thn.erp.view.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thn.erp.R;

import butterknife.BindView;
import ch.ielse.view.SwitchView;

/**
 * Created by Stephen on 2018/3/14 16:53
 * Email: 895745843@qq.com
 */

public class LinearLayoutCustomerAddSwitchView extends LinearLayout {
    private TextView textView1;
    private SwitchView switchView1;
    private boolean enabled = true;


    private SwitchView.OnStateChangedListener mOnStateChangedListener;

    public LinearLayoutCustomerAddSwitchView(Context context) {
        this(context, null);
    }

    public LinearLayoutCustomerAddSwitchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCustomerAddSwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_linearlayout_customer_add_switch, this, true);
        initView();
    }

    private void initView() {
        textView1 = (TextView) findViewById(R.id.textView1);
        switchView1 = (SwitchView) findViewById(R.id.switchView1);
    }


    public void setInitKeyAndHint(String str1, SwitchView.OnStateChangedListener onStateChangedListener) {
        setInitKeyAndHint(str1, onStateChangedListener, false);
    }

    public void setInitKeyAndHint(String str1, final SwitchView.OnStateChangedListener onStateChangedListener, boolean defaultValue) {
        textView1.setText(str1);
        switchView1.setOpened(defaultValue);
        switchView1.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                if (enabled) {
                    view.toggleSwitch(true);
                    onStateChangedListener.toggleToOn(view);
                }
            }

            @Override
            public void toggleToOff(SwitchView view) {
                if (enabled) {
                    view.toggleSwitch(false);
                    onStateChangedListener.toggleToOff(view);
                }
            }
        });
    }


//    public void setName(String str) {
//        textView1.setText(str);
//    }

    public void setValue(String str) {
        switchView1.setOpened(false);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

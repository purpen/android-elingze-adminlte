package com.thn.erp.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thn.erp.R;

/**
 * 数量编辑器
 */
public class CounterView extends LinearLayout {
    private Button btnDecrease;
    private Button btnIncrease;
    private EditText etAmount;
    private final int defaultNum = 1;
    private int num = 1;
    private int storageNum = 100;

    public CounterView(Context context) {
        super(context, null);
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_counter_view, this);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnDecrease = (Button) findViewById(R.id.btnDecrease);
        btnIncrease = (Button) findViewById(R.id.btnIncrease);
        etAmount.setText(String.valueOf(num));
        installListener();
    }

    private void installListener() {
        btnDecrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num > defaultNum) {
                    num--;
                    etAmount.setText(String.valueOf(num));
                }

            }
        });

        btnIncrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num < storageNum) {
                    num++;
                    etAmount.setText(String.valueOf(num));
                } else {
                    Toast.makeText(getContext(), "库存不足", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) return; //输入框为空设置为默认数量

                int curNum = Integer.valueOf(editable.toString());
                if (curNum > storageNum) {  //大于库存数量时设置为库存数量
                    etAmount.setText(String.valueOf(storageNum));
                    num = storageNum;
                } else if (curNum <= 0) {
                    etAmount.setText(String.valueOf(defaultNum));
                    num = defaultNum;
                } else {
                    num = curNum;
                }
            }
        });
    }

    /**
     * 设置库存数量
     *
     * @param num
     */
    public void setStorageNum(int num) {
        this.storageNum = num;
    }

    /**
     * 获取数量
     *
     * @return
     */
    public int getNum() {
        return num;
    }
}

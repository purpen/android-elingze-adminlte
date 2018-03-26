package com.thn.erp.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.utils.Util;

/**
 * Created by Stephen on 2016/12/13 15:48
 * Email: 895745843@qq.com
 */

class THNAlertDialog3 extends BaseDialog implements View.OnClickListener {
    private IDialogListenerConfirmBack iDialogListenerConfirmBack;
    private TextView textViewDialogTitle;
    private TextView textViewDialogContent;
    private Button buttonDialogConfirm;

    private String mTitle;
    private String mContent;
    private String[] mOperationTexts;

    THNAlertDialog3(Context context, String title, String content, String[] operationTexts) {
        super(context, 0);
        this.mTitle = title;
        this.mContent = content;
        this.mOperationTexts = operationTexts;

        setValues();
        setOnListener();
        this.show();
    }

    THNAlertDialog3(Context context, String title, String content, String[] operationTexts, IDialogListenerConfirmBack iDialogListenerConfirmBack) {
        super(context, 0);
        this.mTitle = title;
        this.mContent = content;
        this.mOperationTexts = operationTexts;
        this.iDialogListenerConfirmBack = iDialogListenerConfirmBack;

        setValues();
        setOnListener();
        this.show();
    }

    protected void initView(){
        this.setContentView(R.layout.dialog_alertdialog_layout3);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (Util.getScreenWidth() * 0.87);
        getWindow().setAttributes(lp);
        this.setCanceledOnTouchOutside(true);

        textViewDialogTitle = (TextView) findViewById(R.id.textView_dialog_title);
        textViewDialogContent = (TextView) findViewById(R.id.textView_dialog_content);
        buttonDialogConfirm = (Button) findViewById(R.id.button_dialog_confirm);
    }

    private void setValues() {
        if (!TextUtils.isEmpty(mTitle)) {
            textViewDialogTitle.setText(mTitle);
        }
        if (!TextUtils.isEmpty(mContent)) {
            textViewDialogContent.setText(mContent);
        }
        if (mOperationTexts != null) {
            buttonDialogConfirm.setText(mOperationTexts[0]);
        }
    }

    private void setOnListener() {
        buttonDialogConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_dialog_cancel) {
            this.dismiss();
        } else if (v.getId() == R.id.button_dialog_confirm){
            this.dismiss();
            if (iDialogListenerConfirmBack != null) {
                iDialogListenerConfirmBack.clickRight();
            }
        }
    }
}

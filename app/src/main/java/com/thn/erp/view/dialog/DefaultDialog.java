package com.thn.erp.view.dialog;

import android.content.Context;

/**
 * Created by Stephen on 2016/12/14 16:27
 * Email: 895745843@qq.com
 */

public class DefaultDialog {

    public DefaultDialog(Context context, String title, String[] operationTexts, IDialogListener iDialogListener) {
        new THNAlertDialog(context, title, operationTexts, iDialogListener);
    }

    public DefaultDialog(Context context, String title, String[] operationTexts, IDialogListenerConfirmBack iDialogListenerConfirmBack) {
        new THNAlertDialog(context, title, operationTexts, iDialogListenerConfirmBack);
    }

    public DefaultDialog(Context context, String title, String content, String[] operationTexts, IDialogListener iDialogListener) {
        new THNAlertDialog2(context, title, content, operationTexts, iDialogListener);
    }

    public DefaultDialog(Context context, String title, String content, String[] operationTexts, IDialogListenerConfirmBack iDialogListenerConfirmBack) {
        new THNAlertDialog2(context, title, content, operationTexts, iDialogListenerConfirmBack);
    }

    public DefaultDialog(Context context, String title, String content, String operationTexts, IDialogListenerConfirmBack iDialogListenerConfirmBack) {
        new THNAlertDialog3(context, title, content, new String[]{operationTexts}, iDialogListenerConfirmBack);
    }
}

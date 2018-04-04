package com.thn.erp.common.interfaces;

import android.view.View;

/**
 * Created by Stephen on 2018/3/12 16:02
 * Email: 895745843@qq.com
 */

public interface ImpTopbarOnClickListener {
    int LEFT            = 0; //左
    int CENTER          = 1; //中
    int RIGHT           = 2; //右

    void onTopBarClick(View view, int position);
}

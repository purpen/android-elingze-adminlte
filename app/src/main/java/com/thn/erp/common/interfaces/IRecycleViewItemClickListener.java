package com.thn.erp.common.interfaces;

import android.view.View;

/**
 * Created by lilin on 2017/2/22.
 */

public interface IRecycleViewItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}

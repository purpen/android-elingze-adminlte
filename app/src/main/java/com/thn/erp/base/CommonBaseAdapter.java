package com.thn.erp.base;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonBaseAdapter<T> extends BaseAdapter {
    protected final String TAG = getClass().getSimpleName();
    protected List<T> list;
    protected Activity activity;

    public CommonBaseAdapter(List<T> list, Activity activity) {
        if (null==list) throw new IllegalArgumentException("list fragments must not be null");
        if (null==activity) throw new IllegalArgumentException("activity titles must not be null");
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}

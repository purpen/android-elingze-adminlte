package com.thn.erp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.thn.erp.R;

/**
 * 带搜索框的导航栏
 */
public class CustomSearchHeadView extends RelativeLayout {
    private Context context;
    private OnLeftButtonClickListener onLeftButtonClickListener;
    private OnSearchClickListener onSearchClickListener;

    public void setOnLeftButtonClickListener(OnLeftButtonClickListener listener){
        this.onLeftButtonClickListener = listener;
    }

    public void setOnSearchClickListener(OnSearchClickListener listener){
        this.onSearchClickListener = listener;
    }

    public CustomSearchHeadView(Context context) {
        this(context, null);
    }

    public CustomSearchHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSearchHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflateLayout(context);
        installListener();
    }

    private void inflateLayout(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_custom_head_search, this, true);
    }

    private void installListener() {
        findViewById(R.id.ibBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftButtonClickListener != null) {
                    onLeftButtonClickListener.onClick(v);
                }
            }
        });

        ((SearchView) findViewById(R.id.searchView)).setOnSearchClickListener(new SearchView.OnSearchClickListener() {
            @Override
            public void onSearchClick(String s) {
                if (onSearchClickListener != null) {
                    onSearchClickListener.onSearch(s);
                }
            }
        });
    }


    /**
     * 点击搜索
     */
    public interface OnSearchClickListener {
        void onSearch(String s);
    }

    /**
     * 监听最左边的按钮点击
     */
    public interface OnLeftButtonClickListener {
        void onClick(View v);
    }
}

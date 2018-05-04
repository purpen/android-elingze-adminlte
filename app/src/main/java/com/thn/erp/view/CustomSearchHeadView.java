package com.thn.erp.view;

import android.content.Context;
import android.text.TextUtils;
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
    private SearchView searchView;

    public void setOnLeftButtonClickListener(OnLeftButtonClickListener listener) {
        this.onLeftButtonClickListener = listener;
    }

    public void setOnSearchClickListener(OnSearchClickListener listener) {
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
        searchView = findViewById(R.id.searchView);
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

        searchView.setOnSearchClickListener(new SearchView.OnSearchClickListener() {
            @Override
            public void onSearchClick(String s) {
                if (onSearchClickListener != null) {
                    onSearchClickListener.onSearch(s);
                }
            }
        });
    }

    /**
     * 设置搜索框是否可输入
     *
     * @param enable
     */
    public void setSearchViewEnable(boolean enable) {
        searchView.setEnabled(enable);
    }

    /**
     * 设置搜索框内容
     *
     * @param content
     */
    public void setContent(String content) {
        if (TextUtils.isEmpty(content)) return;
        searchView.setText(content);
        searchView.setSelection(content.length());
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

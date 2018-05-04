package com.thn.erp.view;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;


public class SearchView extends AppCompatEditText implements View.OnKeyListener {
    private static final String TAG = SearchView.class.getSimpleName();

    /**
     * 软键盘搜索键监听
     */
    private OnSearchClickListener listener;

    public void setOnSearchClickListener(OnSearchClickListener listener) {
        this.listener = listener;
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnKeyListener(this);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && listener != null) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                listener.onSearchClick(getText().toString().trim());
            }
        }
        return false;
    }

    public interface OnSearchClickListener {
        void onSearchClick(String s);
    }

}
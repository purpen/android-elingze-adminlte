package com.thn.chatinput.emoji.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.thn.chatinput.emoji.EmoticonFilter;

import java.util.ArrayList;
import java.util.List;


/**
 * use XhsEmotionsKeyboard(https://github.com/w446108264/XhsEmoticonsKeyboard)
 * author: sj
 */
public class EmoticonsEditText extends AppCompatEditText {

    private List<EmoticonFilter> mFilterList;

    public EmoticonsEditText(Context context) {
        this(context, null);
    }

    public EmoticonsEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmoticonsEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(oldh > 0 && onSizeChangedListener != null){
            onSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    @Override
    public void setGravity(int gravity) {
        try {
            super.setGravity(gravity);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            super.setGravity(gravity);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        try {
            super.setText(text, type);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(text.toString());
        }
    }

    @Override
    protected final void onTextChanged(CharSequence arg0, int start, int lengthBefore, int after) {
        super.onTextChanged(arg0, start, lengthBefore, after);
        if(mFilterList == null){
            return;
        }
        for(EmoticonFilter emoticonFilter : mFilterList) {
            emoticonFilter.filter(this, arg0, start, lengthBefore, after);
        }
    }

    public void addEmoticonFilter(EmoticonFilter emoticonFilter){
        if(mFilterList == null){
            mFilterList = new ArrayList<>();
        }
        mFilterList.add(emoticonFilter);
    }

    public void removedEmoticonFilter(EmoticonFilter emoticonFilter){
        if(mFilterList != null && mFilterList.contains(emoticonFilter)){
            mFilterList.remove(emoticonFilter);
        }
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (onBackKeyClickListener != null) {
                onBackKeyClickListener.onBackKeyClick();
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public interface OnBackKeyClickListener {
        void onBackKeyClick();
    }

    OnBackKeyClickListener onBackKeyClickListener;

    public void setOnBackKeyClickListener(OnBackKeyClickListener i) {
        onBackKeyClickListener = i;
    }

    public interface OnSizeChangedListener {
        void onSizeChanged(int w, int h, int oldw, int oldh);
    }

    OnSizeChangedListener onSizeChangedListener;

    public void setOnSizeChangedListener(OnSizeChangedListener i) {
        onSizeChangedListener = i;
    }


    /**
     * adjust the IME options after letting the platform configure them
     * @param outAttrs
     * @return
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection connection = super.onCreateInputConnection(outAttrs);
        int imeActions = outAttrs.imeOptions&EditorInfo.IME_MASK_ACTION;
        if ((imeActions&EditorInfo.IME_ACTION_SEND) != 0) {
            // clear the existing action
            outAttrs.imeOptions ^= imeActions;
            // set the SEND action
            outAttrs.imeOptions |= EditorInfo.IME_ACTION_SEND;
        }
        if ((outAttrs.imeOptions& EditorInfo.IME_FLAG_NO_ENTER_ACTION) != 0) {
            outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        }
        return connection;
    }

}
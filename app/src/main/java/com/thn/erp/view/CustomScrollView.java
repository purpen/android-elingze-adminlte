package com.thn.erp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by lilin on 2017/8/15.
 */

public class CustomScrollView extends ScrollView {
    private float downX;
    float downY;
    float deltaX;
    float deltaY;
    public CustomScrollView(Context context) {
        this(context,null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX =event.getX();
                downY=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = event.getX() - downX;
                deltaY = event.getY() -downY;
                if (Math.abs(deltaX)> Math.abs(deltaY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

}

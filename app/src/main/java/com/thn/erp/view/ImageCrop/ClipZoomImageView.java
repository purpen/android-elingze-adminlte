package com.thn.erp.view.ImageCrop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;

import com.thn.erp.R;


/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 *
 * @author zhy
 */
public class ClipZoomImageView extends AppCompatImageView implements OnTouchListener {
    /**
     * 最大放大比率（相较于原图尺寸）
     */
    public static float SCALE_MAX = 4.0f;

    /**
     * 最小放大比率（相较于原图尺寸）
     */
    private static float SCALE_MID = 2.0f;

    /**
     * 初始化时的缩放比例，如果图片宽或高最小值大于屏幕，此值将小于1.0
     */
    private float initScale = 1.0f;
    private boolean once = true;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];

    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector;
    private Matrix mScaleMatrix = new Matrix();

    /**
     * 用于双击检测
     */
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;

    private int mTouchSlop;

    private float mLastX;
    private float mLastY;

    private boolean isCanDrag;
    private int lastPointerCount;

    private int oldwidth;
    private int oldHeight;

    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding = 0;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding = 0;
    private float WidthToHeight = 0f;//宽高比

    public ClipZoomImageView(Context context) {
        this(context, null);
    }

    public ClipZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClipZoomImageView);
        WidthToHeight = a.getFloat(R.styleable.ClipZoomImageView_wTh, 0f);
        a.recycle();
        setScaleType(ScaleType.MATRIX);

        SimpleOnGestureListener onDoubleListener = new SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                if (onLongClickListener != null) {
                    onLongClickListener.onLongClick(ClipZoomImageView.this);
                }
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (onClickListener != null) {
                    onClickListener.onClick(ClipZoomImageView.this);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale)
                    return true;

                float x = e.getX();
                float y = e.getY();
                if (getScale() < SCALE_MID) {
                    ClipZoomImageView.this.postDelayed(
                            new AutoScaleRunnable(SCALE_MID, x, y), 16);
                    isAutoScale = true;
                } else {
                    ClipZoomImageView.this.postDelayed(
                            new AutoScaleRunnable(initScale, x, y), 16);
                    isAutoScale = true;
                }

                return true;
            }
        };
        mGestureDetector = new GestureDetector(context, onDoubleListener);

        OnScaleGestureListener mOnScaleListener = new OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scale = getScale();
                float scaleFactor = detector.getScaleFactor();

                if (getDrawable() == null)
                    return true;
                /**
                 * 缩放的范围控制
                 */
                if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                        || (scale > initScale && scaleFactor < 1.0f)) {
                    /**
                     * 最大值最小值判断
                     */
                    if (scaleFactor * scale < initScale) {
                        scaleFactor = initScale / scale;
                    }
                    if (scaleFactor * scale > SCALE_MAX) {
                        scaleFactor = SCALE_MAX / scale;
                    }
                    /**
                     * 设置缩放比例
                     */
                    mScaleMatrix.postScale(scaleFactor, scaleFactor,
                            detector.getFocusX(), detector.getFocusY());
                    checkBorder();
                    setImageMatrix(mScaleMatrix);
                }
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        };
        mScaleGestureDetector = new ScaleGestureDetector(context, mOnScaleListener);
        this.setOnTouchListener(this);
        getViewTreeObserver().addOnGlobalLayoutListener(onLayoutListener);
    }

    private OnLongClickListener onLongClickListener;
    private OnClickListener onClickListener;

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        onLongClickListener = l;
    }

    private ViewTreeObserver.OnGlobalLayoutListener onLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (once) {
                getViewTreeObserver().removeGlobalOnLayoutListener(onLayoutListener);
                initScale();
                once = false;
            }
        }
    };

    /**
     * 自动缩放的任务
     *
     * @author zhy
     */
    private class AutoScaleRunnable implements Runnable {
        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
        private float mTargetScale;
        private float tmpScale;
        /**
         * 缩放的中心
         */
        private float x;
        private float y;

        /**
         * 传入目标缩放值，根据目标值与当前值，判断应该放大还是缩小
         *
         * @param targetScale
         */
        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }

        }

        @Override
        public void run() {
            // 进行缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorder();
            setImageMatrix(mScaleMatrix);

            final float currentScale = getScale();
            // 如果值在合法范围内，继续缩放
            if (((tmpScale > 1f) && (currentScale < mTargetScale))
                    || ((tmpScale < 1f) && (mTargetScale < currentScale))) {
                ClipZoomImageView.this.postDelayed(this, 16);
            } else
            // 设置为目标的缩放比例
            {
                final float deltaScale = mTargetScale / currentScale;
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorder();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        setWAH();
        if (mGestureDetector.onTouchEvent(event))
            return true;
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0, y = 0;
        // 拿到触摸点的个数
        final int pointerCount = event.getPointerCount();
        // 得到多个触摸点的x与y均值
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;

        /**
         * 每当触摸点发生变化时，重置mLasX , mLastY
         */
        if (pointerCount != lastPointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        lastPointerCount = pointerCount;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag) {
                    if (getDrawable() != null) {

                        RectF rectF = getMatrixRectF();
                        // 如果宽度小于屏幕宽度，则禁止左右移动
                        if (rectF.width() <= getWidth() - mHorizontalPadding * 2) {
                            dx = 0;
                        }
                        // 如果高度小雨屏幕高度，则禁止上下移动
                        if (rectF.height() <= getHeight() - mVerticalPadding * 2) {
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorder();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;
                break;
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Drawable d = getDrawable();
        if (d == null || getWidth() == 0 || once)
            return;
        initScale();
    }

    private boolean isSet;

    private void setWAH() {
        if (isSet) {
            return;
        }
        if (WidthToHeight != 0f && getHeight() != 0) {
            int nW = (int) (WidthToHeight * getHeight());
            if (nW == getWidth()) {
                mHorizontalPadding = 0;
                mVerticalPadding = 0;
            } else if (nW < getWidth()) {
                mVerticalPadding = 0;
                mHorizontalPadding = (getWidth() - nW) / 2;
            } else if (nW > getWidth()) {
                mHorizontalPadding = 0;
                mVerticalPadding = (int) ((getHeight() - (double) getWidth() / WidthToHeight) / 2);
            }
            isSet = true;
        }
    }

    private void initScale() {
        setWAH();
        Drawable d = getDrawable();
        if (d == null)
            return;
        if (oldwidth != d.getIntrinsicWidth() || oldHeight != d.getIntrinsicHeight()) {
            oldwidth = d.getIntrinsicWidth();
            oldHeight = d.getIntrinsicHeight();
            // 垂直方向的边距
//            mVerticalPadding = (getHeight() - getWidth() * 4 / 3) / 2;
//            if (getHeight() * 9 < getWidth() * 16) {
//                mVerticalPadding = 0;
//                mHorizontalPadding = (getWidth() - getHeight() * 9 / 16) / 2;
//            } else {
//                mHorizontalPadding = 0;
//                mVerticalPadding = (getHeight() - getWidth() * 16 / 9) / 2;
//            }
            int width = getWidth();
            int height = getHeight();
            // 拿到图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            int contentWidth = width - mHorizontalPadding * 2;
            int contentHeight = height - mVerticalPadding * 2;

            if (dw < contentWidth && dh >= contentHeight) {
                scale = (width * 1.0f - mHorizontalPadding * 2) / dw;
            }
            if (dw < contentWidth && dh < contentHeight) {
                float scaleW = (width * 1.0f - mHorizontalPadding * 2)
                        / dw;
                float scaleH = (height * 1.0f - mVerticalPadding * 2) / dh;
                scale = Math.max(scaleW, scaleH);
            }
            if (dw >= contentWidth && dh >= contentHeight) {
                float scaleW = (width * 1.0f - mHorizontalPadding * 2)
                        / dw;
                float scaleH = (height * 1.0f - mVerticalPadding * 2) / dh;
                scale = Math.max(scaleW, scaleH);
            }
            if (dw >= contentWidth && dh < contentHeight) {
                scale = (height * 1.0f - mVerticalPadding * 2) / dh;
            }

            initScale = scale;
            SCALE_MID = initScale * 2;
            SCALE_MAX = initScale * 4;
            mScaleMatrix = new Matrix();
            mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
            mScaleMatrix.postScale(scale, scale, width / 2,
                    height / 2);
            // 图片移动至屏幕中心
            setImageMatrix(mScaleMatrix);
        }
    }

    /**
     * 获得当前的缩放比例
     *
     * @return
     */
    public final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    /**
     * 剪切图片，返回剪切后的bitmap对象
     *
     * @return
     */
    public Bitmap clip() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        Bitmap chipResult = Bitmap.createBitmap(bitmap, mHorizontalPadding,
                mVerticalPadding, getWidth() - 2 * mHorizontalPadding, getHeight() - 2 * mVerticalPadding);
        if (chipResult != bitmap) {
            bitmap.recycle();
        }
        return chipResult;
    }

    /**
     * 边界检测
     */
    private void checkBorder() {
        setWAH();
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围 ; 这里的0.001是因为精度丢失会产生问题，但是误差一般很小，所以我们直接加了一个0.01
        if (rect.width() + 0.01 >= width - 2 * mHorizontalPadding) {
            if (rect.left > mHorizontalPadding) {
                deltaX = -rect.left + mHorizontalPadding;
            }
            if (rect.right < width - mHorizontalPadding) {
                deltaX = width - mHorizontalPadding - rect.right;
            }
        }
        if (rect.height() + 0.01 >= height - 2 * mVerticalPadding) {
            if (rect.top > mVerticalPadding) {
                deltaY = -rect.top + mVerticalPadding;
            }
            if (rect.bottom < height - mVerticalPadding) {
                deltaY = height - mVerticalPadding - rect.bottom;
            }
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 是否是拖动行为
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }

}
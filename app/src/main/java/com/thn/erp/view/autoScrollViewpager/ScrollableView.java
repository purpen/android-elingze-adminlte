package com.thn.erp.view.autoScrollViewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.thn.erp.R;

import java.util.ArrayList;

/**
 * @author lilin
 *         created at 2016/4/20 11:58
 */
public class ScrollableView extends RelativeLayout {
    private Context context;
    private int interval = 3000;  //默认3s
    private CustomAutoScrollViewPager viewPager;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private ArrayList<ImageView> imageViews;
    private int currentItem;
    private LinearLayout ll;
    private TextView tvNum;
    private TextView tvDistance;
    private int size;

    public ScrollableView(Context context) {
        this(context, null);
    }

    public ScrollableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public void setAdapter(ViewPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        this.size=adapter.getSize();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.auto_scroll_viewpager_scrollable_view, this);
        viewPager = (CustomAutoScrollViewPager) view.findViewById(R.id.casvp);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        tvNum = (TextView) view.findViewById(R.id.tv_num);
        tvDistance = (TextView) view.findViewById(R.id.tv_distance);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener){
        if (onPageChangeListener!=null){
            viewPager.addOnPageChangeListener(onPageChangeListener);
        }
    }

    public void setSwipeScrollDurationFactor(double f) {
        viewPager.setSwipeScrollDurationFactor(f);
    }

    public void setAutoScrollDurationFactor(double f){
        viewPager.setAutoScrollDurationFactor(f);

    }

    public void setInterval(int interval){
        viewPager.setInterval(interval);
    }

    public void start(){
        viewPager.startAutoScroll();
    }

    public void stop(){
        viewPager.stopAutoScroll();
    }

    public void showIndicators() {
        if (size<=1) return;
        imageViews = new ArrayList<>();
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.gravity = Gravity.CENTER_HORIZONTAL;
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        llp.setMargins(context.getResources().getDimensionPixelSize(R.dimen.dp5), 0, 0, context.getResources().getDimensionPixelSize(R.dimen.dp10));
        ImageView imageView;
        for (int i = 0; i < size; i++) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(vlp);
            if (i==currentItem){
                imageView.setImageResource(R.drawable.bg_auto_scroll_viewpager_shape_oval_sel);
            }else {
                imageView.setImageResource(R.drawable.bg_auto_scroll_viewpager_shape_oval_unsel);
            }
            imageViews.add(imageView);
            ll.addView(imageView, llp);
        }
        addOnPageChangeListener(new CustomOnPageChangeListener());
    }

    public void showIndicatorRight(){

        tvNum.setVisibility(VISIBLE);
        tvNum.setText("1/"+size);
        tvNum.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        tvNum.setTextColor(context.getResources().getColor(android.R.color.white));
        addOnPageChangeListener(new CustomOnPageChangeListener());
    }

    public void showDistance(String distance){
        tvDistance.setVisibility(VISIBLE);
        tvDistance.setText(distance);
        tvDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        tvDistance.setTextColor(context.getResources().getColor(android.R.color.white));
    }

    private class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            currentItem=position = position % size;
            tvNum.setText((position+1)+"/"+size);
            setCurFocus(position);
            if (mOnPageChangedListener != null) {
                mOnPageChangedListener.onPageSelected(size, position);
            }
        }

        private void setCurFocus(int position) {
            if (imageViews==null||imageViews.size()==0) return;
//            LogUtil.e("setCurFocus", position + "");
            for (int i = 0; i < size; i++) {
                if (i == position) {
                    imageViews.get(i).setImageResource(R.drawable.bg_auto_scroll_viewpager_shape_oval_sel);
                } else {
                    imageViews.get(i).setImageResource(R.drawable.bg_auto_scroll_viewpager_shape_oval_unsel);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public void setCurrentItem(int i) {
        viewPager.setCurrentItem(i);
    }

    public void setPagingEnabled(boolean b) {
        viewPager.setPagingEnabled(false);
    }

    private OnPageChangedListener mOnPageChangedListener;
    public interface OnPageChangedListener{
        void onPageSelected(int total, int position);
    }

    public void setOnPageChangeListener(OnPageChangedListener onPageChangeListener) {
        this.mOnPageChangedListener = onPageChangeListener;
    }
}

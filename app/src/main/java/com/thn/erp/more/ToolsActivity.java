package com.thn.erp.more;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.more.adapter.ToolsAdapter;
import com.thn.erp.view.common.PublicTopBar;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/21 15:22
 * Email: 895745843@qq.com
 */

public class ToolsActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;

    private static final String[] STOCK_ITEMS = {"扫码出库", "发货核验", "扫码入库", "扫码盘点"};
    private static final String[] ORDER_ITEMS = {"扫码下单"};

    @Override
    protected int getLayout() {
        return R.layout.activity_more_tools;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setTitle("fdjsakdfsjka");
    }

    @Override
    protected void initView() {
        initTopBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.addItemDecoration(new MYDividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView2.addItemDecoration(new MYDividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        ToolsAdapter adapter1 = new ToolsAdapter(Arrays.asList(STOCK_ITEMS), new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(ToolsActivity.this, PrepareExportStockActivity.class));
                        break;
                }
            }
        });
        recyclerView1.setAdapter(adapter1);
        ToolsAdapter adapter2 = new ToolsAdapter(Arrays.asList(ORDER_ITEMS), new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(ToolsActivity.this, PrepareOrderingActivity.class));
                        break;
                }
            }
        });
        recyclerView2.setAdapter(adapter2);
    }

    private void initTopBar() {
        publicTopBar.setTopBarCenterTextView("工具箱", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }

    private class MYDividerItemDecoration extends RecyclerView.ItemDecoration{
        public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
        public static final int VERTICAL = LinearLayout.VERTICAL;

        private static final String TAG = "DividerItem";
        private final int[] ATTRS = new int[]{ android.R.attr.listDivider };

        private Drawable mDivider;

        /**
         * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
         */
        private int mOrientation;

        private final Rect mBounds = new Rect();

        /**
         * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
         * {@link LinearLayoutManager}.
         *
         * @param context Current context, it will be used to access resources.
         * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
         */
        public MYDividerItemDecoration(Context context, int orientation) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            if (mDivider == null) {
                Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this "
                        + "DividerItemDecoration. Please set that attribute all call setDrawable()");
            }
            a.recycle();
            setOrientation(orientation);
        }

        /**
         * Sets the orientation for this divider. This should be called if
         * {@link RecyclerView.LayoutManager} changes orientation.
         *
         * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
         */
        public void setOrientation(int orientation) {
            if (orientation != HORIZONTAL && orientation != VERTICAL) {
                throw new IllegalArgumentException(
                        "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
            }
            mOrientation = orientation;
        }

        /**
         * Sets the {@link Drawable} for this divider.
         *
         * @param drawable Drawable that should be used as a divider.
         */
        public void setDrawable(@NonNull Drawable drawable) {
            if (drawable == null) {
                throw new IllegalArgumentException("Drawable cannot be null.");
            }
            mDivider = drawable;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (parent.getLayoutManager() == null || mDivider == null) {
                return;
            }
            if (mOrientation == VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        private void drawVertical(Canvas canvas, RecyclerView parent) {
            canvas.save();
            final int left;
            final int right;
            //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
            if (parent.getClipToPadding()) {
                left = parent.getPaddingLeft();
                right = parent.getWidth() - parent.getPaddingRight();
                canvas.clipRect(left, parent.getPaddingTop(), right,
                        parent.getHeight() - parent.getPaddingBottom());
            } else {
                left = 0;
                right = parent.getWidth();
            }

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                parent.getDecoratedBoundsWithMargins(child, mBounds);
                final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                final int top = bottom - mDivider.getIntrinsicHeight()/2;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            canvas.restore();
        }

        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            canvas.save();
            final int top;
            final int bottom;
            //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
            if (parent.getClipToPadding()) {
                top = parent.getPaddingTop();
                bottom = parent.getHeight() - parent.getPaddingBottom();
                canvas.clipRect(parent.getPaddingLeft(), top,
                        parent.getWidth() - parent.getPaddingRight(), bottom);
            } else {
                top = 0;
                bottom = parent.getHeight();
            }

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
                final int right = mBounds.right + Math.round(child.getTranslationX());
                final int left = right - mDivider.getIntrinsicWidth()/2;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            canvas.restore();
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (mDivider == null) {
                outRect.set(0, 0, 0, 0);
                return;
            }
//            if (mOrientation == VERTICAL) {
//                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight()/2);
//            } else {
//                outRect.set(0, 0, mDivider.getIntrinsicWidth()/2, 0);
//            }
        }
    }
}

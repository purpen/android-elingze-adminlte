package com.thn.erp.view.DropdownMenu;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stephen.taihuoniaolibrary.utils.THNLogUtil;
import com.thn.erp.R;
import com.thn.erp.utils.Util;

/**
 * 下拉菜单
 */
public class DropdownMenu extends RelativeLayout {
    /**
     * 菜单列表
     */
    private RecyclerView popRecyclerView;
    private PopupWindow popupWindow;
    private View view;
    private LayoutInflater inflater;
    public static final int LINEAR_TYPE = 0;
    public static final int GRID_TYPE = 1;
    private DropdownMenuAdapter adapter;
    private RecyclerView.LayoutManager manager;
    public DropdownMenu(Context context) {
        this(context, null);
    }

    public DropdownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context);
    }


    private void inflateLayout(Context context) {
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_dropdown_menu, this, true);
        ImageView imageView = findViewById(R.id.imageView);
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        ((TextView)findViewById(R.id.title)).setText(title);
    }



    /**
     * 设置显示类型
     * @param layoutManagerType
     * @param column
     */
    public void setLayoutManagerType(int layoutManagerType,int column) {
        if (layoutManagerType==LINEAR_TYPE){
            manager =new LinearLayoutManager(getContext());
        }else if(layoutManagerType== GRID_TYPE){
            manager =new GridLayoutManager(getContext(),column);
        }
    }

    public void setAdapter(DropdownMenuAdapter adapter){
        this.adapter =adapter;
    }

    public void show() {
        if (popupWindow!=null&&popRecyclerView!=null) {
            showMenu();
            return;
        }
        popRecyclerView = (RecyclerView) inflater.inflate(R.layout.view_popup_recyclerview, null);
        popRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        if (manager==null){
            manager = new LinearLayoutManager(getContext());

        }
        popRecyclerView.setLayoutManager(manager);
        popRecyclerView.setAdapter(adapter);
        popupWindow = new PopupWindow(popRecyclerView,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(android.R.style.Widget_PopupMenu);
//        popupWindow.setFocusable(true);  //注释后不获得焦点，其它地方可点
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        showMenu();
    }

    private void  showMenu(){
        THNLogUtil.e("getLeft()="+getLeft());
        THNLogUtil.e("getRight()="+getRight());
        double v = (getResources().getDimensionPixelSize(R.dimen.dp200) - getMeasuredWidth()) * 0.5;

        THNLogUtil.e("v="+v);
        if (getLeft()<v || Util.getScreenWidth()- getRight()<v){
            popupWindow.showAsDropDown(view);
        }else {
            popupWindow.showAsDropDown(view,-(int)v,0);
        }
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public void setOnItemClickListener(DropdownMenuAdapter.OnItemClickListener listener) {
        adapter.setOnItemClickListener(listener);
    }

}

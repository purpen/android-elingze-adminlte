package com.thn.erp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thn.erp.R;

public class MyTopBar extends RelativeLayout {
  
    // 自定义的控件和自定义的属性（values下mytopbar.xml）的声明  
    private Button leftButton, rightButton;
    private TextView tvTitle;
  
    private int leftTextColor;  
    private Drawable leftDrawable;
    private String leftText;  
  
    private float titleTextSize;  
    private int titleTextColor;  
    private String title;  
  
    private int rightTextColor;  
    private Drawable rightDrawable;  
    private String rightText;  
  
    private LayoutParams leftLayoutParams, titleLayoutParams, rightLayoutParams;  
  
    private myTopbarClicklistenter clicklistenter;  
      
    //自定义click的监听回调接口  
    public interface myTopbarClicklistenter{  
        public void leftClick();  
        public void rightClick();
    }
      
    //自定义一个setOnClickListenter方法  
    public void setOnTopbarClickListenter(myTopbarClicklistenter clicklistenter){  
        this.clicklistenter=clicklistenter;   //调用的时候通过一个匿名内部类映射进来  
    }  
      
    //重写构造方法  
    public MyTopBar(Context context, AttributeSet attrs) {
          
        super(context, attrs);  
          
        // 获取自定义的属性，并将自定义的属性映射到自定义的属性值中去  
        // 通过obtainStyledAttributes获取自定义属性，并存到TypedArray  
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTopBar);
  
        leftTextColor = ta.getColor(R.styleable.MyTopBar_leftTextColor, 0);    //从TypedArray中取出来，并对应到自定义的属性值上  
        leftDrawable = ta.getDrawable(R.styleable.MyTopBar_leftBackGround);  
        leftText = ta. getString(R.styleable.MyTopBar_leftText);  
  
        titleTextSize = ta.getDimension(R.styleable.MyTopBar_titleTextSize, 0);  
        titleTextColor = ta.getColor(R.styleable.MyTopBar_titleTextColor, 0);  
        title = ta.getString(R.styleable.MyTopBar_title);  
  
        rightTextColor = ta.getColor(R.styleable.MyTopBar_rightTextColor, 0);  
        rightDrawable = ta.getDrawable(R.styleable.MyTopBar_rightBackGround);  
        rightText = ta.getString(R.styleable.MyTopBar_rightText);  
  
        ta.recycle();  
  
        //组件定义  
        leftButton = new Button(context);  
        tvTitle = new TextView(context);  
        rightButton = new Button(context);  
  
        // 将自定义的属性设置到控件上  
        leftButton.setTextColor(leftTextColor);  
        leftButton.setBackground(leftDrawable);  
        leftButton.setText(leftText);  
  
        tvTitle.setTextColor(titleTextColor);  
        tvTitle.setTextSize(titleTextSize);  
        tvTitle.setText(title);  
        tvTitle.setGravity(Gravity.CENTER);    // 设置文字居中
  
        rightButton.setTextColor(rightTextColor);  
        rightButton.setBackground(rightDrawable);  
        rightButton.setText(rightText);  
  
//        setBackgroundColor(0xFFF59563);    // 设置背景颜色
          
        //将自定义的控件放到Layout中（以LayoutParams的形式）  
        leftLayoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        leftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);     //设置左对齐          
        addView(leftButton,leftLayoutParams);  //leftButton以leftLayoutParams的形式加入到ViewGroup中  
          
        titleLayoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);  
        titleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);  //设置居中对齐        
        addView(tvTitle,titleLayoutParams);    //tvTitle以titleLayoutParams的形式加入到ViewGroup中  
                  
        rightLayoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);  
        rightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE); //设置右对齐        
        addView(rightButton,rightLayoutParams);//rightButton以rightLayoutParams的形式加入到ViewGroup中  
          
        //设置监听事件  
        leftButton.setOnClickListener(new View.OnClickListener() {
              
            @Override  
            public void onClick(View v) {
                if (clicklistenter != null) {
                    clicklistenter.leftClick();
                }
            }
        });  
          
        rightButton.setOnClickListener(new View.OnClickListener() {  
              
            @Override  
            public void onClick(View v) {  
                if (clicklistenter != null) {
                    clicklistenter.rightClick();
                }
            }  
        });  
    }
      
    //设置左Button是否显示  
    public void setLeftIsVisible(boolean flag){  
        if(flag){  
            leftButton.setVisibility(View.VISIBLE);  
        }else{  
            leftButton.setVisibility(View.GONE);  
        }  
    }  
      
    // 设置右Button是否显示  
    public void setRightIsVisible(boolean flag) {  
        if (flag) {  
            rightButton.setVisibility(View.VISIBLE);  
        } else {  
            rightButton.setVisibility(View.GONE);  
        }  
    }  
}  
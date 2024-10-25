package com.ixuea.superui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class SquareLinearLayout extends LinearLayout {
    public SquareLinearLayout(Context context) {
        super(context);
    }

    public SquareLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    /*
    * 测量控件
    * */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置系统测量好的尺寸
        //getDefaultSize 方法获取系统根据传入的测量规格（widthMeasureSpec 和 heightMeasureSpec）
        //测量出的默认尺寸，然后通过 setMeasuredDimension 方法设置视图的测量尺寸。这里先将视图的尺寸设置为系统默认测量的尺寸。
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),getDefaultSize(0, heightMeasureSpec));
        //获取测量后的宽度
        int width=getMeasuredWidth();
        //创建一个测量规格
        //设置高度等于宽度
        //通过 MeasureSpec.makeMeasureSpec 方法将高度和宽度的测量规格都设置为与前面获取到的宽度相等
        //并且测量模式设置为 MeasureSpec.EXACTLY，这意味着视图的高度和宽度将被精确地设置为指定的值
        widthMeasureSpec=heightMeasureSpec=MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);
        //让父类设置尺寸
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

    }
}

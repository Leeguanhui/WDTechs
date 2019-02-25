package com.wd.tech.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.tech.R;

public class FlowLayout extends FrameLayout {
    //水平间的距离
    private final static int H_DISTANCE = 22;
    //竖直间的距离
    private final static int V_DISTANCE = 20;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addTextView(String keys) {
        //加载页面布局
        TextView textView = (TextView) View.inflate(getContext(), R.layout.flow_item, null);
        textView.setText(keys);
        //布局宽高自适应
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置控件的布局参数
        textView.setLayoutParams(params);

        this.addView(textView);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取本控件的宽度
        int width = getWidth();
        //行数
        int row = 0;
        int disWidth = H_DISTANCE;//子控件左边的坐标
        for (int i = 0; i < getChildCount(); i++) {
            //查找子控件
            View view = getChildAt(i);
            //获取控件的宽高
            int viewWidth = view.getWidth();
            int viewHeight = view.getHeight();
            //判断控件是否超过了屏幕宽度
            if (disWidth + viewWidth > width) {
                //行数增加
                row++;
                //初始化坐标
                disWidth = H_DISTANCE;
            }
            int viewTop = row * viewHeight + row * V_DISTANCE;
            //子布局控件
            view.layout(disWidth, viewTop, disWidth + viewWidth, viewTop + viewHeight);
            //记录下一次子控件左边的坐标
            disWidth += (viewWidth + H_DISTANCE);
        }
    }
}
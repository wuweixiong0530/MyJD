package com.bwie.myapplication.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class MyExpanableView extends ExpandableListView {
    public MyExpanableView(Context context) {
        super(context);
    }

    public MyExpanableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyExpanableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, hight);
    }
}

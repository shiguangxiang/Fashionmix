package com.kuaimeizhuang.fashionmix.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import java.util.function.Consumer;

/**
 * <p>注释类</p>
 * Created on 17/6/6.
 *
 * @author Shi GuangXiang.
 */

public class BaseRecyclerView extends RecyclerView {


    private int scaledEdgeSlop;

    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        scaledEdgeSlop = configuration.getScaledEdgeSlop();
    }

    @Override
    public void setScrollingTouchSlop(int slopConstant) {
        super.setScrollingTouchSlop(slopConstant);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());

    }
}

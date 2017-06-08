package com.kuaimeizhuang.fashionmix.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <p>自定义ViewPager</p>
 * Created on 17/5/11.
 *
 * @author Shi GuangXiang.
 */

public class BeautyViewPager extends LazyViewPager {
    private boolean scroll = true;
    public BeautyViewPager(Context context) {
        super(context);
    }
    public BeautyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if(scroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(scroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
}

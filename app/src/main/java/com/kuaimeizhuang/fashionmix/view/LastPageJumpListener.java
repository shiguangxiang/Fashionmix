package com.kuaimeizhuang.fashionmix.view;

import android.support.v4.view.ViewPager;

import com.kuaimeizhuang.fashionmix.utils.LogUtils;

/**
 * <p>当viewpager滑动到最后一页的监听</p>
 * Created on 17/6/7.
 *
 * @author Shi GuangXiang.
 */

public class LastPageJumpListener implements ViewPager.OnPageChangeListener {
    private int lastIndex;
    private Runnable command;

    /**
     * 是否可以跳转
     */
    private boolean canJump;
    private int curPosition;

    /**
     * @param lastIndex 最后一页的索引
     * @param command   事件触发时你想要做的事情
     */
    public LastPageJumpListener(int lastIndex, Runnable command) {
        this.lastIndex = lastIndex;
        this.command = command;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        LogUtils.d("onPageScrolled position:" + position);
        //当最后一页往后划动的时候触发该事件
        if (position == lastIndex && positionOffset == 0
                && positionOffsetPixels == 0) {
            if (canJump) {
                command.run();
                // 事件执行一次后进行重置,避免事件多次触发
                canJump = false;
            }
        }
    }

    /*
    * (non-Javadoc)
    *
    * @see
    * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
    * (int)
    */
    @Override
    public void onPageSelected(int position) {
        curPosition = position;
    }

    /**
     * public static final int SCROLL_STATE_IDLE = 0;
     * <p>
     * public static final int SCROLL_STATE_DRAGGING = 1;
     * <p>
     * public static final int SCROLL_STATE_SETTLING = 2;
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        LogUtils.d("onPageScrollStateChanged state:" + state);
        // 判断是否是划动状态且是最后一页
        if (state == ViewPager.SCROLL_STATE_DRAGGING
                && curPosition == lastIndex) {
            canJump = true;
        } else {
            canJump = false;
        }
    }

}

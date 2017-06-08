package com.kuaimeizhuang.fashionmix.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.Random;

/**
 * <p>喜欢点赞</p>
 * Created on 17/5/26.
 *
 * @author Shi GuangXiang.
 */

public class FavorLayout extends RelativeLayout {
    private Interpolator mLinearInterpolator = new LinearInterpolator();
    private Interpolator mAccelerateInterpolator = new AccelerateInterpolator();
    private Interpolator mDecelerateInterpolator = new DecelerateInterpolator();
    private Interpolator mAccelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
    private Interpolator[] mInterpolator;
    private int mHeight;
    private int mWidth;
    private LayoutParams lp;

    private int dWidth;
    private int dHeight;
    private Drawable[] drawables;
    private Drawable zan_red_heart;
    private Random random = new Random();

    public FavorLayout(Context context) {
        super(context);
    }

    public FavorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        init();
    }

    private void init() {
        drawables = new Drawable[3];
        zan_red_heart = getResources().getDrawable(R.drawable.iv_praise_red);
        drawables[0] = zan_red_heart;
        drawables[1] = zan_red_heart;
        drawables[2] = zan_red_heart;
        dHeight = zan_red_heart.getIntrinsicHeight();
        dWidth = zan_red_heart.getIntrinsicWidth();

        lp = new LayoutParams(dWidth, dHeight);
        lp.rightMargin = lp.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.dp37);
        lp.addRule(ALIGN_PARENT_RIGHT, TRUE);
        lp.addRule(ALIGN_PARENT_BOTTOM, TRUE);

        mInterpolator = new Interpolator[4];
        mInterpolator[0] = mLinearInterpolator;
        mInterpolator[1] = mAccelerateInterpolator;
        mInterpolator[2] = mDecelerateInterpolator;
        mInterpolator[3] = mAccelerateDecelerateInterpolator;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        /*int minimumHeight = getMinimumHeight();
        if (mHeight == 0) {
            mHeight = UIUtils.dip2px(202);
        }*/
    }


    public void addFavors() {
        for (int i = 0; i < 8; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(drawables[random.nextInt(3)]);
            imageView.setLayoutParams(lp);
            addView(imageView);
            Animator set = getAnimator(imageView);
            set.addListener(new AnimEndListener(imageView));
            set.start();
        }
    }

    private Animator getAnimator(View target) {
        AnimatorSet set = getEnterAnimator(target);
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);
        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(set);
        finalSet.playSequentially(set, bezierValueAnimator);
        finalSet.setInterpolator(mInterpolator[random.nextInt(4)]);
        finalSet.setTarget(target);
        return finalSet;
    }

    private AnimatorSet getEnterAnimator(final View target) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha, scaleX, scaleY);
        enter.setTarget(target);
        return enter;
    }

    private ValueAnimator getBezierValueAnimator(View target) {
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(2), getPointF(1));
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(UiUtils.dip2px(120), mHeight - dHeight - UiUtils.dip2px(47)), new PointF(random.nextInt(getWidth()), 0));
        animator.addUpdateListener(new BezierListener(target));
        animator.setTarget(target);
        animator.setDuration(3000);
        return animator;
    }

    private PointF getPointF(int scale) {
        PointF pointF = new PointF();
        pointF.x = random.nextInt((mWidth));
        pointF.y = random.nextInt((mHeight)) / scale;
        return pointF;
    }

    private class BezierListener implements ValueAnimator.AnimatorUpdateListener {
        private View target;

        public BezierListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            target.setAlpha(1 - animation.getAnimatedFraction() + 0.2f);
        }
    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView((target));
        }
    }
}
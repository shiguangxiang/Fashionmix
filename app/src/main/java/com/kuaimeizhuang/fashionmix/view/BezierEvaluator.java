package com.kuaimeizhuang.fashionmix.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * <p>注释类</p>
 * Created on 17/5/26.
 *
 * @author Shi GuangXiang.
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF pointF1;
    private PointF pointF2;

    public BezierEvaluator(PointF pointF1, PointF pointF2) {
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

        float leftFraction = 1.0f - fraction;
        PointF point = new PointF();
        //贝塞尔三次方公式
        point.x = leftFraction * leftFraction * leftFraction * (startValue.x)
                + 3 * leftFraction * leftFraction * fraction * (pointF1.x)
                + 3 * leftFraction * fraction * fraction * (pointF2.x)
                + fraction * fraction * fraction * (endValue.x);


        point.y = leftFraction * leftFraction * leftFraction * (startValue.y)
                + 3 * leftFraction * leftFraction * fraction * (pointF1.y)
                + 3 * leftFraction * fraction * fraction * (pointF2.y)
                + fraction * fraction * fraction * (endValue.y);

        return point;
    }
}

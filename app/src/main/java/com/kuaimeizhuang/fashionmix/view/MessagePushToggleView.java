package com.kuaimeizhuang.fashionmix.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;

/**
 * <p>消息推送设置</p>
 * Created on 17/5/23.
 *
 * @author Shi GuangXiang.
 */

public class MessagePushToggleView extends View implements View.OnClickListener {
    //开关的背景
    private Bitmap backgroundBitmap;
    //滑动bitmap
    private Bitmap slidingBitmap;
    private int slidingLeftMax;
    private Paint paint;
    private int slidingLeft;
    private OnToggleSwitch onToggleSwitch;

    public MessagePushToggleView(Context context) {
        super(context);
    }

    /**
     * 如果在布局文件使用该类，将会用这个构造方法
     *
     * @param context 上下文
     * @param attrs   自定义属性
     */
    public MessagePushToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    /**
     * 初始化
     */
    private void initViews() {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iv_toggle_bg);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iv_toggle_sw);
        slidingLeftMax = backgroundBitmap.getWidth() - slidingBitmap.getWidth() + 4;
        slidingLeft = slidingLeftMax;
        paint = new Paint();
        paint.setAntiAlias(true);
        setOnClickListener(this);
    }

    /**
     * 视图的测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        canvas.drawBitmap(slidingBitmap, slidingLeft, 0, paint);
    }

    private boolean isOpen = false;
    private boolean isEnableClick = true;

    @Override
    public void onClick(View view) {
        if (isEnableClick) {
            isOpen = !isOpen;
            flushView();
        }
    }

    private void flushView() {
        if (isOpen) {
            backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toggle_witee);
            slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toggle_wite);
            slidingLeft = 0;
        } else {
            slidingLeft = slidingLeftMax;
            backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iv_toggle_bg);
            slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iv_toggle_sw);
        }
        if (null != onToggleSwitch){
            onToggleSwitch.onToggleSwitch(!isOpen);
        }
        invalidate();//会导致onDraw()执行
    }

    private float startX;
    private float lastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                //记住按下的坐标
                lastX = startX = event.getX();
                isEnableClick = true;
                break;
            case MotionEvent.ACTION_MOVE://移动
                //计算结束值
                float endX = event.getX();
                //计算偏移量
                float distanceX = endX - startX;
                slidingLeft += distanceX;
                if (slidingLeft < 0) {
                    slidingLeft = 0;
                } else if (slidingLeft > slidingLeftMax) {
                    slidingLeft = slidingLeftMax;
                }
                invalidate();
                //数据还原
                startX = event.getX();

                if (Math.abs(endX - lastX) > 8) {
                    isEnableClick = false;
                }
                break;
            case MotionEvent.ACTION_UP://抬起
                if (!isEnableClick) {
                    if (slidingLeft > slidingLeftMax / 2) {
                        isOpen = true;
                    } else {
                        isOpen = false;
                    }
                    flushView();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 开关的回调
     */
    public interface OnToggleSwitch{
        void onToggleSwitch(boolean isOpen);
    }

    /**
     * 设置开关回调
     * @param onToggleSwitch 回调接口
     */
    public void setOnToggleSwitch(OnToggleSwitch onToggleSwitch){
        this.onToggleSwitch = onToggleSwitch;
    }

    /**
     * 设置开关,设置为true是为关，false是为开
     * @param isOpen 是否打开或者关闭
     */
    public void setToggleSwitchOpen(boolean isOpen){
        this.isOpen = isOpen;
        if (isOpen) {
            backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toggle_witee);
            slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toggle_wite);
            slidingLeft = 0;
        } else {
            slidingLeft = slidingLeftMax;
            backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iv_toggle_bg);
            slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iv_toggle_sw);
        }
        invalidate();//会导致onDraw()执行
    }

}

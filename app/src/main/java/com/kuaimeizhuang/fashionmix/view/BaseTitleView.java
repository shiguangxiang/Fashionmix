package com.kuaimeizhuang.fashionmix.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.config.PageManager;
import com.kuaimeizhuang.fashionmix.databinding.BaseTitleViewBinding;

/**
 * <p>自定义头部</p>
 * Created on 17/4/1.
 *
 * @author Shi GuangXiang.
 */

public class BaseTitleView extends LinearLayout {

    private BaseTitleViewBinding mBaseTitleViewBinding;
    private boolean isBack = true;

    public BaseTitleViewBinding getBaseTitleViewBinding() {
        return mBaseTitleViewBinding;

    }

    public BaseTitleView(Context context) {
        super(context);
        init(context);
    }

    public BaseTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBaseTitleViewBinding = DataBindingUtil.inflate((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), R.layout.base_title_view, this, false);
        addView(mBaseTitleViewBinding.getRoot());
        if (isBack) {
            mBaseTitleViewBinding.tvTitleLeft.setOnClickListener(v -> {
                PageManager.getCurrentActivity().finish();
            });
        }
    }


    public void showHeader() {
        setVisibility(View.VISIBLE);
    }

    public void hideHeader() {
        setVisibility(View.GONE);
    }

    public void hideLeftIcon() {
        mBaseTitleViewBinding.tvTitleLeft.setVisibility(GONE);
    }

    public void hideReight(){
        mBaseTitleViewBinding.tvTitleRight.setVisibility(GONE);
    }

    public void setHeaderTitle(String title) {
        mBaseTitleViewBinding.tvTitle.setText(title);
    }

    public String getHeaderTitle() {
        return mBaseTitleViewBinding.tvTitle.getText().toString().trim();
    }


    public void setHeaderRightIcon(int drawableId) {
        mBaseTitleViewBinding.tvTitleRight.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0);
        showHeaderRight();
    }

    public void setHeaderRightBackground(int background) {
        mBaseTitleViewBinding.tvTitleRight.setBackgroundResource(background);
    }

    public void showHeadRightImage(){
        mBaseTitleViewBinding.tvImageRight.setVisibility(VISIBLE);
    }

    public ImageView getRightImage(){
        return mBaseTitleViewBinding.tvImageRight;
    }
    public void showHeaderRight() {
        mBaseTitleViewBinding.tvTitleRight.setVisibility(View.VISIBLE);
    }

    public void hideHeaderRight() {
        mBaseTitleViewBinding.tvTitleRight.setVisibility(View.GONE);
    }

    public void setRightIconListener(OnClickListener listener) {
        mBaseTitleViewBinding.tvTitleRight.setOnClickListener(listener);
    }


    public void setHeaderRightText(String text) {
        if (!text.isEmpty()) {
            showHeaderRight();
        }
        mBaseTitleViewBinding.tvTitleRight.setText(text);
    }

    public void setLeftBackListener(boolean isBack, OnClickListener listener) {
        this.isBack = isBack;
        mBaseTitleViewBinding.tvTitleLeft.setOnClickListener(listener);
    }
}

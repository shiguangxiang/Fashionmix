package com.kuaimeizhuang.fashionmix.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.App;

import java.io.InputStream;

/**
 * <p>图片加载工具类</p>
 * Created on 17/5/12.
 *
 * @author Shi GuangXiang.
 */

public class GlideUtils {
    /**
     * 圆形类图片加载  例如头像等类
     *
     * @param imageView 头像view
     * @param url       头像url
     */
    @BindingAdapter("circleImageUrl")
    public static void imageLoader(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext()).load(url).asBitmap().fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);

                        }

                    });

            LogUtils.d("circleImageUrl: " + url);
        } else {
            imageView.setImageResource(0);
        }
    }

    /**
     * 16:9图片加载  例如各种列表页
     *
     * @param imageView 图片view
     * @param url       图片url
     */
    @BindingAdapter("xhdpiImageUrl")
    public static void imageLoaderXhdpi(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url).crossFade()
                    .into(imageView);
            LogUtils.d("xhdpiImageUrl: " + url);
        } else {
            imageView.setImageResource(0);
        }
    }


    /**
     * 1:1图片加载  例如各种列表页
     *
     * @param imageView 图片view
     * @param url       图片url
     */
    @BindingAdapter("squareImageUrl")
    public static void imageLoadersquare(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
            LogUtils.d("squareImageUrl: " + url);
        } else {
            imageView.setImageResource(0);
        }
    }


    /**
     * 首页banner图片加载
     *
     * @param imageView 图片view
     * @param url       图片url
     */
    @BindingAdapter("bannerImageUrl")
    public static void imageLoaderBanner(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url).crossFade()
                    .into(imageView);
            LogUtils.d("bannerImageUrl: " + url);
        } else {
            imageView.setImageResource(0);
        }
    }


    /**
     * 图片模糊效果
     *
     * @param imageView 图片view
     * @param url       图片url
     */
    @BindingAdapter("blurImageUrl")
    public static void imageLoaderBlur(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext()).load(url).asBitmap().fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new BitmapImageViewTarget(imageView) {

                        @Override
                        protected void setResource(Bitmap resource) {
                            Bitmap bitmap = BlurUtil.fastblur(imageView.getContext(), resource, 40);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            imageView.setImageBitmap(bitmap);
                        }

                    });
            LogUtils.d("blurImageUrl: " + url);
        } else {
            InputStream is = App.getContext().getResources().openRawResource(R.drawable.lv_login_head);
            Bitmap mBitmap = BitmapFactory.decodeStream(is);
            Bitmap bitmap = BlurUtil.fastblur(imageView.getContext(), mBitmap, 40);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(bitmap);
        }
    }


    /**
     *
     * @param imageView 图片view
     * @param url       图片url
     */
    @BindingAdapter("userVenda")
    public static void imageLoaderVenda(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext()).load(url)
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(imageView) {

                        @Override
                        protected void setResource(Bitmap resource) {
                            Bitmap bitmap = imageCrop(resource, 16, 9, false);
                            imageView.setScaleType(ImageView.ScaleType.CENTER);
                            imageView.setImageBitmap(bitmap);
                        }

                    });
            LogUtils.d("userVenda: " + url);
        }
    }


    @BindingAdapter("glideFitCenterLoader")
    public static void glideFitCenter(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);

            LogUtils.d("glideFitCenterLoader: " + url);
        } else {
            imageView.setImageResource(0);
        }
    }

    /**
     * 按照一定的宽高比例裁剪图片
     *
     * @param bitmap     要裁剪的图片
     * @param num1       长边的比例
     * @param num2       短边的比例
     * @param isRecycled 是否回收原图片
     * @return 裁剪后的图片
     */
    public static Bitmap imageCrop(Bitmap bitmap, int num1, int num2, boolean isRecycled) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int retX, retY;
        int nw, nh;
        if (w > h) {
            if (h > w * num2 / num1) {
                nw = w;
                nh = w * num2 / num1;
                retX = 0;
                retY = (h - nh) / 2;
            } else {
                nw = h * num1 / num2;
                nh = h;
                retX = (w - nw) / 2;
                retY = 0;
            }
        } else {
            if (w > h * num2 / num1) {
                nh = h;
                nw = h * num2 / num1;
                retY = 0;
                retX = (w - nw) / 2;
            } else {
                nh = w * num1 / num2;
                nw = w;
                retY = (h - nh) / 2;
                retX = 0;
            }
        }
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null, false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();//回收原图片
            bitmap = null;
        }
        return bmp;
    }

}

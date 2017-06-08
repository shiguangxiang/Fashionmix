package com.kuaimeizhuang.fashionmix.model.my;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.kuaimeizhuang.fashionmix.api.AccountApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.User;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentMyPagerBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.utils.AppSystemUtil;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import rx.Observable;

/**
 * <p>登录model</p>
 * Created on 17/5/22.
 *
 * @author Shi GuangXiang.
 */

public class MyPagerViewModel extends BaseViewModel implements PlatformActionListener {
    private AccountApi api = (AccountApi) RetrofitFactory.create(AccountApi.class);
    private onCompleteListenter completeListenter;

    public MyPagerViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
    }

    @Override
    public void onActivityDestroy() {

    }

    /**
     * 初始化我的界面数据
     *
     * @param binding binding
     */
    public void initMyPagerData(FragmentMyPagerBinding binding) {
        Observable<HttpResult<User>> usersProfile = api.getUsersProfile(UiUtils.getUserId());
        addSubscription(usersProfile, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                User user = (User) object;
                binding.setUser(user);
                GlideUtils.imageLoaderBlur(binding.ivBlurImageUrl, user.getBackground_url());
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 分享到平台
     *
     * @param name 平台名称
     */
    public void sharePlatform(String name) {
        String url = Constants.share.sharefriends + "os=" +
                "android&" + "channel=" + AppSystemUtil.getUmengChannel() + "&from_userid=" +
                UiUtils.getUserId();
        String mImageUrl = Constants.share.imageUrl;
        String titleUrl = Constants.share.titleUrl;
        String site = Constants.share.site;
        Platform.ShareParams sp = new Platform.ShareParams();
        String title = Constants.share.title;
        if (name.equals("微博")) {
            //2、设置分享内容
            sp.setTitle(Constants.share.title);//分享标题
            if (title.length() > 30) {
                String substring = title.substring(0, 30);
                sp.setText(substring + "..." + url); //分享文本
            } else {
                sp.setText(title + url); //分享文本

            }
            sp.setImageUrl(mImageUrl);//分享图片url
            sp.setUrl(title + url);   //分享链接
//                UIUtils.showToastShort(url);
            sp.setTitleUrl(titleUrl);
            sp.setSite(site);//分享来源
            //设置回调事件
            setShareListener(SinaWeibo.NAME, sp);
        } else if (name.equals("微信")) {
            sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
            setShareData(title, titleUrl, "", mImageUrl, url, site, sp);
            setShareListener(Wechat.NAME, sp);

        } else if (name.equals("朋友圈")) {
            sp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
            setShareData(title, titleUrl, title, mImageUrl, url, site, sp);
            setShareListener(WechatMoments.NAME, sp);
        } else if (name.equals("QQ好友")) {
            setShareData(title, titleUrl, "", mImageUrl, url, site, sp);
            setShareListener(QQ.NAME, sp);

        } else if (name.equals("QQ空间")) {
            setShareData(title, titleUrl, title, mImageUrl, url, site, sp);
            setShareListener(QZone.NAME, sp);
        }
    }

    private void setShareData(String title, String titleUrl, String text, String imageUrl, String url, String site, Platform.ShareParams sp) {
        sp.setTitle(title);//分享标题
        sp.setText(text); //分享文本
        sp.setImageUrl(imageUrl);//分享图片url
        sp.setUrl(url);   //分享链接
        sp.setTitleUrl(titleUrl);
        sp.setSite(site);//分享来源
    }

    private void setShareListener(String name, Platform.ShareParams sp) {
        Platform platform = ShareSDK.getPlatform(name);
        platform.setPlatformActionListener(this); // 设置分享事件回调
        platform.share(sp);
        ShareSDK.getPlatform(name);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        LogUtils.d("abc " + "进入到分享完成");
        String platformName = "";
        if (platform.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是新浪微博
            handler.sendEmptyMessage(1);
            platformName = Constants.LoginType.WB;

        } else if (platform.getName().equals(Wechat.NAME)) {
            handler.sendEmptyMessage(2);
            platformName = Constants.LoginType.WX;

        } else if (platform.getName().equals(WechatMoments.NAME)) {
            handler.sendEmptyMessage(3);
            platformName = Constants.LoginType.WX;

        } else if (platform.getName().equals(QQ.NAME)) {
            handler.sendEmptyMessage(4);
            platformName = Constants.LoginType.QQ;

        } else if (platform.getName().equals(WechatFavorite.NAME)) {
            handler.sendEmptyMessage(5);
            platformName = Constants.LoginType.QQ;

        } else if (platform.getName().equals(QZone.NAME)) {
            handler.sendEmptyMessage(6);
            platformName = Constants.LoginType.QQ;
        }
        if (completeListenter != null) {
            completeListenter.onShareComplete(platformName);
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        Message msg = new Message();
        msg.what = 8;
        msg.obj = throwable.getMessage();
        handler.sendMessage(msg);
        LogUtils.e("share filed :" + throwable.getMessage());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        handler.sendEmptyMessage(7);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    UiUtils.showToastLong("微博分享成功");
                    break;
                case 2:
                    UiUtils.showToastLong("微信分享成功");
                    break;
                case 3:
                    UiUtils.showToastLong("朋友圈分享成功");
                    break;
                case 4:
                    UiUtils.showToastLong("QQ分享成功");
                    break;
                case 5:
                    UiUtils.showToastLong("微信收藏分享成功");
                    break;
                case 6:
                    UiUtils.showToastLong("QQ空间分享成功");
                    break;
                case 7:
                    UiUtils.showToastLong("取消分享");
                    break;
                case 8:
                    UiUtils.showToastLong("分享失败" + msg.obj);
                    LogUtils.d("abc " + "分享失败啊" + msg.obj);
                    break;
                default:
                    break;
            }
        }

    };

    public interface onCompleteListenter {
        void onShareComplete(String platformName);
    }

    public void setOnCompleteListener(onCompleteListenter completeListener) {
        this.completeListenter = completeListener;
    }

}

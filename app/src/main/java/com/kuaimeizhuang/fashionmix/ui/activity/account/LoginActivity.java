package com.kuaimeizhuang.fashionmix.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.EventBusBean;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.ActivityLoginBinding;
import com.kuaimeizhuang.fashionmix.model.account.LoginViewModel;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.SnackbarUtil;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.kuaimeizhuang.fashionmix.view.UsersAPI;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>登录页面</p>
 * Created on 17/5/12.
 *
 * @author Shi GuangXiang.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private Tencent mTencent;
    private IUiListener mLoginListener;
    public static LoginViewModel mViewModel;
    public IWXAPI mWeixinAPI;

    @Override
    protected View initView() {
        binding = (ActivityLoginBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_login);
        mWeixinAPI = WXAPIFactory.createWXAPI(this, Constants.weixin.APP_ID, true);
        mWeixinAPI.registerApp(Constants.weixin.APP_ID);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
        mViewModel = new LoginViewModel(getBaseBinding(), mPager, this);
    }

    @Override
    protected void reloadData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
        mViewModel = new LoginViewModel(getBaseBinding(), mPager, this);
    }

    @Override
    protected void initListener() {
        binding.ivLoginWb.setOnClickListener(this);
        binding.ivLoginQq.setOnClickListener(this);
        binding.ivLoginWx.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_wb:
                weiboLogin();
                break;
            case R.id.iv_login_qq:
                if (UiUtils.isFastDoubleClick()) {
                    return;
                }
                qqLogin();
                break;

            case R.id.iv_login_wx:
                if (UiUtils.isFastDoubleClick()) {
                    return;
                }
                weixinLogin();
                break;
        }
    }

    /**
     * 微信登录
     */
    private void weixinLogin() {
        if (!mWeixinAPI.isWXAppInstalled()) {
            UiUtils.showToastLong("您没有安装微信");
            return;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "beauty";
        mWeixinAPI.sendReq(req);
    }

    /**
     * QQ登录
     */
    private void qqLogin() {
        mTencent = Tencent.createInstance(Constants.QQ.TencentAPP_ID, this.getApplicationContext());
        mLoginListener = new IUiListener() {
            @Override
            public void onComplete(Object object) {
                JSONObject jsResp = (JSONObject) object;
                try {
                    if (jsResp.has("access_token")) {
                        String access_token = jsResp.getString("access_token");
                        String openid = jsResp.getString("openid");
                        QQToken qqToken = mTencent.getQQToken();
                        qqToken.setAccessToken(access_token, "1103957156");
                        qqToken.setOpenId(openid);
                        UserInfo info = new UserInfo(LoginActivity.this, qqToken);
                        info.getUserInfo(this);
                    } else if (jsResp.has("nickname") && jsResp.has("figureurl_qq_2")) {
                        String nickname = jsResp.getString("nickname");
                        String avatar = jsResp.getString("figureurl_qq_2");
                        mViewModel.loginByThird(Constants.LoginType.QQ, mTencent.getOpenId(), nickname, avatar);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                SnackbarUtil.showSnackBar(getBaseBinding(), "登录失败");
                LogUtils.e(uiError.toString());
            }

            @Override
            public void onCancel() {
                SnackbarUtil.showSnackBar(getBaseBinding(), "取消QQ登录");
            }
        };

        mTencent.login(LoginActivity.this, "all", mLoginListener);
    }


    /**
     * 微博登录
     */
    private void weiboLogin() {
        mAuthInfo = new AuthInfo(this, Constants.weibo.APP_KEY,
                Constants.weibo.REDIRECT_URL, "all");
        mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
        mSsoHandler.authorize(new WeiboAuthListener() {
            @Override
            public void onComplete(Bundle values) {
                mAccessToken = Oauth2AccessToken.parseAccessToken(values);
                if (mAccessToken.isSessionValid()) {
                    UsersAPI ui = new UsersAPI(LoginActivity.this, Constants.weibo.APP_KEY, mAccessToken);
                    long uid = Long.parseLong(mAccessToken.getUid());
                    ui.show(uid, new RequestListener() {
                        @Override
                        public void onComplete(String response) {
                            if (!TextUtils.isEmpty(response)) {
                                JSONObject data;
                                try {
                                    data = new JSONObject(response);
                                    String id = data.getString("idstr");
                                    String nickname = data.getString("screen_name");
                                    String avatar = data.getString("profile_image_url");
                                    if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(avatar)) {
                                        mViewModel.loginByThird(Constants.LoginType.WB, id, nickname, avatar);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onWeiboException(WeiboException e) {
                            LogUtils.e(e.toString());
                        }
                    });
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                SnackbarUtil.showSnackBar(getBaseBinding(), "微博登录出错" + e.toString());
                UiUtils.showToastLong("微博登录出错" + e.toString());
            }

            @Override
            public void onCancel() {
                UiUtils.showToastLong("取消微博登录");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mLoginListener);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

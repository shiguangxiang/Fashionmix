package com.kuaimeizhuang.fashionmix.model.account;

import android.app.Activity;
import android.app.ProgressDialog;

import com.kuaimeizhuang.fashionmix.api.AccountApi;
import com.kuaimeizhuang.fashionmix.base.App;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.EventBusBean;
import com.kuaimeizhuang.fashionmix.bean.LoginSuccessBean;
import com.kuaimeizhuang.fashionmix.bean.User;
import com.kuaimeizhuang.fashionmix.bean.WeiXinBean;
import com.kuaimeizhuang.fashionmix.bean.WeiXinUserBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.config.PageManager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

/**
 * <p>注释类</p>
 * Created on 17/5/22.
 *
 * @author Shi GuangXiang.
 */

public class LoginViewModel extends BaseViewModel{
    private final ProgressDialog mProgressDialog;
    private AccountApi api = (AccountApi) RetrofitFactory.create(AccountApi.class);

    public LoginViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onActivityDestroy() {

    }


    /**
     * 第三方登陆
     *
     * @param type     登录类型
     * @param id       用户ID
     * @param nickname 用户昵称
     * @param avatar   用户头像
     */
    public void loginByThird(String type, String id, String nickname, String avatar) {
        mProgressDialog.setMessage("登录中...");
        mProgressDialog.show();
        Observable<HttpResult<LoginSuccessBean>> observable = api.thirdLogin(type, id, nickname, avatar);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                LoginSuccessBean loginSuccessBean = (LoginSuccessBean) object;
                mProgressDialog.dismiss();
                loginSuccess(loginSuccessBean);
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }
    /**
     * 登陆成功后处理
     *
     * @param loginSuccessBean 登陆成功后的数据
     */
    private void loginSuccess(LoginSuccessBean loginSuccessBean) {
        if (loginSuccessBean != null) {
            String user_token = loginSuccessBean.getUser_token();
            UiUtils.setToken(user_token);//保存用户登录token
            User user = loginSuccessBean.getUser();
            if (user != null) {
                int id = user.getId();
                String phone_number = user.getPhone_number();
                UiUtils.setUserId(id + "");//保存用户ID
                UiUtils.setUserPhone(phone_number);
                UiUtils.setLogin(true);//设置登录状态为true
                UiUtils.setUserGender(2);
                UiUtils.setUserNickName(user.getNickname());
            }
//            setJPushAlias();
            if (loginSuccessBean.is_register()) {
//                Intent intent = new Intent();
//                String amount = loginSuccessBean.getRedbag().getAmount();
//                intent.putExtra(Constants.BundleConstants.TAG, StringUtils.formatDoubleTwo(Double.valueOf(amount)));
//                PageManager.getCurrentActivity().setResult(777, intent);
                PageManager.getCurrentActivity().finish();
            } else {
                PageManager.getCurrentActivity().finish();
            }
            EventBus.getDefault().post(new EventBusBean("登录成功"));
        }
    }

    /**
     * 微信拉取授权
     *
     * @param code
     */
    public void OnWechatLogin(String code) {
        mProgressDialog.setMessage("正在唤起微信...");
        mProgressDialog.show();
        Call<WeiXinBean> call = api.weixinGetUser(Constants.weixin.APP_ID, Constants.weixin
                .AppSecret, "authorization_code", code);
        call.enqueue(new Callback<WeiXinBean>() {
            @Override
            public void onResponse(Call<WeiXinBean> call, Response<WeiXinBean> response) {
                mProgressDialog.dismiss();
                String access_token = response.body().getAccess_token();
                String openid = response.body().getOpenid();
                getWeixinUserInfo(access_token, openid);
            }

            @Override
            public void onFailure(Call<WeiXinBean> call, Throwable t) {
                mProgressDialog.dismiss();
                LogUtils.e(t.getMessage());
            }
        });
    }

    /**
     * 微信获取用户资料
     *
     * @param access_token
     * @param openid
     */
    private void getWeixinUserInfo(String access_token, final String openid) {
        Call<WeiXinUserBean> callUser = api.weixinUserInfo(access_token, openid);
        callUser.enqueue(new Callback<WeiXinUserBean>() {
            @Override
            public void onResponse(Call<WeiXinUserBean> call, Response<WeiXinUserBean> response) {
                WeiXinUserBean body = response.body();
                String nickname = body.getNickname();
                String headimgurl = body.getHeadimgurl();
                loginByThird(Constants.LoginType.WX, openid, nickname, headimgurl);
            }

            @Override
            public void onFailure(Call<WeiXinUserBean> call, Throwable t) {
                LogUtils.e(t.getMessage());
            }
        });
    }


}

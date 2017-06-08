package com.kuaimeizhuang.fashionmix.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;

/**
 * <p>注释类</p>
 * Created on 17/6/5.
 *
 * @author Shi GuangXiang.
 */

public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.weixin.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtils.d("onLoginFinish, errCode = " + resp.errCode);
        switch (resp.getType()) {
            case ConstantsAPI.COMMAND_SENDAUTH:
                String result;
                if (resp instanceof SendAuth.Resp) {
                    String code = ((SendAuth.Resp) resp).code;
                    if (!TextUtils.isEmpty(code)) {
                        LoginActivity.mViewModel.OnWechatLogin(code);
                    }
                }//登录回调
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        result = "登录成功";
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        result = "用户取消";
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        result = "登录失败";
                        break;
                    default:
                        result = "未知错误";
                        break;
                }
                UiUtils.showToastLong(result);
                finish();
                break;
        }
        finish();
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(iLaunchMyself);
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }
}

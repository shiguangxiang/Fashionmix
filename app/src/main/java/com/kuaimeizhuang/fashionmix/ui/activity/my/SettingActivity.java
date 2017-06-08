package com.kuaimeizhuang.fashionmix.ui.activity.my;

import android.text.TextUtils;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.EventBusBean;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.config.PageManager;
import com.kuaimeizhuang.fashionmix.databinding.ActivitySettingBinding;
import com.kuaimeizhuang.fashionmix.retrofit.UpdateManager;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.DataCleanUtils;
import com.kuaimeizhuang.fashionmix.utils.SPUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.kuaimeizhuang.fashionmix.view.ActionSheetDialog;
import com.kuaimeizhuang.fashionmix.view.BeautyDialog;
import com.sina.weibo.sdk.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * <p>注释类</p>
 * Created on 17/5/22.
 *
 * @author Shi GuangXiang.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySettingBinding binding;

    @Override
    protected View initView() {
        binding = (ActivitySettingBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_setting);
        getBaseBinding().baseTitle.showHeader();
        getBaseBinding().baseTitle.setHeaderTitle("设置");
        int anInt = SPUtils.getInt(Constants.WIFI.NET_WORK_STATE);
        switch (anInt) {
            case 1:
                binding.tvPlayModel.setText("仅WIFI");
                break;
            case 2:
                binding.tvPlayModel.setText("3G/4G和WIFI");
                break;
            default:
                binding.tvPlayModel.setText("关闭");
                break;
        }

        setCacheSize();

        return binding.getRoot();
    }

    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    @Override
    protected void reloadData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    @Override
    protected void initListener() {
        binding.btnExitAccount.setOnClickListener(this);
        binding.tvAutoPlay.setOnClickListener(this);
        binding.tvMsgPushKmz.setOnClickListener(this);
        binding.tvClearCache.setOnClickListener(this);
        binding.tvVersionUpdata.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //退出账号
            case R.id.btn_exit_account:
                exitAccount();
                break;
            //设置视频自动播放
            case R.id.tv_auto_play:
                setAutoPlayMode();
                break;
            //消息推送
            case R.id.tv_msg_push_kmz:
                ActivitySwitchUtil.switchActivity(PushSettingActivity.class);
                break;
            //清除缓存
            case R.id.tv_clear_cache:
                clearCache();
                break;
            //版本更新
            case R.id.tv_version_updata:
                boolean newVersion = checkHasNewVersion();
                if (!newVersion) {
                    binding.tvIsNewVersion.setText("已经是最新版本!");
                }
                break;
        }
    }

    /**
     * 检查更新
     */
    private boolean checkHasNewVersion() {
        String title = SPUtils.getString(Constants.UPDATE_TITLE);
        String msg = SPUtils.getString(Constants.UPDATE_DES);
        if (title == null) {
            title = "有新版本发布啦";
        }
        if (msg == null) {
            msg = "";
        }
        if (UiUtils.isForceUpdate()) {
            BeautyDialog dialog = new BeautyDialog(PageManager.getCurrentActivity()).builder();
            dialog.setTitle(title)
                    .setMsg(msg)
                    .setPositiveButton("立即升级", v ->
                            new UpdateManager().retrofitDownload()).show();
            return true;
        } else if (UiUtils.isNewVersioin()) {
            BeautyDialog dialog = new BeautyDialog(PageManager.getCurrentActivity()).builder();
            dialog.setTitle(title)
                    .setMsg(msg)
                    .setPositiveButton("更新", v ->
                            new UpdateManager().retrofitDownload()).setNegativeButton("忽略", v ->
                    dialog.dismiss())
                    .show();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 退出账户
     */
    private void exitAccount() {
//        setJPushAlias();
        UiUtils.setUserId(null);//删除用户ID
        UiUtils.setToken(null);//删除用户登录token
        UiUtils.setLogin(false);//设置登录状态为false
        UiUtils.setUserPhone(null);//删除用户手机号码
        UiUtils.setLong(Constants.VIDEO_TODAY, 0);
        UiUtils.setUserGender(2);
        UiUtils.setUserNickName(null);
        EventBus.getDefault().post(new EventBusBean("退出成功"));
        setResult(666);
        finish();
    }

    /**
     * 清除缓存
     */
    private void clearCache() {
        final BeautyDialog dialog = new BeautyDialog(SettingActivity.this).builder();
        dialog.setMsg("确定要清除缓存?")
                .setPositiveButton("", v -> {
                    DataCleanUtils.cleanCache(getApplication());
                    setCacheSize();
                    dialog.dismiss();
                })
                .setNegativeButton("", v -> dialog.dismiss())
                .show();

    }

    /**
     * 设置缓存
     */
    private void setCacheSize() {
        String cacheSize = DataCleanUtils.getCacheSize(getApplication());
        if (cacheSize != null && !TextUtils.isEmpty(cacheSize)) {
            binding.tvCacheSize.setText(cacheSize);
        }
    }

    /**
     * 选择自动播放方式
     */
    private void setAutoPlayMode() {
        ActionSheetDialog sheetDialog = new ActionSheetDialog(this).builder();
        sheetDialog.setTitle("选择播放方式")
                .addSheetItem("3G/4G和WIFI", ActionSheetDialog.SheetItemColor.Blue, which -> {
                    SPUtils.setInt(Constants.WIFI.NET_WORK_STATE, Constants.WIFI.NETWORK_OHTER);
                    binding.tvPlayModel.setText("3G/4G和WIFI");
                })
                .addSheetItem("仅WIFI", ActionSheetDialog.SheetItemColor.Blue, which -> {
                    SPUtils.setInt(Constants.WIFI.NET_WORK_STATE, Constants.WIFI.NETWORK_ONLY_WIFI);
                    binding.tvPlayModel.setText("仅WIFI");
                })
                .addSheetItem("关闭", ActionSheetDialog.SheetItemColor.Blue, which -> {
                    SPUtils.setInt(Constants.WIFI.NET_WORK_STATE, Constants.WIFI.NETWORK_NONE);
                    binding.tvPlayModel.setText("关闭");
                })
                .setCancelable(true).show();
    }
}

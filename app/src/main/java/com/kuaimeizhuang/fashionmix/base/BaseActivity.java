package com.kuaimeizhuang.fashionmix.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.config.PageManager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.ActivityMainBinding;
import com.kuaimeizhuang.fashionmix.receiver.CheckNetWorkReceiver;
import com.kuaimeizhuang.fashionmix.ui.activity.home.MainActivity;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;

/**
 * <p>BaseActivity 父类</p>
 * Created on 17/4/1.
 *
 * @author Shi GuangXiang.
 */

public abstract class BaseActivity extends AppCompatActivity implements CheckNetWorkReceiver.OnNetWorkListener {
    protected Activity mActivity;
    private ActivityBaseBinding mBaseBinding;
    protected LoadingPager mPager;
    private boolean needInit = true;
    private long mPreClickTime;
    private ProgressDialog mProgressDialog;
    private CheckNetWorkReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        registerBroadrecevicer();
        mActivity = this;
        PageManager.addPage(this);
        init();
    }

    /**
     * 注册广播
     */
    private void registerBroadrecevicer() {
        //获取广播对象
        receiver = new CheckNetWorkReceiver();
        //创建意图过滤器
        IntentFilter filter = new IntentFilter();
        //添加动作，监听网络
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    /**
     * 初始化
     */
    private void init() {
        mBaseBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_base, null, false);
        LinearLayout rootLayout = mBaseBinding.baseContainer;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (mPager == null) {
            mPager = new LoadingPager(this) {
                @Override
                protected View onCreateSuccessView() {
                    LogUtils.d("onCreateSuccessView");
                    View view = initView();
                    initListener();
                    return view;
                }

                @Override
                protected void onStartLoadData() {
                    LogUtils.d("onStartLoadData");
                    //注册广播监听
                    receiver.setOnNetWorkListener(BaseActivity.this);
                    initData();
                }

                @Override
                public void onDataLoading(LoadedResult result) {
                    super.onDataLoading(result);
                    LogUtils.d("onDataLoading");
                    if (result == LoadedResult.SUCCESS) {
                        if (needInit) {
                            needInit = false;
                        }
                    }
                }

                @Override
                protected void reloadData() {
                    super.reloadData();
                    LogUtils.d("reloadData");
                    BaseActivity.this.reloadData();
                }
            };
        } else {
            ViewParent parent = mPager.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mPager);
            }
        }
        mPager.loadData();
        rootLayout.addView(mPager, params);
        setContentView(mBaseBinding.getRoot());
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
    }

    /**
     * 初始化视图 子类必须实现
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 初始化事件 子类选择实现
     */
    protected void initListener() {

    }

    /**
     * 加载数据 子类必须实现
     */
    protected abstract void initData();

    /**
     * 重新加载数据  错误或者空页面点击
     */
    protected abstract void reloadData();

    public ActivityBaseBinding getBaseBinding() {
        return mBaseBinding;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this instanceof MainActivity) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                LogUtils.d("按了返回键");
//                exit();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 完成退出
     */
    public void exit() {
        //关闭广播
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        PageManager.AppExit(getBaseContext());
    }

    @Override
    public void onBackPressed() {
        if (this instanceof MainActivity) {
            // 拿着当前的时间戳--记录的时间搓 如何间隔2s以上,我就弹出toast提示
            if (System.currentTimeMillis() - mPreClickTime > 2000) {// 两次点击的时间间隔>2s
                Toast.makeText(getApplicationContext(), "再按一次,退出快美搭", Toast.LENGTH_SHORT).show();
                mPreClickTime = System.currentTimeMillis();
                return;
            } else {
                // 两次点击的时间间隔<2s
                // 完成退出
                exit();
            }
        }
        // 如果不是主页,点击就是finish效果
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        PageManager.removePage(this);
        BaseViewModel loading = mBaseBinding.getLoading();
        if (loading != null) {
            LogUtils.e("Activity destroy ...  perform clean work...");
            loading.onActivityDestroy();
        }
        /**
         * 关闭广播
         */
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();

    }

    protected boolean isTransparent() {
        return false;
    }

    public void showProgressDialog(String msg) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onNetWorkListener(boolean isNetWorkSuccess) {
        isNetWorkSuccess(isNetWorkSuccess);
    }

    public void isNetWorkSuccess(boolean isNetWorkSuccess) {

    }
}

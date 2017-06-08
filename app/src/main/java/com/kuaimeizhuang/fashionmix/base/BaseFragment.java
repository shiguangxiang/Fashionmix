package com.kuaimeizhuang.fashionmix.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentBaseBinding;
import com.kuaimeizhuang.fashionmix.receiver.CheckNetWorkReceiver;

import java.lang.reflect.Field;

/**
 * <p>Fragment 父类</p>
 * Created on 17/4/1.
 *
 * @author Stone.
 */
public abstract class BaseFragment extends Fragment implements CheckNetWorkReceiver.OnNetWorkListener {

    protected BaseActivity mActivity;
    protected LoadingPager mPager;
    protected LayoutInflater mInflater;
    private FragmentBaseBinding mBaseBinding;
    private CheckNetWorkReceiver receiver;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    public ActivityBaseBinding getBaseBinding() {
        return mActivity == null ? null : mActivity.getBaseBinding();
    }

    /**
     * 注册广播
     */
    private void registerBroadrecevicerFragment() {
        //获取广播对象
        receiver = new CheckNetWorkReceiver();
        //创建意图过滤器
        IntentFilter filter = new IntentFilter();
        //添加动作，监听网络
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        getActivity().registerReceiver(receiver, filter);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false);
        mInflater = inflater;
        registerBroadrecevicerFragment();
        LinearLayout layout = (LinearLayout) mBaseBinding.getRoot();
        if (mPager == null) {
            mPager = new LoadingPager(mActivity) {
                @Override
                protected View onCreateSuccessView() {
                    View view = initView();
                    registerBroadrecevicerFragment();
                    initListener();
                    return view;
                }

                @Override
                protected void onStartLoadData() {
                    receiver.setOnNetWorkListener(BaseFragment.this);
                    initData();
                }

                @Override
                protected void reloadData() {
                    BaseFragment.this.reloadData();
                }
            };
        } else {
            ViewParent parent = mPager.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mPager);
            }
            initTitleBar();
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.addView(mPager, params);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPager != null) {
            mPager.loadData();
        }
    }

    /**
     * 初始化view
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化事件,子类需要时覆写
     */
    protected void initListener() {
    }

    /**
     * 初始化titlebar
     */
    protected void initTitleBar() {

    }

    /**
     * 重新加载
     */
    protected abstract void reloadData();


    /**
     * 管理Fragment切换
     *
     * @param resourceId       要替换的资源id
     * @param targetFragment   要替换的目标Fragment
     * @param isAddToBackStack 是否添加到返回栈
     * @param bundle           替换Fragment时携带的参数
     */
    protected void replaceFragment(int resourceId, Fragment targetFragment, boolean isAddToBackStack, Bundle bundle) {
        if (getActivity() == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (bundle != null) {
            targetFragment.setArguments(bundle);

        }

        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(resourceId, targetFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 管理Fragment切换
     *
     * @param resourceId     要替换的资源id
     * @param targetFragment 要替换的目标Fragment
     */
    protected void replaceFragment(int resourceId, Fragment targetFragment) {
        replaceFragment(resourceId, targetFragment, false, null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //销毁
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //销毁
        if (receiver != null) {
//            getActivity().unregisterReceiver(receiver);
        }
    }

    @Override
    public void onNetWorkListener(boolean isNetWorkSuccess) {
        isNetWorkSuccess(isNetWorkSuccess);
    }

    /**
     * 判断是否连接网络
     * @param isNetWorkSuccess
     */
    public void isNetWorkSuccess(boolean isNetWorkSuccess){

    }
}

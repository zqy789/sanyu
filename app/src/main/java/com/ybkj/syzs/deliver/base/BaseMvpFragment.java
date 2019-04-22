package com.ybkj.syzs.deliver.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ybkj.syzs.deliver.MyApplication;
import com.ybkj.syzs.deliver.dagger.component.DaggerFragmentComponent;
import com.ybkj.syzs.deliver.dagger.component.FragmentComponent;
import com.ybkj.syzs.deliver.dagger.module.FragmentModule;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.module.auth.activity.LoginActivity;
import com.ybkj.syzs.deliver.ui.dialog.TipDialog;
import com.ybkj.syzs.deliver.utils.LogUtil;

import javax.inject.Inject;

/**
 * author：rongkui.xiao --2018/3/16
 * email：dovexiaoen@163.com
 * description:mvp模式下的fragment
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    @Inject
    public T presenter;
    private TipDialog loginCommonDialog;

    protected abstract void injectPresenter();

    public FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        injectPresenter();
        if (presenter != null) presenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
        if (loginCommonDialog != null && loginCommonDialog.isShowing()) loginCommonDialog.dismiss();
    }

    @Override
    public void onSuccess(int tag) {

    }

    @Override
    public void onError(String errorMsg) {
        LogUtil.i("接口请求失败" + ";errorMsg=" + errorMsg);
        if (isShowNetErrorDefaultToast()) toast(errorMsg);
    }

    /**
     * 登录过期失效
     */
    @Override
    public void onLoginError() {
        if (loginCommonDialog == null) {
            loginCommonDialog = new TipDialog(mContext);
            loginCommonDialog.setContentText("登录失效，请登录");
            loginCommonDialog.setOnConfirmButtonClickListener((Dialog dialog, View view) -> {
                ActivityManager.gotoActivity(mContext, LoginActivity.class);
                loginCommonDialog.dismiss();
            });
            loginCommonDialog.show();
        } else {
            if (!loginCommonDialog.isShowing()) loginCommonDialog.show();
        }
    }

    protected boolean isShowNetErrorDefaultToast() {
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (loginCommonDialog != null && loginCommonDialog.isShowing()) loginCommonDialog.dismiss();
    }
}

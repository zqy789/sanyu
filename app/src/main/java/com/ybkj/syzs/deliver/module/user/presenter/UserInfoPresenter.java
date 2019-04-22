package com.ybkj.syzs.deliver.module.user.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.user.view.IUserInfoView;
import com.ybkj.syzs.deliver.net.exception.HandlerException;

import javax.inject.Inject;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/22.
 * Email 15384030400@163.com
 */
public class UserInfoPresenter extends BaseRxPresenter<IUserInfoView> {
    @Inject
    public UserInfoPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        mView.logoutComplete();
    }

    @Override
    public void onError(String errorMsg) {
        mView.logoutComplete();
    }

    @Override
    public void onError(HandlerException.ResponseThrowable e, int tag) {
        mView.logoutComplete();
    }

    @Override
    public void onError(String errorMsg, int tag) {
        mView.logoutComplete();
    }

    public void logout() {
        sendHttpRequest(apiService.logout(), Constants.REQUEST_CODE_1, true);
    }
}

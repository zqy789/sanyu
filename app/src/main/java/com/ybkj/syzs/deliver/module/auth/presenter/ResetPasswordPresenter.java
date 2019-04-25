package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.PasswordReq;
import com.ybkj.syzs.deliver.bean.request.ResetPasswordReq;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.auth.view.ResetView;
import com.ybkj.syzs.deliver.utils.Base64Util;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import javax.inject.Inject;

/**
 * author : ywh
 * date : 2019/4/1 18:01
 * description :
 */
public class ResetPasswordPresenter extends BaseRxPresenter<ResetView> {

    @Inject
    public ResetPasswordPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case Constants.REQUEST_CODE_1:
                mView.resetSuccess();
                break;
            case Constants.REQUEST_CODE_2:
                mView.passwordPass();
                break;
        }
    }

    @Override
    public void onError(String errorMsg, int tag) {
        if (tag == Constants.REQUEST_CODE_2) {
            mView.passwordFail();
        } else {
            super.onError(errorMsg, tag);
        }
    }

    /**
     * 重置密码
     *
     * @param account
     * @param password
     */
    public void reSetPassword(String account, String sign, String password, String rePassword) {

        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(rePassword)) {
            ToastUtil.showShort("请再次输入密码");
            return;
        }
        if (!password.equals(rePassword)) {
            ToastUtil.showShort("两次输入密码不一致");
            return;
        }
        ResetPasswordReq req = new ResetPasswordReq();
        req.setUserAccount(account);
        req.setSign(sign);
        req.setPassword(Base64Util.getBase64(password));
        req.setRePassword(Base64Util.getBase64(rePassword));

        sendHttpRequest(apiService.forgetPass(req), Constants.REQUEST_CODE_1);
    }

    public void checkPasswordEasy(String password) {
        PasswordReq req = new PasswordReq();
        req.setPassword(Base64Util.getBase64(password));
        sendHttpRequest(apiService.checkPwd(req), Constants.REQUEST_CODE_2, false);
    }

}

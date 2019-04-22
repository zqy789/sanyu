package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.ResetPasswordReq;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.auth.view.ResetView;
import com.ybkj.syzs.deliver.utils.Base64Util;
import com.ybkj.syzs.deliver.utils.FormCheckUtil;

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
        mView.resetSuccess();
    }

    /**
     * 重置密码
     *
     * @param account
     * @param phone
     * @param code
     * @param password
     */
    public void reSetPassword(String account, String phone, String code, String password) {
        if (FormCheckUtil.phoneCheck(phone)) {
            return;
        }
        if (FormCheckUtil.checkAccountAndPwd(password)) {
            return;
        }
        ResetPasswordReq req = new ResetPasswordReq();
        req.setOperatorAccount(account);
        req.setNewPassword(Base64Util.getBase64(password));
        req.setRepeatPassword(Base64Util.getBase64(password));
        req.setValidateCode(code);
        req.setPhoneNumber(phone);
        sendHttpRequest(apiService.forgetPass(req), Constants.REQUEST_CODE_1);
    }
}

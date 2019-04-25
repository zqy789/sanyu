package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.ModifyReq;
import com.ybkj.syzs.deliver.bean.request.PasswordReq;
import com.ybkj.syzs.deliver.bean.request.PhoneNumberReq;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.auth.view.ModifyPasswordView;
import com.ybkj.syzs.deliver.utils.Base64Util;
import com.ybkj.syzs.deliver.utils.FormCheckUtil;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import javax.inject.Inject;

/**
 * author : ywh
 * date : 2019/4/1 21:08
 * description :
 */
public class ModifyPasswordPresenter extends BaseRxPresenter<ModifyPasswordView> {


    @Inject
    public ModifyPasswordPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        if (tag == Constants.REQUEST_CODE_1) {
            mView.modifySuccess();
        } else if (tag == Constants.REQUEST_CODE_2) {
            mView.PhoneCodeSuccess();
        } else if (tag == Constants.REQUEST_CODE_4) {
            mView.passPassword();
        }
    }

    @Override
    public void onError(String errorMsg, int tag) {
        if (tag == Constants.REQUEST_CODE_4) {
            mView.simplePassword(errorMsg);
        } else {
            super.onError(errorMsg, tag);
        }
    }

    /**
     * @param password
     * @param phone
     * @param code
     * @param oldPassword
     * @param password
     * @param newPasswordRe
     */
    public void modifyPsd(String phone, String code, String oldPassword, String password, String newPasswordRe) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort("请输入手机证码");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShort("请输入手机短信验证码");
            return;
        }

        if (TextUtils.isEmpty(oldPassword)) {
            ToastUtil.showShort("请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(newPasswordRe)) {
            ToastUtil.showShort("请再次输入新密码");
            return;
        }
        if (checkInput(password, newPasswordRe)) {
            return;
        }
        if (!password.equals(newPasswordRe)) {
            ToastUtil.showShort("两次输入密码不一致");
            return;
        }
        //发送请求
        ModifyReq req = new ModifyReq();
        req.setOldPassword(Base64Util.getBase64(oldPassword));
        req.setNewPassword(Base64Util.getBase64(password));
        req.setRepeatPassword(Base64Util.getBase64(newPasswordRe));
        req.setPhoneCode(code);
        sendHttpRequest(apiService.modifyPsd(req), Constants.REQUEST_CODE_1);
    }


    private boolean checkInput(String oldPsd, String newPsd) {
        if (TextUtils.isEmpty(oldPsd)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.auth_mine_old_password_hint));
            return true;
        }
        if (oldPsd.length() < 6) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.auth_input_old_password_length));
            return true;
        }
        if (TextUtils.isEmpty(newPsd)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.auth_mine_new_password_hint));
            return true;
        }
        if (newPsd.length() < 6) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.auth_input_new_password_length));
            return true;
        }
        return false;
    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    public void getVerificationCode(String phone) {
        if (FormCheckUtil.phoneCheck(phone)) return;
        PhoneNumberReq req = new PhoneNumberReq();
        req.setPhoneNumber(phone);
        //发送验证码请求
        sendHttpRequest(apiService.getVerificationCode(req), Constants.REQUEST_CODE_2);
    }

    /**
     * 密码校验
     *
     * @param password
     */
    public void checkoutPassword(String password) {
        PasswordReq passwordReq = new PasswordReq();
        passwordReq.setPassword(Base64Util.getBase64(password));
        sendHttpRequest(apiService.checkPwd(passwordReq), Constants.REQUEST_CODE_4, false);
    }

    public void passwordIsEquals(String password, String againPassword) {
        if (!password.equals(againPassword)) {
//            ToastUtil.showShort("两次输入密码不一致");
            mView.passwordInequalityPassword();
            return;
        } else {
            mView.passwordequalityPassword();
        }
    }
}

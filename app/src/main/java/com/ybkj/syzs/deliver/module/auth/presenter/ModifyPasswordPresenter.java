package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.ModifyReq;
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
            mView.getSmsSuccess();
        }

    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     */
    public void modifyPsd(String phoneCode, String oldPassword, String newPassword, String reNewPassword) {

        if (TextUtils.isEmpty(phoneCode)) {
            ToastUtil.showShort("请输入验证码");
            return;
        }
        if (checkInput(oldPassword, newPassword)) {
            return;
        }
        if (TextUtils.isEmpty(reNewPassword)) {
            ToastUtil.showShort("请再次输入新密码");
            return;
        }
        if (!reNewPassword.equals(newPassword)) {
            ToastUtil.showShort("两次输入密码不一致");
            return;
        }
        //发送请求
        ModifyReq req = new ModifyReq();
        req.setOldPassword(Base64Util.getBase64(oldPassword));
        req.setNewPassword(Base64Util.getBase64(newPassword));
        req.setRepeatPassword(Base64Util.getBase64(reNewPassword));
        req.setPhoneCode(phoneCode);
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
}

package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.PhoneNumberReq;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.auth.view.RetrieveView;
import com.ybkj.syzs.deliver.utils.FormCheckUtil;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import javax.inject.Inject;

/**
 * author : ywh
 * date : 2019/4/1 18:17
 * description :找回密码
 */
public class RetrievePresenter extends BaseRxPresenter<RetrieveView> {

    @Inject
    public RetrievePresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case Constants.REQUEST_CODE_1:
                ToastUtil.showShort(ResourcesUtil.getString(R.string.auth_verification_code_get_success));
                mView.verificationCodeChange();
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    public void getVerificationCode(String phone) {
        if (FormCheckUtil.phoneCheck(phone)) {
            return;
        }
        PhoneNumberReq req = new PhoneNumberReq();
        req.setPhoneNumber(phone);
        //发送验证码请求
        sendHttpRequest(apiService.getVerificationCode(req), Constants.REQUEST_CODE_1);
    }

    public boolean checkPhone(String account, String phoneNum, String phoneCode) {
        if (FormCheckUtil.accountCheck(account)) {
            return false;
        }
        if (FormCheckUtil.phoneCheck(phoneNum)) {
            return false;
        }
        if (FormCheckUtil.verificationCodeCheck(phoneCode)) {
            return false;
        }
        return true;
    }
}

package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.PhoneNumberReq;
import com.ybkj.syzs.deliver.bean.request.VerfyPhoneCodeReq;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.auth.view.IVerifyPhoneView;
import com.ybkj.syzs.deliver.utils.FormCheckUtil;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import javax.inject.Inject;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/11.
 * Email 15384030400@163.com
 */
public class VerifyPhonePresenter extends BaseRxPresenter<IVerifyPhoneView> {


    @Inject
    public VerifyPhonePresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        if (tag == Constants.REQUEST_CODE_1) {
            mView.getCodeSuccess();
        } else if (tag == Constants.REQUEST_CODE_2) {
            mView.verifySuccess();
        }

    }

      /*
    获取验证码
     */

    public void getPhoneCode(String phone) {
        if (FormCheckUtil.phoneCheck(phone)) {
            return;
        }
        PhoneNumberReq req = new PhoneNumberReq();
        req.setPhoneNumber(phone);
        sendHttpRequest(apiService.getVerificationCode(req), Constants.REQUEST_CODE_1);
    }

    /**
     * 修改  绑定手机号
     *
     * @param phone
     * @param phoneCode
     */
    public void verifyPhone(String phone, String phoneCode) {
        if (checkInput(phone, phoneCode)) {
            return;
        }
        VerfyPhoneCodeReq req = new VerfyPhoneCodeReq();
        req.setPhoneCode(phoneCode);
        req.setPhoneNumber(phone);
        sendHttpRequest(apiService.verifyPhone(req), Constants.REQUEST_CODE_2);
    }

    private boolean checkInput(String phone, String phoneCode) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.auth_input_phone));
            return true;
        }
        if (phone.length() != 11) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.auth_staff_phone_eleven));
            return true;
        }
        if (TextUtils.isEmpty(phoneCode)) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.auth_login_code_null));
            return true;
        }
        if (phoneCode.length() != 6) {
            ToastUtil.showShort(ResourcesUtil.getString(R.string.auth_login_code_min_six));
            return true;
        }
        return false;
    }
}

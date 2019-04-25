package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.PhoneNumberReq;
import com.ybkj.syzs.deliver.bean.request.SignReq;
import com.ybkj.syzs.deliver.bean.response.PhoneRes;
import com.ybkj.syzs.deliver.bean.response.SignRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.auth.view.IAccountBackPsdView;
import com.ybkj.syzs.deliver.utils.FormCheckUtil;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import javax.inject.Inject;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/24.
 * Email 15384030400@163.com
 */
public class AccountBackPsdPresenter extends BaseRxPresenter<IAccountBackPsdView> {

    @Inject
    public AccountBackPsdPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        if (tag == Constants.REQUEST_CODE_1) {
            mView.loadPhone((PhoneRes) response);
        } else if (tag == Constants.REQUEST_CODE_2) {
            ToastUtil.showShort("获取短信验证码成功");
            mView.getSmsSuccess();
        } else if (tag == Constants.REQUEST_CODE_3) {
            mView.loadSign(((SignRes) response).getSign());
        }
    }

    @Override
    public void onError(String errorMsg, int tag) {
        if (tag == Constants.REQUEST_CODE_1) {
            mView.accountError(errorMsg);
        } else {
            super.onError(errorMsg, tag);
        }
    }

    public void getPhone(String account) {
        sendHttpRequest(apiService.getPhone(account), Constants.REQUEST_CODE_1, false);
    }

    public void getSign(String account, String code) {
        SignReq req = new SignReq();
        req.setUserAccount(account);
        req.setPhoneCode(code);
        sendHttpRequest(apiService.getSign(req), Constants.REQUEST_CODE_3);
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

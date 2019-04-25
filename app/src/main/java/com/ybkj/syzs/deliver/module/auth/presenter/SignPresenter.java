package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.SignReq;
import com.ybkj.syzs.deliver.bean.response.SignRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.auth.view.ISignView;

import javax.inject.Inject;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/24.
 * Email 15384030400@163.com
 */
public class SignPresenter extends BaseRxPresenter<ISignView> {

    @Inject
    public SignPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        if (tag == Constants.REQUEST_CODE_1) {
            mView.loadSign(((SignRes) response).getSign());
        }
    }

    public void getSign(String account, String code) {
        SignReq req = new SignReq();
        req.setUserAccount(account);
        req.setPhoneCode(code);
        sendHttpRequest(apiService.getSign(req), Constants.REQUEST_CODE_1);
    }
}

package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.LoginReq;
import com.ybkj.syzs.deliver.bean.response.LoginRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.manager.UserDataManager;
import com.ybkj.syzs.deliver.module.auth.view.ILoginView;
import com.ybkj.syzs.deliver.utils.Base64Util;
import com.ybkj.syzs.deliver.utils.FormCheckUtil;
import com.ybkj.syzs.deliver.utils.SPHelper;

import javax.inject.Inject;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/3/26.
 * Email 15384030400@163.com
 */
public class LoginPresenter extends BaseRxPresenter<ILoginView> {

    @Inject
    public LoginPresenter(Context context) {
        super(context);
    }


    @Override
    public void onSuccess(Object response, int tag) {
        LoginRes loginRes = (LoginRes) response;
        UserDataManager.saveLoginInfo(loginRes);
        //登陆成功后将账号保存，便于下次到登录界面填充
        SPHelper.getInstance().set(Constants.LOGIN_NAME_KEY, loginRes.getOperatorAccount());
        mView.loginSuccess(loginRes);
    }


    public void login(String account, String password) {
        if (FormCheckUtil.accountCheck(account)) {
            return;
        }
        if (FormCheckUtil.checkAccountAndPwd(password)) {
            return;
        }
        LoginReq req = new LoginReq();
        req.setOperatorAccount(account);
        req.setPassword(Base64Util.getBase64(password));
        //发送请求
        sendHttpRequest(apiService.startLogin(req), Constants.REQUEST_CODE_1);
    }
}

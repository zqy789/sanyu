package com.ybkj.syzs.deliver.module.auth.view;

import com.ybkj.syzs.deliver.base.BaseView;
import com.ybkj.syzs.deliver.bean.response.AccountListRes;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/24.
 * Email 15384030400@163.com
 */
public interface IPhoneBackPsdView extends BaseView {
    void phoneError(String msg);

    void loadAccountList(AccountListRes res);

    void getSmsSuccess();

    void loadSign(String sign);
}
package com.ybkj.syzs.deliver.module.auth.view;


import com.ybkj.syzs.deliver.base.BaseView;
import com.ybkj.syzs.deliver.bean.response.LoginRes;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/3/26.
 * Email 15384030400@163.com
 */
public interface ILoginView extends BaseView {
    void loginSuccess(LoginRes response);
}

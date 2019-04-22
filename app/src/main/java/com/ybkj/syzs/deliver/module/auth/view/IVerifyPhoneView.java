package com.ybkj.syzs.deliver.module.auth.view;


import com.ybkj.syzs.deliver.base.BaseView;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/11.
 * Email 15384030400@163.com
 */
public interface IVerifyPhoneView extends BaseView {

    void getCodeSuccess();

    void verifySuccess();
}

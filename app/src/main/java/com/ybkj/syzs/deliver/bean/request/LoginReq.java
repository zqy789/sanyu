package com.ybkj.syzs.deliver.bean.request;

import com.ybkj.syzs.deliver.bean.BaseReq;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  登录请求实体类
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class LoginReq extends BaseReq {


    private String operatorAccount;
    private String password;

    public String getOperatorAccount() {
        return operatorAccount;
    }

    public void setOperatorAccount(String operatorAccount) {
        this.operatorAccount = operatorAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

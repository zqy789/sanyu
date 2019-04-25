package com.ybkj.syzs.deliver.bean.request;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/22.
 * Email 15384030400@163.com
 */
public class ResetPasswordReq {


    private String password;
    private String rePassword;
    private String userAccount;
    private String sign;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

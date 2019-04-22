package com.ybkj.syzs.deliver.bean.request;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/22.
 * Email 15384030400@163.com
 */
public class ResetPasswordReq {

    /**
     * operatorAccount : whx001
     * phoneNumber : 15954134739
     * validateCode : 123455
     * newPassword : 123123
     * repeatPassword : 123123
     */

    private String operatorAccount;
    private String phoneNumber;
    private String validateCode;
    private String newPassword;
    private String repeatPassword;

    public String getOperatorAccount() {
        return operatorAccount;
    }

    public void setOperatorAccount(String operatorAccount) {
        this.operatorAccount = operatorAccount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}

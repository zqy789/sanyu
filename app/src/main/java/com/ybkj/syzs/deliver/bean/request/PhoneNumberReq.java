package com.ybkj.syzs.deliver.bean.request;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/17.
 * Email 15384030400@163.com
 */
public class PhoneNumberReq {
    private String phoneNumber;
    private String phoneAreaCode = "+86";

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }
}

package com.ybkj.syzs.deliver.bean.request;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/22.
 * Email 15384030400@163.com
 */
public class VerfyPhoneCodeReq {

    /**
     * phoneCode : 123456
     * phoneNumber : 13112345678
     */

    private String phoneCode;
    private String phoneNumber;

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

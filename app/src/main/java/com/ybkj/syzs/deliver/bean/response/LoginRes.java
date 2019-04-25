package com.ybkj.syzs.deliver.bean.response;

import java.io.Serializable;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  登录返回实体类
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */

public class LoginRes implements Serializable {


    /**
     * phoneNumber : 13949175447
     * operatorAccount : whx001
     * token : 8EF238B5626E6480D9CFF4B122DEEE036129a0d4b558449baab311f823869273
     */

    private String phoneNumber;
    private String operatorAccount;
    private String token;
    private int simpleStatus;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOperatorAccount() {
        return operatorAccount;
    }

    public void setOperatorAccount(String operatorAccount) {
        this.operatorAccount = operatorAccount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSimpleStatus() {
        return simpleStatus;
    }

    public void setSimpleStatus(int simpleStatus) {
        this.simpleStatus = simpleStatus;
    }
}

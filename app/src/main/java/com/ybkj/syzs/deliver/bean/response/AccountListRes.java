package com.ybkj.syzs.deliver.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/24.
 * Email 15384030400@163.com
 */
public class AccountListRes implements Serializable {

    private List<String> userAccounts;

    public List<String> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<String> userAccounts) {
        this.userAccounts = userAccounts;
    }
}

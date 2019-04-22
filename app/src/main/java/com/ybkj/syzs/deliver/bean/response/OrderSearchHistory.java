package com.ybkj.syzs.deliver.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/22.
 * Email 15384030400@163.com
 */
public class OrderSearchHistory implements Serializable {

    private List<String> kewords;

    public List<String> getKewords() {
        return kewords;
    }

    public void setKewords(List<String> kewords) {
        this.kewords = kewords;
    }
}
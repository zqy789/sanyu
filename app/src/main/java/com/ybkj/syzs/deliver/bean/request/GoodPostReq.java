package com.ybkj.syzs.deliver.bean.request;

import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class GoodPostReq {
    /**
     * list : [{"orderGoodsNo":"38","goodsPublicCode":"10025VXTSKS0000052"},{"orderGoodsNo":"39","goodsPublicCode":"10025LVJJOF0000055"}]
     * expressName : 申通
     * expressOrderNumber : 1qqqw332cdq
     * orderNo : H19049PYDBTRJ6U
     */

    private String expressName;
    private String expressOrderNumber;
    private String orderNo;
    private List<ListBean> list;

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressOrderNumber() {
        return expressOrderNumber;
    }

    public void setExpressOrderNumber(String expressOrderNumber) {
        this.expressOrderNumber = expressOrderNumber;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * orderGoodsNo : 38
         * goodsPublicCode : 10025VXTSKS0000052
         */

        private String orderGoodsNo;
        private String goodsPublicCode;

        public String getOrderGoodsNo() {
            return orderGoodsNo;
        }

        public void setOrderGoodsNo(String orderGoodsNo) {
            this.orderGoodsNo = orderGoodsNo;
        }

        public String getGoodsPublicCode() {
            return goodsPublicCode;
        }

        public void setGoodsPublicCode(String goodsPublicCode) {
            this.goodsPublicCode = goodsPublicCode;
        }
    }
}

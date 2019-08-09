package com.ybkj.syzs.deliver.bean.response;

import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class OrderDetailRes {
    /**
     * order : {"addTime":1556250918000,"expressBoList":[{"expressName":"顺丰快递","expressOrderNumber":"SF1010626048608","expressTime":1564457188000,"operatorAccount":"dangchuancai","goods":[{"addTime":1556250918000,"expressName":"顺丰快递","expressOrderNumber":"SF1010626048608","expressTime":1564457188000,"goodsId":10000015,"goodsImg":"http://192.168.4.214:8080/fileservice/upload/goodsManageImage/20190424/2019042411313934865932385.png","goodsName":"三域XDQ410电源转换器","operatorAccount":"dangchuancai","orderGoodsNo":"BAHLWH6JLU","orderNo":"H1904EG9J9AYGCB","goodsPublicCode":"H1904EG9J9AYGCB","orderStatus":2}]}],"orderNo":"H1904EG9J9AYGCB","orderStatus":7,"receiverCity":"洛阳市","receiverCounty":"新安县","receiverDetail":"铁门镇计生站一楼核算站","receiverName":"梁玉兰","receiverPhone":"13525497292","receiverProvince":"河南省"}
     */

    private OrderBean order;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean {
        /**
         * addTime : 1556250918000
         * expressBoList : [{"expressName":"顺丰快递","expressOrderNumber":"SF1010626048608","expressTime":1564457188000,"operatorAccount":"dangchuancai","goods":[{"addTime":1556250918000,"expressName":"顺丰快递","expressOrderNumber":"SF1010626048608","expressTime":1564457188000,"goodsId":10000015,"goodsImg":"http://192.168.4.214:8080/fileservice/upload/goodsManageImage/20190424/2019042411313934865932385.png","goodsName":"三域XDQ410电源转换器","operatorAccount":"dangchuancai","orderGoodsNo":"BAHLWH6JLU","orderNo":"H1904EG9J9AYGCB","goodsPublicCode":"H1904EG9J9AYGCB","orderStatus":2}]}]
         * orderNo : H1904EG9J9AYGCB
         * orderStatus : 7
         * receiverCity : 洛阳市
         * receiverCounty : 新安县
         * receiverDetail : 铁门镇计生站一楼核算站
         * receiverName : 梁玉兰
         * receiverPhone : 13525497292
         * receiverProvince : 河南省
         */

        private long addTime;
        private String orderNo;
        private int orderStatus;
        private String receiverCity;
        private String receiverCounty;
        private String receiverDetail;
        private String receiverName;
        private String receiverPhone;
        private String receiverProvince;
        private List<ExpressBoListBean> expressBoList;

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getReceiverCity() {
            return receiverCity;
        }

        public void setReceiverCity(String receiverCity) {
            this.receiverCity = receiverCity;
        }

        public String getReceiverCounty() {
            return receiverCounty;
        }

        public void setReceiverCounty(String receiverCounty) {
            this.receiverCounty = receiverCounty;
        }

        public String getReceiverDetail() {
            return receiverDetail;
        }

        public void setReceiverDetail(String receiverDetail) {
            this.receiverDetail = receiverDetail;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public String getReceiverProvince() {
            return receiverProvince;
        }

        public void setReceiverProvince(String receiverProvince) {
            this.receiverProvince = receiverProvince;
        }

        public List<ExpressBoListBean> getExpressBoList() {
            return expressBoList;
        }

        public void setExpressBoList(List<ExpressBoListBean> expressBoList) {
            this.expressBoList = expressBoList;
        }

        public static class ExpressBoListBean {
            /**
             * expressName : 顺丰快递
             * expressOrderNumber : SF1010626048608
             * expressTime : 1564457188000
             * operatorAccount : dangchuancai
             * goods : [{"addTime":1556250918000,"expressName":"顺丰快递","expressOrderNumber":"SF1010626048608","expressTime":1564457188000,"goodsId":10000015,"goodsImg":"http://192.168.4.214:8080/fileservice/upload/goodsManageImage/20190424/2019042411313934865932385.png","goodsName":"三域XDQ410电源转换器","operatorAccount":"dangchuancai","orderGoodsNo":"BAHLWH6JLU","orderNo":"H1904EG9J9AYGCB","goodsPublicCode":"H1904EG9J9AYGCB","orderStatus":2}]
             */

            private String expressName;
            private String expressOrderNumber;
            private long expressTime;
            private String operatorAccount;
            private List<GoodsBean> goods;

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

            public long getExpressTime() {
                return expressTime;
            }

            public void setExpressTime(long expressTime) {
                this.expressTime = expressTime;
            }

            public String getOperatorAccount() {
                return operatorAccount;
            }

            public void setOperatorAccount(String operatorAccount) {
                this.operatorAccount = operatorAccount;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * addTime : 1556250918000
                 * expressName : 顺丰快递
                 * expressOrderNumber : SF1010626048608
                 * expressTime : 1564457188000
                 * goodsId : 10000015
                 * goodsImg : http://192.168.4.214:8080/fileservice/upload/goodsManageImage/20190424/2019042411313934865932385.png
                 * goodsName : 三域XDQ410电源转换器
                 * operatorAccount : dangchuancai
                 * orderGoodsNo : BAHLWH6JLU
                 * orderNo : H1904EG9J9AYGCB
                 * goodsPublicCode : H1904EG9J9AYGCB
                 * orderStatus : 2
                 */

                private long addTime;
                private String expressName;
                private String expressOrderNumber;
                private long expressTime;
                private int goodsId;
                private String goodsImg;
                private String goodsName;
                private String operatorAccount;
                private String orderGoodsNo;
                private String orderNo;
                private String goodsPublicCode;
                private int orderStatus;

                public long getAddTime() {
                    return addTime;
                }

                public void setAddTime(long addTime) {
                    this.addTime = addTime;
                }

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

                public long getExpressTime() {
                    return expressTime;
                }

                public void setExpressTime(long expressTime) {
                    this.expressTime = expressTime;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public String getGoodsImg() {
                    return goodsImg;
                }

                public void setGoodsImg(String goodsImg) {
                    this.goodsImg = goodsImg;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public String getOperatorAccount() {
                    return operatorAccount;
                }

                public void setOperatorAccount(String operatorAccount) {
                    this.operatorAccount = operatorAccount;
                }

                public String getOrderGoodsNo() {
                    return orderGoodsNo;
                }

                public void setOrderGoodsNo(String orderGoodsNo) {
                    this.orderGoodsNo = orderGoodsNo;
                }

                public String getOrderNo() {
                    return orderNo;
                }

                public void setOrderNo(String orderNo) {
                    this.orderNo = orderNo;
                }

                public String getGoodsPublicCode() {
                    return goodsPublicCode;
                }

                public void setGoodsPublicCode(String goodsPublicCode) {
                    this.goodsPublicCode = goodsPublicCode;
                }

                public int getOrderStatus() {
                    return orderStatus;
                }

                public void setOrderStatus(int orderStatus) {
                    this.orderStatus = orderStatus;
                }
            }
        }
    }


//
//    /**
//     * order : {"orderNo":"H1904X6XB7E6BJS","receiverName":"明磨破","receiverPhone":"64646676464","receiverProvince":"内蒙古自治区","receiverCity":"赤峰市","receiverCounty":"巴林左旗","receiverDetail":"匿名你","goods":[{"goodsName":"古驰2018流行包包11","goodsId":null,"goodsPublicCode":"/upload/goodsManageImage/20190408/2019040802592846133268930.jpg","goodsImg":null}],"operatorAccount":"whx001","expressName":"申通","expressOrderNumber":"qqqe123ssss","expressRemark":"aaaaa","expressTime":1555039707000}
//     */
//
//    private OrderBean order;
//
//    public OrderBean getOrder() {
//        return order;
//    }
//
//    public void setOrder(OrderBean order) {
//        this.order = order;
//    }
//
//    public static class OrderBean {
//        /**
//         * orderNo : H1904X6XB7E6BJS
//         * receiverName : 明磨破
//         * receiverPhone : 64646676464
//         * receiverProvince : 内蒙古自治区
//         * receiverCity : 赤峰市
//         * receiverCounty : 巴林左旗
//         * receiverDetail : 匿名你
//         * goods : [{"goodsName":"古驰2018流行包包11","goodsId":null,"goodsPublicCode":"/upload/goodsManageImage/20190408/2019040802592846133268930.jpg","goodsImg":null}]
//         * operatorAccount : whx001
//         * expressName : 申通
//         * expressOrderNumber : qqqe123ssss
//         * expressRemark : aaaaa
//         * expressTime : 1555039707000
//         */
//
//        private String orderNo;
//        private String receiverName;
//        private String receiverPhone;
//        private String receiverProvince;
//        private String receiverCity;
//        private String receiverCounty;
//        private String receiverDetail;
//        private String operatorAccount;
//        private String expressName;
//        private String expressOrderNumber;
//        private String expressRemark;
//        private long expressTime;
//        private List<GoodsBean> goods;
//        private long addTime;
//
//        public long getAddTime() {
//            return addTime;
//        }
//
//        public void setAddTime(long addTime) {
//            this.addTime = addTime;
//        }
//
//        public String getOrderNo() {
//            return orderNo;
//        }
//
//        public void setOrderNo(String orderNo) {
//            this.orderNo = orderNo;
//        }
//
//        public String getReceiverName() {
//            return receiverName;
//        }
//
//        public void setReceiverName(String receiverName) {
//            this.receiverName = receiverName;
//        }
//
//        public String getReceiverPhone() {
//            return receiverPhone;
//        }
//
//        public void setReceiverPhone(String receiverPhone) {
//            this.receiverPhone = receiverPhone;
//        }
//
//        public String getReceiverProvince() {
//            return receiverProvince;
//        }
//
//        public void setReceiverProvince(String receiverProvince) {
//            this.receiverProvince = receiverProvince;
//        }
//
//        public String getReceiverCity() {
//            return receiverCity;
//        }
//
//        public void setReceiverCity(String receiverCity) {
//            this.receiverCity = receiverCity;
//        }
//
//        public String getReceiverCounty() {
//            return receiverCounty;
//        }
//
//        public void setReceiverCounty(String receiverCounty) {
//            this.receiverCounty = receiverCounty;
//        }
//
//        public String getReceiverDetail() {
//            return receiverDetail;
//        }
//
//        public void setReceiverDetail(String receiverDetail) {
//            this.receiverDetail = receiverDetail;
//        }
//
//        public String getOperatorAccount() {
//            return operatorAccount;
//        }
//
//        public void setOperatorAccount(String operatorAccount) {
//            this.operatorAccount = operatorAccount;
//        }
//
//        public String getExpressName() {
//            return expressName;
//        }
//
//        public void setExpressName(String expressName) {
//            this.expressName = expressName;
//        }
//
//        public String getExpressOrderNumber() {
//            return expressOrderNumber;
//        }
//
//        public void setExpressOrderNumber(String expressOrderNumber) {
//            this.expressOrderNumber = expressOrderNumber;
//        }
//
//        public String getExpressRemark() {
//            return expressRemark;
//        }
//
//        public void setExpressRemark(String expressRemark) {
//            this.expressRemark = expressRemark;
//        }
//
//        public long getExpressTime() {
//            return expressTime;
//        }
//
//        public void setExpressTime(long expressTime) {
//            this.expressTime = expressTime;
//        }
//
//        public List<GoodsBean> getGoods() {
//            return goods;
//        }
//
//        public void setGoods(List<GoodsBean> goods) {
//            this.goods = goods;
//        }
//
//        public static class GoodsBean {
//            /**
//             * goodsName : 古驰2018流行包包11
//             * goodsId : null
//             * goodsPublicCode : /upload/goodsManageImage/20190408/2019040802592846133268930.jpg
//             * goodsImg : null
//             */
//
//            private String goodsName;
//            private int goodsId;
//            private String goodsPublicCode;
//            private String goodsImg;
//
//            public String getGoodsName() {
//                return goodsName;
//            }
//
//            public void setGoodsName(String goodsName) {
//                this.goodsName = goodsName;
//            }
//
//            public int getGoodsId() {
//                return goodsId;
//            }
//
//            public void setGoodsId(int goodsId) {
//                this.goodsId = goodsId;
//            }
//
//            public String getGoodsPublicCode() {
//                return goodsPublicCode;
//            }
//
//            public void setGoodsPublicCode(String goodsPublicCode) {
//                this.goodsPublicCode = goodsPublicCode;
//            }
//
//            public String getGoodsImg() {
//                return goodsImg;
//            }
//
//            public void setGoodsImg(String goodsImg) {
//                this.goodsImg = goodsImg;
//            }
//        }
//    }

}

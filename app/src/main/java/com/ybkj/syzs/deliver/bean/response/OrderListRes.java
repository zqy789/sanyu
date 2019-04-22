package com.ybkj.syzs.deliver.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/18.
 * Email 15384030400@163.com
 */
public class OrderListRes implements Serializable {


    /**
     * total : 48
     * pages : 3
     * pageSize : 20
     * list : [{"orderNo":"D1190329396051497266","addTime":1553826040000,"receiverName":"刘囯新","receiverPhone":"13216117992","receiverProvince":"河北省","receiverCity":"秦皇岛市","receiverCounty":"北戴河区","receiverDetail":"测试收获地址","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190328129042766264","addTime":1553765391000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190314191624891738","addTime":1552527942000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190314386813112949","addTime":1552526574000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190313158490779822","addTime":1552459210000,"receiverName":"刘囯新","receiverPhone":"13216117992","receiverProvince":"河北省","receiverCity":"秦皇岛市","receiverCounty":"北戴河区","receiverDetail":"测试收获地址","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190311880737581554","addTime":1552316027000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190311579837122839","addTime":1552315832000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190311485328820286","addTime":1552307393000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190311297356318199","addTime":1552307142000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190311706563913624","addTime":1552281117000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190311711245937892","addTime":1552280893000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190306333161684828","addTime":1551844434000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190401286308888107","addTime":1551777603000,"receiverName":"刘囯新","receiverPhone":"13216117992","receiverProvince":"河北省","receiverCity":"秦皇岛市","receiverCounty":"北戴河区","receiverDetail":"测试收获地址","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190401844648202094","addTime":1551693757000,"receiverName":"刘囯新","receiverPhone":"13216117992","receiverProvince":"河北省","receiverCity":"秦皇岛市","receiverCounty":"北戴河区","receiverDetail":"测试收获地址","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190304423108713003","addTime":1551670571000,"receiverName":"程先生","receiverPhone":"18355040923","receiverProvince":"浙江省","receiverCity":"杭州市","receiverCounty":"西湖区","receiverDetail":"骆家庄","goods":[{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]},{"orderNo":"D1190303528311174182","addTime":1551628145000,"receiverName":"的风格","receiverPhone":"15210156014","receiverProvince":"北京市","receiverCity":"北京市市辖区","receiverCounty":"东城区","receiverDetail":"的方式感到十分感到十分","goods":[{"goodsImg":"/upload/goodsManageImage/20190420/2019042000484826035342756.png","goodsId":10000014,"goodsName":"旅居布朗诚意茶艺"}]},{"orderNo":"D1190303078284385584","addTime":1551628055000,"receiverName":"的风格","receiverPhone":"15210156014","receiverProvince":"北京市","receiverCity":"北京市市辖区","receiverCounty":"东城区","receiverDetail":"的方式感到十分感到十分","goods":[{"goodsImg":"/upload/goodsManageImage/20190420/2019042000484826035342756.png","goodsId":10000014,"goodsName":"旅居布朗诚意茶艺"}]},{"orderNo":"D1190303663113152181","addTime":1551627971000,"receiverName":"的风格","receiverPhone":"15210156014","receiverProvince":"北京市","receiverCity":"北京市市辖区","receiverCounty":"东城区","receiverDetail":"的方式感到十分感到十分","goods":[{"goodsImg":"/upload/goodsManageImage/20190420/2019042000484826035342756.png","goodsId":10000014,"goodsName":"旅居布朗诚意茶艺"}]},{"orderNo":"D1190303570458886006","addTime":1551627922000,"receiverName":"的风格","receiverPhone":"15210156014","receiverProvince":"北京市","receiverCity":"北京市市辖区","receiverCounty":"东城区","receiverDetail":"的方式感到十分感到十分","goods":[{"goodsImg":"/upload/goodsManageImage/20190420/2019042000484826035342756.png","goodsId":10000014,"goodsName":"旅居布朗诚意茶艺"}]},{"orderNo":"D1190303755445738798","addTime":1551627775000,"receiverName":"的风格","receiverPhone":"15210156014","receiverProvince":"北京市","receiverCity":"北京市市辖区","receiverCounty":"东城区","receiverDetail":"的方式感到十分感到十分","goods":[{"goodsImg":"/upload/goodsManageImage/20190420/2019042000484826035342756.png","goodsId":10000014,"goodsName":"旅居布朗诚意茶艺"}]}]
     * pageNum : 1
     */

    private int total;
    private int pages;
    private int pageSize;
    private int pageNum;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * orderNo : D1190329396051497266
         * addTime : 1553826040000
         * receiverName : 刘囯新
         * receiverPhone : 13216117992
         * receiverProvince : 河北省
         * receiverCity : 秦皇岛市
         * receiverCounty : 北戴河区
         * receiverDetail : 测试收获地址
         * goods : [{"goodsImg":"/upload/goodsManageImage/20190416/2019041606560795120455763.jpg","goodsId":10000010,"goodsName":"旅居必备高频布朗环境净化仪"}]
         */

        private String orderNo;
        private long addTime;
        private String receiverName;
        private String receiverPhone;
        private String receiverProvince;
        private String receiverCity;
        private String receiverCounty;
        private String receiverDetail;
        private List<GoodsBean> goods;
        private int orderStatus;

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
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

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean implements Serializable {
            /**
             * goodsImg : /upload/goodsManageImage/20190416/2019041606560795120455763.jpg
             * goodsId : 10000010
             * goodsName : 旅居必备高频布朗环境净化仪
             */

            private String goodsImg;
            private int goodsId;
            private String goodsName;
            private String goodsPublicCode;
            private String orderGoodsNo;

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsPublicCode() {
                return goodsPublicCode;
            }

            public void setGoodsPublicCode(String goodsPublicCode) {
                this.goodsPublicCode = goodsPublicCode;
            }

            public String getOrderGoodsNo() {
                return orderGoodsNo;
            }

            public void setOrderGoodsNo(String orderGoodsNo) {
                this.orderGoodsNo = orderGoodsNo;
            }
        }
    }
}

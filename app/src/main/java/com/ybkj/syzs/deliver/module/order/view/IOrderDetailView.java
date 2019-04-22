package com.ybkj.syzs.deliver.module.order.view;

import com.ybkj.syzs.deliver.base.BaseView;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public interface IOrderDetailView extends BaseView {
    void loadOrderData(OrderDetailRes response);
}

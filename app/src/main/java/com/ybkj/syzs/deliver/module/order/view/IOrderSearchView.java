package com.ybkj.syzs.deliver.module.order.view;

import com.ybkj.syzs.deliver.base.BaseView;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;

import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public interface IOrderSearchView extends BaseView {

    void refreshList(List<OrderListRes.ListBean> data);
}

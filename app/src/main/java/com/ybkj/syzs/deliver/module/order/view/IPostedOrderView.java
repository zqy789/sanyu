package com.ybkj.syzs.deliver.module.order.view;

import com.ybkj.syzs.deliver.base.BaseView;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;

import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/18.
 * Email 15384030400@163.com
 */
public interface IPostedOrderView extends BaseView {
    void loadMore(List<OrderListRes.ListBean> data);

    void refreshList(List<OrderListRes.ListBean> data);

    /**
     * 上拉加载结束
     */
    void loadMoreEnd();

    /**
     * 显示空页面
     */
    void showEmptyView();
}

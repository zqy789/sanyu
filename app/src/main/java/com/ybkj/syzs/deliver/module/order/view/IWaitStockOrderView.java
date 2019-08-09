package com.ybkj.syzs.deliver.module.order.view;

import com.ybkj.syzs.deliver.base.BaseView;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;

import java.util.List;

/**
 * Description
 * Author dangchuancai
 * Created on 2019/8/9.
 * Email 15001231972@163.com
 */
public interface IWaitStockOrderView extends BaseView {

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

    /**
     * 批量出库成功
     */
    void stockSuccess();
}

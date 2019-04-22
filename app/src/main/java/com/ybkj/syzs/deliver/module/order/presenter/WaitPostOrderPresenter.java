package com.ybkj.syzs.deliver.module.order.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.OrderListReq;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.order.view.IWaitPostOrderView;

import javax.inject.Inject;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/18.
 * Email 15384030400@163.com
 */
public class WaitPostOrderPresenter extends BaseRxPresenter<IWaitPostOrderView> {
    private int pageNumber = 1;

    @Inject
    public WaitPostOrderPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        OrderListRes res = (OrderListRes) response;
        if (res != null) {
            if (pageNumber > 1) {
                mView.loadMore(res.getList());
            } else {
                if (res.getList() == null || res.getList().size() == 0) {
                    mView.showEmptyView();
                } else {
                    mView.refreshList(res.getList());
                }

            }
            if (pageNumber >= res.getPages()) {
                mView.loadMoreEnd();
            }
        }
    }

    public void getOrderData(int timeType, long time, boolean isRefresh, boolean showLoading) {
        if (isRefresh) {
            pageNumber = 1;
        } else {
            pageNumber++;
        }
        OrderListReq orderListReq = new OrderListReq();
        orderListReq.setStartTimestamp(time);
        orderListReq.setQueryType(timeType);
        orderListReq.setPageNum(pageNumber);
        orderListReq.setOrderStatus(3);

        sendHttpRequest(apiService.getOrderList(orderListReq), Constants.REQUEST_CODE_1, showLoading);
    }
}

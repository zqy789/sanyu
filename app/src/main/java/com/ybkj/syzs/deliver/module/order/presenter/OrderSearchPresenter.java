package com.ybkj.syzs.deliver.module.order.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.OrderListReq;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.order.view.IOrderSearchView;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class OrderSearchPresenter extends BaseRxPresenter<IOrderSearchView> {
    @Inject
    public OrderSearchPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        OrderListRes res = (OrderListRes) response;
        List<OrderListRes.ListBean> orderList = res.getList();
        if (orderList.size() > 0) {
            mView.refreshList(orderList);
        }

    }

    public void getOrderData(String keyword, Date date) {
        OrderListReq orderListReq = new OrderListReq();
        orderListReq.setKey(keyword);
        orderListReq.setPageNum(1);
        orderListReq.setOrderStatus(0);
        if(date ==null){
            orderListReq.setStartTimestamp(1);
        }else{
            orderListReq.setStartTimestamp(date.getTime());
        }
        sendHttpRequest(apiService.getOrderList(orderListReq), Constants.REQUEST_CODE_1, false);
    }
}

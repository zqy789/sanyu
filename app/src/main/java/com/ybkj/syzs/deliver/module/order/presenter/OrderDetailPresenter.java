package com.ybkj.syzs.deliver.module.order.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.order.view.IOrderDetailView;

import javax.inject.Inject;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class OrderDetailPresenter extends BaseRxPresenter<IOrderDetailView> {

    @Inject
    public OrderDetailPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        mView.loadOrderData((OrderDetailRes) response);
    }

    public void getOrderDetail(String orderNo) {
        sendHttpRequest(apiService.getOrderDetail(orderNo), Constants.REQUEST_CODE_1);
    }
}

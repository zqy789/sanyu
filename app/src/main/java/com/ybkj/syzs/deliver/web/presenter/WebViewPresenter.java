package com.ybkj.syzs.deliver.web.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.web.view.IWebView;

import javax.inject.Inject;

public class WebViewPresenter extends BaseRxPresenter<IWebView> {
    @Inject
    public WebViewPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        mView.loadOrderData((OrderDetailRes) response);
    }

    public void getOrderDetail(String orderNo) {
//        sendHttpRequest(apiService.getDetailExpress(orderNo), Constants.REQUEST_CODE_1);
    }
}

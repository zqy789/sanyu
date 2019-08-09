package com.ybkj.syzs.deliver.module.order.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.OrderListReq;
import com.ybkj.syzs.deliver.bean.request.OutOfStock;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.order.view.IWaitPostOrderView;
import com.ybkj.syzs.deliver.module.order.view.IWaitStockOrderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;

/**
 * Description
 * Author dangchuancai
 * Created on 2019/8/9.
 * Email 15384030400@163.com
 */
public class WaitStockOrderPresenter extends BaseRxPresenter<IWaitStockOrderView> {
    private int pageNumber = 1;

    @Inject
    public WaitStockOrderPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag){
            case Constants.REQUEST_CODE_1:
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
                break;
            case Constants.REQUEST_CODE_2:
                mView.stockSuccess();
                break;
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
        orderListReq.setOrderStatus(2);
        sendHttpRequest(apiService.getOrderList(orderListReq), Constants.REQUEST_CODE_1, showLoading);
    }

    public void outOfStock(OutOfStock outOfStock, boolean showLoading) {
        sendHttpRequest(apiService.outOfStock(outOfStock), Constants.REQUEST_CODE_2, showLoading);
    }
}
package com.ybkj.syzs.deliver.web.view;

import com.ybkj.syzs.deliver.base.BaseView;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;

public interface IWebView extends BaseView {
    void loadOrderData(OrderDetailRes response);
}

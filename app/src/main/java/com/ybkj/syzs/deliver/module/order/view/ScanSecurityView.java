package com.ybkj.syzs.deliver.module.order.view;

import com.ybkj.syzs.deliver.base.BaseView;

/**
 * author : ywh
 * date : 2019/1/18 14:02
 * description :
 */
public interface ScanSecurityView extends BaseView {
    void noUserCode(String resultCode);

    void againScan();
}

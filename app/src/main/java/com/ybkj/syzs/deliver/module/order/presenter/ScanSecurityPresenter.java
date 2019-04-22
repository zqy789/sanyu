package com.ybkj.syzs.deliver.module.order.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.CheckCodeUseReq;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.order.view.ScanSecurityView;

import javax.inject.Inject;

/**
 * author : ywh
 * date : 2019/1/18 14:01
 * description :
 */
public class ScanSecurityPresenter extends BaseRxPresenter<ScanSecurityView> {
    private String resultCode;//扫描到的的防伪码

    @Inject
    public ScanSecurityPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        switch (tag) {
            case Constants.REQUEST_CODE_1:
                mView.noUserCode(resultCode);
                break;
        }
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        mView.againScan();
    }

    /**
     * 查询公码是否已使用
     *
     * @param result
     */
    public void checkCodeUse(String result) {
        resultCode = result;
        CheckCodeUseReq req = new CheckCodeUseReq();
        req.setGoodsPublicCode(result);
        sendHttpRequest(apiService.checkCodeUseAble(req), Constants.REQUEST_CODE_1, false);
    }
}

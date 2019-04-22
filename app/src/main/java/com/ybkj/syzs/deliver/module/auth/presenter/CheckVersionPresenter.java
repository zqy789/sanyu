package com.ybkj.syzs.deliver.module.auth.presenter;

import android.content.Context;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.response.VersionRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.auth.view.CheckVersionView;

import javax.inject.Inject;

/**
 * author : ywh
 * date : 2019/4/1 16:30
 * description :
 */
public class CheckVersionPresenter extends BaseRxPresenter<CheckVersionView> {

    @Inject
    public CheckVersionPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        mView.checkVersionSuccess((VersionRes) response);
    }

    public void getVersionInfo(boolean showLoading) {
        sendHttpRequest(apiService.getVersion(), Constants.REQUEST_CODE_1, showLoading);
    }
}

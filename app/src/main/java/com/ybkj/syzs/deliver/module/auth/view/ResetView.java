package com.ybkj.syzs.deliver.module.auth.view;


import com.ybkj.syzs.deliver.base.BaseView;

/**
 * author : ywh
 * date : 2019/4/1 18:01
 * description :
 */
public interface ResetView extends BaseView {
    void resetSuccess();

    void passwordFail();

    void passwordPass();
}

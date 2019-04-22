package com.ybkj.syzs.deliver.module.auth.view;


import com.ybkj.syzs.deliver.base.BaseView;
import com.ybkj.syzs.deliver.bean.response.VersionRes;

/**
 * author : ywh
 * date : 2019/4/1 16:31
 * description :
 */
public interface CheckVersionView extends BaseView {

    void checkVersionSuccess(VersionRes versionRes);
}

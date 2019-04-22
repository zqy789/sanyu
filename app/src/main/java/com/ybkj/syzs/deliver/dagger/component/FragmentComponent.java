package com.ybkj.syzs.deliver.dagger.component;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.ybkj.syzs.deliver.dagger.module.FragmentModule;
import com.ybkj.syzs.deliver.dagger.scope.FragmentScope;
import com.ybkj.syzs.deliver.module.order.activity.PostedOrderFragment;
import com.ybkj.syzs.deliver.module.order.activity.WaitPostOrderFragment;

import dagger.Component;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  将对象注解到实力对象的接口
 * - @Time:  2018/9/6
 * - @Emaill:  380948730@qq.com
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Fragment getFragment();

    Context getContext();

    void inject(PostedOrderFragment postedOrderFragment);

    void inject(WaitPostOrderFragment waitPostOrderFragment);
}
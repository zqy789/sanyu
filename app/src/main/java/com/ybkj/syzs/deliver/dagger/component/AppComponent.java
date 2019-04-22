package com.ybkj.syzs.deliver.dagger.component;


import com.ybkj.syzs.deliver.MyApplication;
import com.ybkj.syzs.deliver.dagger.module.AppModule;
import com.ybkj.syzs.deliver.dagger.module.HttpModule;
import com.ybkj.syzs.deliver.net.api.ApiService;
import com.ybkj.syzs.deliver.net.api.BaseNetFunction;

import javax.inject.Singleton;

import dagger.Component;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  引入dragger2依赖注入，创建项目中所需要用到的全局对象
 *
 * @Singleton 代表这些全局对象单例
 * @AppModule app全局都要用到的对象module
 * @HttpModule Http请求所需要用到的对象
 * <p>
 * - @Time:  2018/9/5
 * - @Emaill:  380948730@qq.com
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    MyApplication getContext();

    ApiService provideService();

    BaseNetFunction provideBaseNetFunction();

}

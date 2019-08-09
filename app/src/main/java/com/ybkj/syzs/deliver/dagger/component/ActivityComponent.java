package com.ybkj.syzs.deliver.dagger.component;

import android.app.Activity;
import android.content.Context;

import com.ybkj.syzs.deliver.dagger.module.ActivityModule;
import com.ybkj.syzs.deliver.dagger.scope.ActivityScope;
import com.ybkj.syzs.deliver.module.auth.activity.AccountChoseActivity;
import com.ybkj.syzs.deliver.module.auth.activity.LoginActivity;
import com.ybkj.syzs.deliver.module.auth.activity.ModifyPhoneActivity;
import com.ybkj.syzs.deliver.module.auth.activity.ModifyPsdActivity;
import com.ybkj.syzs.deliver.module.auth.activity.ResetPasswordActivity;
import com.ybkj.syzs.deliver.module.auth.activity.RetrieveActivity;
import com.ybkj.syzs.deliver.module.auth.activity.VerifyPhoneActivity;
import com.ybkj.syzs.deliver.module.auth.activity.VersionActivity;
import com.ybkj.syzs.deliver.module.auth.activity.WelcomeActivity;
import com.ybkj.syzs.deliver.module.order.activity.GoodsPostActivity;
import com.ybkj.syzs.deliver.module.order.activity.OrderDetailActivity;
import com.ybkj.syzs.deliver.module.order.activity.OrderSearchActivity;
import com.ybkj.syzs.deliver.module.order.activity.ScanSecurityActivity;
import com.ybkj.syzs.deliver.module.user.activity.UserInfoActivity;
import com.ybkj.syzs.deliver.web.BaseWebviewActivity;

import dagger.Component;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  Activity的Component
 * 主要作用是将@Inject标注的Presenter和@Module标注的ActivityModule联系起来，从@Module中获取依赖，
 * 并将依赖注入给@Inject标注的对象。
 * - @Time:  2018/9/5
 * - @Emaill:  380948730@qq.com
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    Context getContext();


    void inject(LoginActivity loginActivity);

    void inject(ModifyPhoneActivity modifyPhoneActivity);

    void inject(ModifyPsdActivity modifyPsdActivity);

    void inject(ResetPasswordActivity resetPasswordActivity);

    void inject(RetrieveActivity retrieveActivity);

    void inject(VerifyPhoneActivity verifyPhoneActivity);

    void inject(VersionActivity versionActivity);

    void inject(WelcomeActivity welcomeActivity);

    void inject(ScanSecurityActivity scanSecurityActivity);

    void inject(GoodsPostActivity goodsPostActivity);

    void inject(OrderDetailActivity orderDetailActivity);

    void inject(BaseWebviewActivity baseWebviewActivity);

    void inject(OrderSearchActivity orderSearchActivity);

    void inject(UserInfoActivity userInfoActivity);

    void inject(AccountChoseActivity accountChoseActivity);
}

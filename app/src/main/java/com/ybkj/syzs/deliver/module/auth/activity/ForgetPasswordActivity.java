package com.ybkj.syzs.deliver.module.auth.activity;

import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.ui.adapter.ViewPagerAdapter;

import butterknife.BindView;

/**
 * Description 忘记密码
 * Author Ren Xingzhi
 * Created on 2019/4/24.
 * Email 15384030400@163.com
 */
public class ForgetPasswordActivity extends BaseMvpActivity {
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void injectPresenter() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_forget_password;
    }

    @Override
    protected void initView() {
        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        tabLayout.addTab(tabLayout.newTab().setText("通过手机号找回"));
        tabLayout.addTab(tabLayout.newTab().setText("通过账号找回"));
        PhoneBackPsdFragment phoneBackPsdFragment = new PhoneBackPsdFragment();
        AccountBackPsdFragment accountBackPsdFragment = new AccountBackPsdFragment();
        viewPagerAdapter.addFragment(phoneBackPsdFragment, "通过手机号找回");
        viewPagerAdapter.addFragment(accountBackPsdFragment, "通过账号找回");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }

}

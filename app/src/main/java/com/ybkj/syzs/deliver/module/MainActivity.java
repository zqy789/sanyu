package com.ybkj.syzs.deliver.module;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.LoginRes;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.manager.UserDataManager;
import com.ybkj.syzs.deliver.module.order.activity.OrderSearchActivity;
import com.ybkj.syzs.deliver.module.order.activity.PostedOrderFragment;
import com.ybkj.syzs.deliver.module.order.activity.WaitPostOrderFragment;
import com.ybkj.syzs.deliver.module.user.activity.UserInfoActivity;
import com.ybkj.syzs.deliver.ui.adapter.ViewPagerAdapter;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;

import butterknife.BindView;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  首页
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements IMainAtView {
    private static final int ORDER_STATUS_WAIT_POST = 3;//订单状态：待发货
    private static final int ORDER_STATUS_POSTED = 5;//订单状态：已发货
    @BindView(R.id.image_avatar)
    ImageView imageAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.layout_search)
    RelativeLayout layoutSearch;
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.layout_user_info)
    LinearLayout layoutUserInfo;
    //点击回退的时间
    private long recodeTime = 0;

    @Override
    protected void initTitle() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        tabLayout.addTab(tabLayout.newTab().setText(ResourcesUtil.getString(R.string.order_wait_post)));
        tabLayout.addTab(tabLayout.newTab().setText(ResourcesUtil.getString(R.string.order_posted)));

        WaitPostOrderFragment waitPostOrderFragment = WaitPostOrderFragment.getInstance(ORDER_STATUS_WAIT_POST);
        PostedOrderFragment postedOrderFragment = PostedOrderFragment.getInstance(ORDER_STATUS_POSTED);

        viewPagerAdapter.addFragment(waitPostOrderFragment, ResourcesUtil.getString(R.string.order_wait_post));
        viewPagerAdapter.addFragment(postedOrderFragment, ResourcesUtil.getString(R.string.order_posted));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        layoutUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 跳转到个人信息页
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                ActivityManager.gotoActivity(mContext, intent);
            }
        });
        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.gotoActivity(mContext, OrderSearchActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        LoginRes loginRes = UserDataManager.getLoginInfo();
        if (loginRes != null) {
            tvUserName.setText(loginRes.getOperatorAccount());
        }

    }


    @Override
    protected void injectPresenter() {
    }

    @Override
    public boolean isImmersiveStatusBar() {
        return true;
    }

    /**
     * 连续点击2次返回键才能退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - recodeTime > 2000) {
                toast(getResources().getString(R.string.click_exit));
                recodeTime = System.currentTimeMillis();
                return true;
            } else {
                //退出程序
                finish();
                ActivityManager.exit();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

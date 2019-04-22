package com.ybkj.syzs.deliver.module.user.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.LoginRes;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.manager.UserDataManager;
import com.ybkj.syzs.deliver.module.auth.activity.LoginActivity;
import com.ybkj.syzs.deliver.module.auth.activity.ModifyPsdActivity;
import com.ybkj.syzs.deliver.module.auth.activity.VerifyPhoneActivity;
import com.ybkj.syzs.deliver.module.auth.activity.VersionActivity;
import com.ybkj.syzs.deliver.module.user.presenter.UserInfoPresenter;
import com.ybkj.syzs.deliver.module.user.view.IUserInfoView;
import com.ybkj.syzs.deliver.ui.dialog.TipDialog;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/21.
 * Email 15384030400@163.com
 */
public class UserInfoActivity extends BaseMvpActivity<UserInfoPresenter> implements IUserInfoView {
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_change_phone)
    TextView tvChangePhone;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_change_password)
    TextView tvChangePassword;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.btn_logout)
    TextView btnLogout;

    private TipDialog logoutDialog;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        LoginRes loginRes = UserDataManager.getLoginInfo();
        String phoneNumber = loginRes.getPhoneNumber();
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() > 7) {
            String encryptPhone = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length());
            tvPhone.setText(encryptPhone);
        }
        tvAccount.setText(loginRes.getOperatorAccount());
    }

    @OnClick({R.id.tv_change_phone, R.id.tv_change_password, R.id.tv_update, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change_phone:
                ActivityManager.gotoActivity(mContext, VerifyPhoneActivity.class);
                break;
            case R.id.tv_change_password:
                ActivityManager.gotoActivity(mContext, ModifyPsdActivity.class);
                break;
            case R.id.tv_update:
                ActivityManager.gotoActivity(mContext, VersionActivity.class);
                break;
            case R.id.btn_logout:
                showLogoutDialog();
                break;
        }
    }

    /**
     * 显示退出登录弹框
     */
    private void showLogoutDialog() {
        if (logoutDialog == null) {
            logoutDialog = new TipDialog(mContext);
            logoutDialog.setContentText("是否确定退出登录？");
            logoutDialog.setTitleText("退出登录");
            logoutDialog.setOnConfirmButtonClickListener((Dialog dialog, View view) -> {
                presenter.logout();
                logoutDialog.dismiss();
            });
            logoutDialog.show();
        } else {
            if (!logoutDialog.isShowing()) {
                logoutDialog.show();
            }
        }
    }

    @Override
    public void logoutComplete() {
        ToastUtil.showShort("退出成功");
        UserDataManager.clearLoginInfo();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityManager.gotoActivity(mContext, intent);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LoginRes loginRes = UserDataManager.getLoginInfo();
        String phoneNumber = loginRes.getPhoneNumber();
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() > 7) {
            String encryptPhone = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length());
            tvPhone.setText(encryptPhone);
        }
        tvAccount.setText(loginRes.getOperatorAccount());
    }
}

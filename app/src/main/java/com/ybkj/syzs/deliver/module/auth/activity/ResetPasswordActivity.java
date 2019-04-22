package com.ybkj.syzs.deliver.module.auth.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.module.auth.presenter.ResetPasswordPresenter;
import com.ybkj.syzs.deliver.module.auth.view.ResetView;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : ywh
 * date : 2019/4/1 16:02
 * description :重置密码
 */
public class ResetPasswordActivity extends BaseMvpActivity<ResetPasswordPresenter> implements ResetView {
    String account;
    String phone;
    String code;
    @BindView(R.id.auth_password_et)
    EditText authPasswordEt;
    @BindView(R.id.auth_btn_finish)
    Button authBtnFinish;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_retset_password;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        account = getIntent().getStringExtra(Constants.INTENT_PARAMETER_1);
        phone = getIntent().getStringExtra(Constants.INTENT_PARAMETER_2);
        code = getIntent().getStringExtra(Constants.INTENT_PARAMETER_3);
    }

    /**
     * 重置密码成功
     */
    @OnClick(R.id.auth_btn_finish)
    public void onViewClicked() {
        String password = authPasswordEt.getText().toString().trim();
        presenter.reSetPassword(account, phone, code, password);

    }

    @Override
    public void resetSuccess() {
        ToastUtil.showShort("重置密码成功，请重新登录");
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityManager.gotoActivity(mContext, intent);
        finish();
    }
}

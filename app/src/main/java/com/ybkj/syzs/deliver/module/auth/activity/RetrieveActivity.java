package com.ybkj.syzs.deliver.module.auth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.module.auth.presenter.RetrievePresenter;
import com.ybkj.syzs.deliver.module.auth.view.RetrieveView;
import com.ybkj.syzs.deliver.utils.VerificationCodeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 找回密码
 */

public class RetrieveActivity extends BaseMvpActivity<RetrievePresenter> implements RetrieveView {

    //账号
    @BindView(R.id.auth_account_et)
    EditText authAccountEt;
    //手机号
    @BindView(R.id.auth_register_phone_code_et)
    EditText authRegisterPhoneCodeEt;
    //获取验证码
    @BindView(R.id.auth_register_code_btn)
    TextView authRegisterCodeBtn;
    //验证码输入
    @BindView(R.id.auth_code_et)
    EditText authCodeEt;
    //下一步
    @BindView(R.id.auth_btn_next)
    Button authBtnNext;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_retrieve;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.auth_register_code_btn, R.id.auth_btn_next})
    public void onViewClicked(View view) {
        int i = view.getId();//获取验证码
        if (i == R.id.auth_register_code_btn) {
            String phone = authRegisterPhoneCodeEt.getText().toString();
            presenter.getVerificationCode(phone);

        } //下一步
        else if (i == R.id.auth_btn_next) {
            String account = authAccountEt.getText().toString();
            String phoneNum = authRegisterPhoneCodeEt.getText().toString();
            String phoneCode = authCodeEt.getText().toString();
            if (presenter.checkPhone(account, phoneNum, phoneCode)) {
                Intent intent = new Intent(mContext, ResetPasswordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_PARAMETER_1, account);
                bundle.putString(Constants.INTENT_PARAMETER_2, phoneNum);
                bundle.putString(Constants.INTENT_PARAMETER_3, phoneCode);
                intent.putExtras(bundle);
                ActivityManager.gotoActivity(mContext, intent);
            }
        }
    }

    /**
     * 获取验证码成功
     */
    @Override
    public void verificationCodeChange() {
        VerificationCodeUtil util = new VerificationCodeUtil();
        util.codeCountDown(authRegisterCodeBtn);
    }
}

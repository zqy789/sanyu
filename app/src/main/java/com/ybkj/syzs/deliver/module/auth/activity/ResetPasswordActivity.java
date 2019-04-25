package com.ybkj.syzs.deliver.module.auth.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.module.auth.presenter.ResetPasswordPresenter;
import com.ybkj.syzs.deliver.module.auth.view.ResetView;
import com.ybkj.syzs.deliver.ui.view.ClearEditText;
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
    String sign;

    @BindView(R.id.account_tv)
    TextView accountTv;
    @BindView(R.id.auth_password_et)
    ClearEditText authPasswordEt;
    @BindView(R.id.auth_password_re_et)
    ClearEditText authPasswordReEt;
    @BindView(R.id.auth_btn_finish)
    Button authBtnFinish;
    @BindView(R.id.layout_password_easy)
    LinearLayout layoutPasswordEasy;
    @BindView(R.id.layout_password_different)
    LinearLayout layoutPasswordDifferent;

    private boolean passwordCanUse = false;
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
        authPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = authPasswordEt.getText().toString().trim();
                if (password.length() > 5) {
                    presenter.checkPasswordEasy(password);
                } else {
                    layoutPasswordEasy.setVisibility(View.GONE);
                }
                judgePasswordEqual();
                judgeButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        authPasswordReEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                judgePasswordEqual();
                judgeButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void judgePasswordEqual() {
        String rePassword = authPasswordReEt.getText().toString().trim();
        if (rePassword.length() == 0) {
            return;
        }
        String password = authPasswordEt.getText().toString().trim();
        layoutPasswordDifferent.setVisibility(password.equals(rePassword) ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void initData() {
        account = getIntent().getStringExtra("account");
        sign = getIntent().getStringExtra("sign");
        accountTv.setText(account);

    }

    @OnClick(R.id.auth_btn_finish)
    public void onViewClicked() {
        String password = authPasswordEt.getText().toString().trim();
        String rePassword = authPasswordEt.getText().toString().trim();
        presenter.reSetPassword(account, sign, password, rePassword);

    }

    @SuppressLint("WrongConstant")
    @Override
    public void resetSuccess() {
        ToastUtil.showShort("重置密码成功，请登录");
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityManager.gotoActivity(mContext, intent);
        finish();
    }

    @Override
    public void passwordFail() {
        passwordCanUse = false;
        layoutPasswordEasy.setVisibility(View.VISIBLE);
    }

    @Override
    public void passwordPass() {
        passwordCanUse = true;
        layoutPasswordEasy.setVisibility(View.GONE);
        judgeButtonEnable();
    }

    private void judgeButtonEnable() {
        String rePassword = authPasswordReEt.getText().toString().trim();
        String password = authPasswordEt.getText().toString().trim();
        if (password.length() > 5 && rePassword.length() > 5 && rePassword.equals(password) && passwordCanUse) {
            authBtnFinish.setEnabled(true);
        } else {
            authBtnFinish.setEnabled(false);
        }
    }
}

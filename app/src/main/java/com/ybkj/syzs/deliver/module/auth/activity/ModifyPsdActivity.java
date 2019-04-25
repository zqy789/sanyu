package com.ybkj.syzs.deliver.module.auth.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.manager.UserDataManager;
import com.ybkj.syzs.deliver.module.auth.presenter.ModifyPasswordPresenter;
import com.ybkj.syzs.deliver.module.auth.view.ModifyPasswordView;
import com.ybkj.syzs.deliver.ui.view.ClearEditText;
import com.ybkj.syzs.deliver.utils.InputTextHelper;
import com.ybkj.syzs.deliver.utils.ToastUtil;
import com.ybkj.syzs.deliver.utils.VerificationCodeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码
 */
public class ModifyPsdActivity extends BaseMvpActivity<ModifyPasswordPresenter> implements ModifyPasswordView {

    //手机号
    @BindView(R.id.phone_code_et)
    EditText phoneCodeEt;
    //验证码
    @BindView(R.id.auth_code_et)
    EditText authCodeEt;
    //获取注册验证
    @BindView(R.id.auth_register_code_btn)
    TextView authRegisterCodeBtn;
    //第一次密码
    @BindView(R.id.auth_password_et)
    ClearEditText authPasswordEt;
    //重复密码
    @BindView(R.id.auth_password_re_et)
    ClearEditText authPasswordReEt;
    //完成
    @BindView(R.id.auth_btn_finish)
    Button authBtnFinish;

    //原密码
    @BindView(R.id.auth_old_password_et)
    ClearEditText authOldPassword;
    //密码过于简单提示
    @BindView(R.id.password_has_tv)
    TextView passwordHasTv;
    //两次输入密码是否一致
    @BindView(R.id.password_Inequality_tv)
    TextView passwordInequalityTv;

    private String phoneNumber;

    @Override
    protected void injectPresenter() {
        getActivityComponent()
                .inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_modify_psd;
    }

    @Override
    protected void initView() {
        new InputTextHelper.Builder(this)
                .setMain(authBtnFinish)
                .addView(phoneCodeEt)
                .addView(authCodeEt)
                .addView(authOldPassword)
                .addView(authPasswordEt)
                .addView(authPasswordReEt)
                .build();
        authPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = authPasswordEt.getText().toString().trim();
                if (password.length() > 5) {
                    presenter.checkoutPassword(password);
                }
            }
        });


        authPasswordReEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = authPasswordEt.getText().toString().trim();
                String againPassword = authPasswordReEt.getText().toString().trim();
                if (againPassword.length() > 5) {
                    presenter.passwordIsEquals(password, againPassword);
                }
            }
        });
        authRegisterCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getVerificationCode(phoneNumber);
            }
        });
    }

    @Override
    protected void initData() {
        phoneNumber = UserDataManager.getLoginInfo().getPhoneNumber();
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() > 7) {
            String encryptPhone = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length());
            phoneCodeEt.setText(encryptPhone);
        }
    }


    @OnClick({R.id.auth_btn_finish})
    public void onViewClicked(View view) {
        int i = view.getId();//密码显示
        if (i == R.id.auth_btn_finish) {
            String oldPassword = authOldPassword.getText().toString().trim();
            String Password = authPasswordEt.getText().toString().trim();
            String newPasswordRe = authPasswordReEt.getText().toString().trim();
            String phone = phoneCodeEt.getText().toString().trim();
            String code = authCodeEt.getText().toString().trim();
            presenter.modifyPsd(phone, code, oldPassword, Password, newPasswordRe);
        }
    }

    @Override
    public void modifySuccess() {
        ToastUtil.showShort("密码修改成功");
        finish();
    }

    @Override
    public void PhoneCodeSuccess() {
        VerificationCodeUtil util = new VerificationCodeUtil();
        util.codeCountDown(authRegisterCodeBtn);
    }

    @Override
    public void passPassword() {
        passwordHasTv.setVisibility(View.GONE);
    }

    @Override
    public void simplePassword(String message) {
        passwordHasTv.setVisibility(View.VISIBLE);
        passwordHasTv.setText(message);
    }

    /**
     * 两次密码不一致
     */
    @Override
    public void passwordInequalityPassword() {
        passwordInequalityTv.setVisibility(View.VISIBLE);
        passwordInequalityTv.setText("两次输入密码不一致");
    }

    /**
     * 两次M密码相同
     */
    @Override
    public void passwordequalityPassword() {
        passwordInequalityTv.setVisibility(View.GONE);
    }
}

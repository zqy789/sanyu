package com.ybkj.syzs.deliver.module.auth.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.manager.UserDataManager;
import com.ybkj.syzs.deliver.module.auth.presenter.ModifyPasswordPresenter;
import com.ybkj.syzs.deliver.module.auth.view.ModifyPasswordView;
import com.ybkj.syzs.deliver.utils.ToastUtil;
import com.ybkj.syzs.deliver.utils.VerificationCodeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码
 */
public class ModifyPsdActivity extends BaseMvpActivity<ModifyPasswordPresenter> implements ModifyPasswordView {


    @BindView(R.id.auth_old_password_et)
    EditText authOldPasswordEt;
    @BindView(R.id.auth_new_password_et)
    EditText authNewPasswordEt;
    @BindView(R.id.phone_code_et)
    EditText phoneCodeEt;
    @BindView(R.id.auth_btn_finish)
    Button authBtnFinish;
    @BindView(R.id.auth_new_password_re_et)
    EditText authNewPasswordReEt;
    @BindView(R.id.password_visible)
    ImageButton passwordVisible;
    @BindView(R.id.new_password_visible)
    ImageButton newPasswordVisible;
    @BindView(R.id.new_password_re_visible)
    ImageButton newPasswordReVisible;
    @BindView(R.id.auth_register_code_btn)
    TextView authRegisterCodeBtn;

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
        authOldPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordVisible.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authOldPasswordEt.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                    authOldPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordVisible.setImageResource(R.mipmap.ic_password_visible);
                } else {
                    authOldPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
                    passwordVisible.setImageResource(R.mipmap.ic_password_unvisible);
                }
            }
        });

        authNewPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPasswordVisible.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newPasswordVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authNewPasswordEt.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                    authNewPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPasswordVisible.setImageResource(R.mipmap.ic_password_visible);
                } else {
                    authNewPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
                    newPasswordVisible.setImageResource(R.mipmap.ic_password_unvisible);
                }
            }
        });

        authNewPasswordReEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPasswordReVisible.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newPasswordReVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authNewPasswordReEt.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                    authNewPasswordReEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPasswordReVisible.setImageResource(R.mipmap.ic_password_visible);
                } else {
                    authNewPasswordReEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
                    newPasswordReVisible.setImageResource(R.mipmap.ic_password_unvisible);
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
            String oldPassword = authOldPasswordEt.getText().toString().trim();
            String newPassword = authNewPasswordEt.getText().toString().trim();
            String phoneCode = phoneCodeEt.getText().toString().trim();
            String newPasswordRe = authNewPasswordReEt.getText().toString().trim();
            presenter.modifyPsd(phoneCode, oldPassword, newPassword, newPasswordRe);
        }
    }

    @Override
    public void modifySuccess() {
        ToastUtil.showShort("密码修改成功");
        finish();
    }

    @Override
    public void getSmsSuccess() {
        VerificationCodeUtil util = new VerificationCodeUtil();
        util.codeCountDown(authRegisterCodeBtn);
    }
}

package com.ybkj.syzs.deliver.module.auth.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.LoginRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.module.MainActivity;
import com.ybkj.syzs.deliver.module.auth.presenter.LoginPresenter;
import com.ybkj.syzs.deliver.module.auth.view.ILoginView;
import com.ybkj.syzs.deliver.module.changeurl.ChangeUrlActivity;
import com.ybkj.syzs.deliver.utils.SPHelper;
import com.ybkj.syzs.deliver.utils.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/3/1.
 * Email 15384030400@163.com
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements ILoginView {
    //账号输入
    @BindView(R.id.auth_account_et)
    EditText authAccountEt;
    //密码输入
    @BindView(R.id.auth_password_et)
    EditText authPasswordEt;
    //忘记密码
    @BindView(R.id.auth_forget_password_tv)
    TextView authForgetPasswordTv;
    @BindView(R.id.password_visible)
    ImageButton passwordVisible;
    //登录按钮
    @BindView(R.id.auth_login_btn)
    Button authLoginTtn;
    //注册
    @BindView(R.id.auth_register_tv)
    TextView authRegisterTv;
    @BindView(R.id.login_logo)
    ImageView loginLogo;

    private long recodeTime;
    private int clickTimes = 0;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_login;
    }

    @Override
    protected void initView() {
        loginLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - recodeTime > 2000) {
                    clickTimes = 0;
                }
                recodeTime = System.currentTimeMillis();
                clickTimes++;
                if (clickTimes >= 10) {
                    ActivityManager.gotoActivity(mContext, ChangeUrlActivity.class);
                }
            }
        });

        authPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    passwordVisible.setVisibility(View.VISIBLE);
                } else {
                    passwordVisible.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authPasswordEt.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                    authPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordVisible.setImageResource(R.mipmap.ic_password_visible);
                } else {
                    authPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
                    passwordVisible.setImageResource(R.mipmap.ic_password_unvisible);
                }
            }
        });
    }

    @Override
    protected void initData() {

        String account = SPHelper.getInstance().getString(Constants.LOGIN_NAME_KEY);
        if (!StringUtil.isNull(account)) {
            authAccountEt.setText(account);
        }
    }

    @OnClick({R.id.auth_forget_password_tv, R.id.auth_login_btn, R.id.auth_register_tv})
    public void onViewClicked(View view) {
        int i = view.getId();
        //忘记密码
        if (i == R.id.auth_forget_password_tv) {
            ActivityManager.gotoActivity(mContext, RetrieveActivity.class);
        }
        //登录
        else if (i == R.id.auth_login_btn) {
            String account = authAccountEt.getText().toString().trim();
            String password = authPasswordEt.getText().toString().trim();
            presenter.login(account, password);
        }
    }

    @Override
    public void loginSuccess(LoginRes response) {
        ActivityManager.gotoActivity(mContext, MainActivity.class);
        finish();
    }
}

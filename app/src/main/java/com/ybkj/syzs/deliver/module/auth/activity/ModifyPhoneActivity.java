package com.ybkj.syzs.deliver.module.auth.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.LoginRes;
import com.ybkj.syzs.deliver.manager.UserDataManager;
import com.ybkj.syzs.deliver.module.auth.presenter.ModifyPhonePresenter;
import com.ybkj.syzs.deliver.module.auth.view.ModifyPhoneView;
import com.ybkj.syzs.deliver.module.user.activity.UserInfoActivity;
import com.ybkj.syzs.deliver.ui.view.ClearEditText;
import com.ybkj.syzs.deliver.utils.InputTextHelper;
import com.ybkj.syzs.deliver.utils.ToastUtil;
import com.ybkj.syzs.deliver.utils.VerificationCodeUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * 修改手机号码
 */
public class ModifyPhoneActivity extends BaseMvpActivity<ModifyPhonePresenter> implements ModifyPhoneView {
    @BindView(R.id.auth_register_phone_code_et)
    ClearEditText authRegisterPhoneCodeEt;
    @BindView(R.id.auth_register_code_btn)
    TextView authRegisterCodeBtn;
    @BindView(R.id.auth_code_et)
    EditText authCodeEt;
    @BindView(R.id.auth_btn_next)
    Button authBtnNext;


    private String phone;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_modify_phone;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        new InputTextHelper.Builder(this)
                .setMain(authBtnNext)
                .addView(authRegisterPhoneCodeEt)
                .addView(authCodeEt)
                .build();
    }


    @OnClick({R.id.auth_register_code_btn, R.id.auth_btn_next})
    public void onViewClicked(View view) {
        int i = view.getId();//获取验证码
        if (i == R.id.auth_register_code_btn) {
            phone = authRegisterPhoneCodeEt.getText().toString().trim();
            presenter.getPhoneCode(phone);

        }
        //下一步
        else if (i == R.id.auth_btn_next) {
            phone = authRegisterPhoneCodeEt.getText().toString().trim();
            String phoneCode = authCodeEt.getText().toString().trim();
            presenter.bindPhone(phone, phoneCode);
        }
    }

    @Override
    public void verificationCodeChange() {
        VerificationCodeUtil util = new VerificationCodeUtil();
        util.codeCountDown(authRegisterCodeBtn);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void changeSuccess() {
        ToastUtil.showShort("修改成功");
        LoginRes loginRes = UserDataManager.getLoginInfo();
        loginRes.setPhoneNumber(authRegisterPhoneCodeEt.getText().toString().trim());
        UserDataManager.saveLoginInfo(loginRes);
        Intent intent = new Intent(mContext, UserInfoActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

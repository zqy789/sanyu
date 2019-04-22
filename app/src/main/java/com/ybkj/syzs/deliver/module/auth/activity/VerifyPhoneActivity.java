package com.ybkj.syzs.deliver.module.auth.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.manager.UserDataManager;
import com.ybkj.syzs.deliver.module.auth.presenter.VerifyPhonePresenter;
import com.ybkj.syzs.deliver.module.auth.view.IVerifyPhoneView;
import com.ybkj.syzs.deliver.utils.ToastUtil;
import com.ybkj.syzs.deliver.utils.VerificationCodeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/11.
 * Email 15384030400@163.com
 */
public class VerifyPhoneActivity extends BaseMvpActivity<VerifyPhonePresenter> implements IVerifyPhoneView {
    @BindView(R.id.auth_register_phone_code_et)
    EditText authRegisterPhoneCodeEt;
    @BindView(R.id.auth_register_code_btn)
    TextView authRegisterCodeBtn;
    @BindView(R.id.auth_code_et)
    EditText authCodeEt;
    @BindView(R.id.auth_btn_next)
    Button authBtnNext;

    private String phoneNumber;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_verify_phone;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        phoneNumber = UserDataManager.getLoginInfo().getPhoneNumber();
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() > 7) {
            String encryptPhone = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length());
            authRegisterPhoneCodeEt.setText(encryptPhone);
        }
    }

    @Override
    public void getCodeSuccess() {
        ToastUtil.showShort("获取验证码成功");
        VerificationCodeUtil util = new VerificationCodeUtil();
        util.codeCountDown(authRegisterCodeBtn);
    }

    @Override
    public void verifySuccess() {
        ActivityManager.gotoActivity(mContext, ModifyPhoneActivity.class);
    }

    @OnClick({R.id.auth_register_code_btn, R.id.auth_btn_next})
    public void onViewClicked(View view) {
        int i = view.getId();//获取验证码
        if (i == R.id.auth_register_code_btn) {
            String phone = authRegisterPhoneCodeEt.getText().toString().trim();
            presenter.getPhoneCode(phoneNumber);

        }
        //下一步
        else if (i == R.id.auth_btn_next) {
            String phone = authRegisterPhoneCodeEt.getText().toString().trim();
            String phoneCode = authCodeEt.getText().toString().trim();
            presenter.verifyPhone(phoneNumber, phoneCode);
        }
    }
}

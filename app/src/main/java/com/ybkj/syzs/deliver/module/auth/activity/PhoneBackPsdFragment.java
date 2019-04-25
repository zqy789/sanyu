package com.ybkj.syzs.deliver.module.auth.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpFragment;
import com.ybkj.syzs.deliver.bean.response.AccountListRes;
import com.ybkj.syzs.deliver.module.auth.presenter.PhoneBackPsdPresenter;
import com.ybkj.syzs.deliver.module.auth.view.IPhoneBackPsdView;
import com.ybkj.syzs.deliver.ui.view.ClearEditText;
import com.ybkj.syzs.deliver.utils.ToastUtil;
import com.ybkj.syzs.deliver.utils.VerificationCodeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/24.
 * Email 15384030400@163.com
 */
public class PhoneBackPsdFragment extends BaseMvpFragment<PhoneBackPsdPresenter> implements IPhoneBackPsdView {
    @BindView(R.id.phone_et)
    ClearEditText phoneEt;
    @BindView(R.id.code_et)
    ClearEditText codeEt;
    @BindView(R.id.get_sms_tv)
    TextView getSmsTv;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.layout_phone_error)
    LinearLayout layoutPhoneError;

    private AccountListRes accountListRes;

    private boolean phoneCanUse = false;

    @Override
    protected void injectPresenter() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    presenter.getAccountList(s.toString().trim());
                } else {
                    // todo 隐藏提示
                    layoutPhoneError.setVisibility(View.GONE);
                    phoneCanUse = false;
                    accountListRes = null;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6 && phoneCanUse) {
                    nextBtn.setEnabled(true);
                } else {
                    nextBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.auth_fragment_phone_back_psd;
    }

    @Override
    protected String getSimpleNme() {
        return null;
    }

    @Override
    public void phoneError(String msg) {
        layoutPhoneError.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadAccountList(AccountListRes res) {
        phoneCanUse = true;
        accountListRes = res;
    }

    @Override
    public void getSmsSuccess() {
        VerificationCodeUtil util = new VerificationCodeUtil();
        util.codeCountDown(getSmsTv);
    }

    @Override
    public void loadSign(String sign) {
        Intent intent = new Intent(mContext, ResetPasswordActivity.class);
        intent.putExtra("sign", sign);
        intent.putExtra("account", accountListRes.getUserAccounts().get(0));
        startActivity(intent);
    }


    @OnClick({R.id.get_sms_tv, R.id.next_btn})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.get_sms_tv) {
            String phone = phoneEt.getText().toString().trim();
            if (!phoneCanUse) {
                ToastUtil.showShort("请输入可用的手机号");
                return;
            }
            presenter.getVerificationCode(phone);
        } else if (i == R.id.next_btn) {
            if (accountListRes != null) {
                String code = codeEt.getText().toString().trim();
                if (accountListRes.getUserAccounts().size() > 1) {
                    Intent intent = new Intent(mContext, AccountChoseActivity.class);
                    intent.putExtra("code", code);
                    intent.putExtra("account", accountListRes);
                    startActivity(intent);
                } else if (accountListRes.getUserAccounts().size() == 1) {
                    presenter.getSign(accountListRes.getUserAccounts().get(0), code);
                }

            }

        }
    }
}

package com.ybkj.syzs.deliver.module.auth.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpFragment;
import com.ybkj.syzs.deliver.bean.response.PhoneRes;
import com.ybkj.syzs.deliver.module.auth.presenter.AccountBackPsdPresenter;
import com.ybkj.syzs.deliver.module.auth.view.IAccountBackPsdView;
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
public class AccountBackPsdFragment extends BaseMvpFragment<AccountBackPsdPresenter> implements IAccountBackPsdView {
    @BindView(R.id.auth_account_et)
    ClearEditText authAccountEt;
    @BindView(R.id.layout_account_error)
    LinearLayout layoutAccountError;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.code_et)
    ClearEditText codeEt;
    @BindView(R.id.get_sms_tv)
    TextView getSmsTv;
    @BindView(R.id.next_btn)
    Button nextBtn;
    private boolean accountCanUse = false;
    private String phone;

    @Override
    protected void injectPresenter() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        authAccountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutAccountError.setVisibility(View.GONE);
                phoneEt.setText("");
                accountCanUse = false;
                if (s.length() > 5) {
                    presenter.getPhone(s.toString().trim());
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
                if (s.length() == 6 && accountCanUse) {
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
        return R.layout.auth_fragment_account_back_psd;
    }

    @Override
    protected String getSimpleNme() {
        return null;
    }


    @OnClick({R.id.get_sms_tv, R.id.next_btn})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.get_sms_tv) {
            if (!accountCanUse) {
                ToastUtil.showShort("请输入可用的账号");
                return;
            }
            presenter.getVerificationCode(phone);
        } else if (i == R.id.next_btn) {
            String code = codeEt.getText().toString().trim();
            presenter.getSign(authAccountEt.getText().toString().trim(), code);
        }
    }

    @Override
    public void accountError(String msg) {
        layoutAccountError.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadPhone(PhoneRes res) {
        layoutAccountError.setVisibility(View.GONE);
        accountCanUse = true;
        phone = res.getPhoneNumber();
        if (!TextUtils.isEmpty(phone) && phone.length() > 7) {
            String encryptPhone = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
            phoneEt.setText(encryptPhone);
        }
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
        intent.putExtra("account", authAccountEt.getText().toString().trim());
        startActivity(intent);
    }
}

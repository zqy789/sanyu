package com.ybkj.syzs.deliver.module.auth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.AccountListRes;
import com.ybkj.syzs.deliver.module.auth.presenter.SignPresenter;
import com.ybkj.syzs.deliver.module.auth.view.ISignView;
import com.ybkj.syzs.deliver.ui.adapter.AccountAdapter;
import com.ybkj.syzs.deliver.ui.view.recyclerview.XRecyclerView;

import butterknife.BindView;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/24.
 * Email 15384030400@163.com
 */
public class AccountChoseActivity extends BaseMvpActivity<SignPresenter> implements ISignView {
    @BindView(R.id.recycler_account)
    XRecyclerView recyclerAccount;

    private AccountAdapter accountAdapter;
    private String code;
    private String account;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_chose_account;
    }

    @Override
    protected void initView() {
        accountAdapter = new AccountAdapter(mContext);
        recyclerAccount.setAdapter(accountAdapter);
        accountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                account = accountAdapter.getItem(position);
                presenter.getSign(account, code);
            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            AccountListRes accountListRes = (AccountListRes) bundle.getSerializable("account");
            accountAdapter.setNewData(accountListRes.getUserAccounts());
            code = bundle.getString("code");
        }


    }

    @Override
    public void loadSign(String sign) {
        Intent intent = new Intent(mContext, ResetPasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("account", account);
        bundle.putString("sign", sign);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

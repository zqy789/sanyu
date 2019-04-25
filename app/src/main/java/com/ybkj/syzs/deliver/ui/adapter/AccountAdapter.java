package com.ybkj.syzs.deliver.ui.adapter;

import android.content.Context;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseAdapter;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseViewHolder;


/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/24.
 * Email 15384030400@163.com
 */
public class AccountAdapter extends XBaseAdapter<String> {
    public AccountAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.auth_recycler_item_account;
    }

    @Override
    protected void convert(XBaseViewHolder helper, String item) {
        helper.setText(R.id.account_tv, item);
    }
}

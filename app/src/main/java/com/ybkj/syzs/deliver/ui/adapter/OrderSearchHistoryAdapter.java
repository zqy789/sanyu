package com.ybkj.syzs.deliver.ui.adapter;

import android.content.Context;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseAdapter;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseViewHolder;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/13.
 * Email 15384030400@163.com
 */
public class OrderSearchHistoryAdapter extends XBaseAdapter<String> {

    public OrderSearchHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.recycle_item_order_search_history;
    }

    @Override
    protected void convert(XBaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title, item);
    }
}

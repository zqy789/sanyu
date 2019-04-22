package com.ybkj.syzs.deliver.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.bean.AreaModel;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseAdapter;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseViewHolder;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  区号选择适配器
 * - @Time:  2018/8/1
 * - @Emaill:  380948730@qq.com
 */
public class AreaSelectAdapter extends XBaseAdapter<AreaModel> {
    public AreaSelectAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.recycle_item_area;
    }

    @Override
    protected void convert(XBaseViewHolder helper, AreaModel item) {
        TextView name = helper.getView(R.id.area_item_name);
        TextView id = helper.getView(R.id.area_item_id);

        name.setText(item.getName());
        id.setText(item.getId());
    }
}

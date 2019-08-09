package com.ybkj.syzs.deliver.ui.adapter;

import android.content.Context;
import android.widget.Toast;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseAdapter;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseViewHolder;
import com.ybkj.syzs.deliver.ui.view.recyclerview.XRecyclerView;
import com.ybkj.syzs.deliver.utils.DateUtil;

import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/7/30.
 * Email 15384030400@163.com
 */
public class OrderDetailListAdapter extends XBaseAdapter<OrderDetailRes.OrderBean.ExpressBoListBean> {
    public OrderDetailListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {

        return R.layout.recycle_item_order_detail_list;
    }

    @Override
    protected void convert(XBaseViewHolder helper, OrderDetailRes.OrderBean.ExpressBoListBean expressBoListBean) {
        helper.setText(R.id.tv_express_n0, "快递单号：" + expressBoListBean.getExpressOrderNumber());
        helper.setText(R.id.tv_express_name, "快递名称：" + expressBoListBean.getExpressName());
        helper.setText(R.id.tv_poster, "发货员：" + expressBoListBean.getOperatorAccount());
        helper.setText(R.id.tv_post_time, "发货时间：" + DateUtil.longToTimeStr(expressBoListBean.getExpressTime(), DateUtil.dateFormat2));

        OrderDetailGoodAdapter goodAdapter  = new OrderDetailGoodAdapter(mContext);
        XRecyclerView order_list = helper.itemView.findViewById(R.id.order_list);
        order_list.setAdapter(goodAdapter);
        goodAdapter.setNewData(expressBoListBean.getGoods());
    }
}

package com.ybkj.syzs.deliver.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseAdapter;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseViewHolder;
import com.ybkj.syzs.deliver.utils.DateUtil;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;

import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/18.
 * Email 15384030400@163.com
 */
public class OrderListAdapter extends XBaseAdapter<OrderListRes.ListBean> {
    private static final int ORDER_STATUS_POSTED = 5;
    private static final int ORDER_STATUS_WAIT_POST = 3;
    private static final int ORDER_STATUS_COMPLETE = 7;

    public OrderListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 0) {
            return HEADER_VIEW;
        }
        if (mData.size() < 1) {
            return super.getItemViewType(position);
        }
        final OrderListRes.ListBean item = getItem(position);
        return item == null ? super.getItemViewType(position) : item.getOrderStatus();
    }

    @Override
    protected int getLayoutResId(int viewType) {
        int viewRes;
        switch (viewType) {
            case ORDER_STATUS_WAIT_POST:
                viewRes = R.layout.recycle_item_order_wait_post;
                break;
            case ORDER_STATUS_COMPLETE:
            case ORDER_STATUS_POSTED:
                viewRes = R.layout.recycle_item_order_posted;
                break;
            default:
                viewRes = R.layout.recycle_item_order_posted;
                break;
        }
        return viewRes;
    }

    @Override
    protected void convert(XBaseViewHolder helper, OrderListRes.ListBean item) {
        helper.setText(R.id.tv_order_num, "订单号:" + item.getOrderNo());
        helper.setText(R.id.tv_time, "下单时间:" + DateUtil.longToTimeStr(item.getAddTime(), DateUtil.dateFormat2));
        helper.setText(R.id.tv_receiver_name, item.getReceiverName() + "    " + item.getReceiverPhone());
        helper.setText(R.id.tv_receiver_address, getReceiverAddaress(item));
        TextView tvOrderStatus = helper.getView(R.id.tv_order_status);
        if (item.getOrderStatus() == ORDER_STATUS_POSTED) {
            helper.setText(R.id.tv_goods_num, "订单商品" + (item.getGoods() == null ? 0 : item.getGoods().size()) + "件");
            helper.setText(R.id.tv_order_status, "已发货");
            tvOrderStatus.setTextColor(ResourcesUtil.getColor(R.color.auth_color_666));
        } else if (item.getOrderStatus() == ORDER_STATUS_WAIT_POST) {
            setupGoodsList(helper, item.getGoods());
            helper.setText(R.id.tv_order_status, "待发货");
            tvOrderStatus.setTextColor(ResourcesUtil.getColor(R.color.order_status_orange));
            helper.addOnClickListener(R.id.btn_post);
        } else if (item.getOrderStatus() == ORDER_STATUS_COMPLETE) {
            helper.setText(R.id.tv_goods_num, "订单商品" + (item.getGoods() == null ? 0 : item.getGoods().size()) + "件");
            helper.setText(R.id.tv_order_status, "已完成");
            tvOrderStatus.setTextColor(ResourcesUtil.getColor(R.color.auth_color_666));
        }
    }

    private String getReceiverAddaress(OrderListRes.ListBean item) {
        return item.getReceiverProvince() + item.getReceiverCity() + item.getReceiverCounty() + item.getReceiverDetail();
    }

    private void setupGoodsList(XBaseViewHolder helper, List<OrderListRes.ListBean.GoodsBean> orderGoodsList) {
        // 展示商品列表
        final RecyclerView recyclerView = helper.getView(R.id.recycler_goods);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        OrderGoodsAdapter goodsAdapter = new OrderGoodsAdapter(mContext);
        goodsAdapter.setNewData(orderGoodsList);
        recyclerView.setAdapter(goodsAdapter);

    }
}

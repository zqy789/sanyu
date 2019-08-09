package com.ybkj.syzs.deliver.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseAdapter;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseViewHolder;
import com.ybkj.syzs.deliver.ui.view.RoundImageView;
import com.ybkj.syzs.deliver.ui.view.recyclerview.XRecyclerView;
import com.ybkj.syzs.deliver.utils.DateUtil;
import com.ybkj.syzs.deliver.utils.ImageLoadUtils;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;

import java.util.Collections;
import java.util.List;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/18.
 * Email 15384030400@163.com
 */
public class OrderDetailListAdapter1 extends XBaseAdapter<OrderDetailRes.OrderBean.ExpressBoListBean> {
    private static final int ADRESS = 0;
    private static final int CONTENT = 1;


    public OrderDetailListAdapter1(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
//        switch (position){
//            case 0:
//
//                break;
//        }
//        if (position < 0) {
//            return HEADER_VIEW;
//        }
//        if (mData.size() < 1) {
//            return super.getItemViewType(position);
//        }
//        final OrderListRes.ListBean item = getItem(position);
//        return item == null ? super.getItemViewType(position) : item.getOrderStatus();
    }


    @Override
    protected int getLayoutResId(int viewType) {
//        int viewRes;
//        switch (viewType) {
//            case ORDER_STATUS_PART_POST:
//            case ORDER_STATUS_WAIT_POST:
//                viewRes = R.layout.recycle_item_order_wait_post;
//                break;
//            case ORDER_STATUS_PART_POSTED:
//            case ORDER_STATUS_COMPLETE:
//            case ORDER_STATUS_POSTED:
//                viewRes = R.layout.recycle_item_order_posted;
//                break;
//            default:
//                viewRes = R.layout.recycle_item_order_posted;
//                break;
//        }
//        return viewRes;

        switch (viewType) {
            case ADRESS:
//                return R.layout.layout_order_detail_adress;
                return R.layout.layout_common_recyclerview;
            case CONTENT:
                return R.layout.layout_common_recyclerview;
        }
        return R.layout.layout_common_recyclerview;
    }

    @Override
    protected void convert(XBaseViewHolder helper,OrderDetailRes.OrderBean.ExpressBoListBean expressBoListBean) {
//        OrderDetailRes.OrderBean orderBean = response.getOrder();
        if(helper.getPosition() == 0){
//            helper.setText(R.id.tv_receiver_name, orderBean.getReceiverName() + "    " + orderBean.getReceiverPhone());
//            helper.setText(R.id.tv_address, "地址：" + orderBean.getReceiverProvince() + orderBean.getReceiverCity() + orderBean.getReceiverCounty() + orderBean.getReceiverDetail());
//
//            helper.setText(R.id.tv_order_no, "订单号：" + orderBean.getOrderNo());
//            String orderStatus = orderBean.getOrderStatus()==7?"已发货":"已完成";
//            helper.setText(R.id.tv_status, orderStatus);
//            helper.setText(R.id.tv_add_time, "下单时间：" + DateUtil.longToTimeStr(orderBean.getAddTime(), DateUtil.dateFormat2));
            OrderDetailGoodAdapter goodAdapter  = new OrderDetailGoodAdapter(mContext);
            XRecyclerView order_recycle = helper.itemView.findViewById(R.id.order_recycle);
            order_recycle.setAdapter(goodAdapter);

//            goodListAdapter.setNewData(Collections.singletonList(list));
            goodAdapter.setNewData(expressBoListBean.getGoods());
        }else{
            OrderDetailGoodAdapter goodAdapter  = new OrderDetailGoodAdapter(mContext);
            XRecyclerView order_recycle = helper.itemView.findViewById(R.id.order_recycle);
            order_recycle.setAdapter(goodAdapter);

//            goodListAdapter.setNewData(Collections.singletonList(list));
            goodAdapter.setNewData(expressBoListBean.getGoods());
        }

    }
}

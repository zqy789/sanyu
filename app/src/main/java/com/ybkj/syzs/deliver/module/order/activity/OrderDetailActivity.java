package com.ybkj.syzs.deliver.module.order.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;
import com.ybkj.syzs.deliver.module.order.presenter.OrderDetailPresenter;
import com.ybkj.syzs.deliver.module.order.view.IOrderDetailView;
import com.ybkj.syzs.deliver.ui.adapter.OrderDetailGoodAdapter;
import com.ybkj.syzs.deliver.ui.view.recyclerview.XRecyclerView;
import com.ybkj.syzs.deliver.utils.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class OrderDetailActivity extends BaseMvpActivity<OrderDetailPresenter> implements IOrderDetailView {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_receiver_name)
    TextView tvReceiverName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.order_recycle)
    XRecyclerView orderRecycle;
    @BindView(R.id.tv_express_name)
    TextView tvExpressName;
    @BindView(R.id.tv_express_n0)
    TextView tvExpressN0;
    @BindView(R.id.tv_poster)
    TextView tvPoster;
    @BindView(R.id.tv_post_time)
    TextView tvPostTime;

    TextView tvOrderNo;
    TextView tvStatus;
    TextView tvAddTime;

    private OrderDetailGoodAdapter goodAdapter;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        goodAdapter = new OrderDetailGoodAdapter(mContext);
        orderRecycle.setAdapter(goodAdapter);
        orderRecycle.setNestedScrollingEnabled(false);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.order_good_header, null);
        tvOrderNo = headerView.findViewById(R.id.tv_order_no);
        tvStatus = headerView.findViewById(R.id.tv_status);
        tvAddTime = headerView.findViewById(R.id.tv_add_time);
        goodAdapter.addHeaderView(headerView);
    }

    @Override
    protected void initData() {
        String orderNo = getIntent().getStringExtra("orderNo");
        presenter.getOrderDetail(orderNo);
    }

    @Override
    public void loadOrderData(OrderDetailRes response) {
        OrderDetailRes.OrderBean orderBean = response.getOrder();
        tvReceiverName.setText(orderBean.getReceiverName() + "    " + orderBean.getReceiverPhone());
        tvAddress.setText("地址：" + orderBean.getReceiverProvince() + orderBean.getReceiverCity() + orderBean.getReceiverCounty() + orderBean.getReceiverDetail());

        tvExpressN0.setText("快递单号：" + orderBean.getExpressOrderNumber());
        tvExpressName.setText("快递名称：" + orderBean.getExpressName());
        tvPoster.setText("发货员：" + orderBean.getOperatorAccount());
        tvPostTime.setText("发货时间：" + DateUtil.longToTimeStr(orderBean.getExpressTime(), DateUtil.dateFormat2));
        goodAdapter.setNewData(response.getOrder().getGoods());

        tvOrderNo.setText("订单号：" + orderBean.getOrderNo());
        tvStatus.setText("已发货");
        tvAddTime.setText("下单时间：" + DateUtil.longToTimeStr(orderBean.getAddTime(), DateUtil.dateFormat2));
    }


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public boolean isImmersiveStatusBar() {
        return true;
    }
}
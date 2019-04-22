package com.ybkj.syzs.deliver.module.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpFragment;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.module.order.presenter.PostedOrderPresenter;
import com.ybkj.syzs.deliver.module.order.view.IPostedOrderView;
import com.ybkj.syzs.deliver.ui.adapter.OrderListAdapter;
import com.ybkj.syzs.deliver.ui.view.TimeSortView;
import com.ybkj.syzs.deliver.ui.view.recyclerview.DividerItemDecoration;
import com.ybkj.syzs.deliver.ui.view.recyclerview.XRecyclerView;
import com.ybkj.syzs.deliver.ui.view.refreshlayout.XRefreshLayout;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/18.
 * Email 15384030400@163.com
 */
public class PostedOrderFragment extends BaseMvpFragment<PostedOrderPresenter> implements IPostedOrderView {
    private static final String ORDER_SATTUS = "orderStatus";
    @BindView(R.id.order_recycle)
    XRecyclerView orderRecycle;
    @BindView(R.id.order_refresh)
    XRefreshLayout orderRefresh;
    //    @BindView(R.id.shop_goods_layout)
//    BackToTopLayout shopGoodsLayout;
    @BindView(R.id.timeSortView)
    TimeSortView timeSortView;
    //    private TimeSortView timeSortView;
    private int orderStatus;
    private OrderListAdapter orderListAdapter;
    private int timeType = 6;
    private long time = 1;

    public static PostedOrderFragment getInstance(int orderStatus) {
        PostedOrderFragment fragment = new PostedOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ORDER_SATTUS, orderStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void injectPresenter() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        orderListAdapter = new OrderListAdapter(mContext);
        orderRecycle.setAdapter(orderListAdapter);

        View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_order_list, null);
        //timeSortView = headerView.findViewById(R.id.timeSortView);
        //orderListAdapter.addHeaderView(headerView);

        orderRecycle.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, 8,
                ResourcesUtil.getColor(R.color.project_base_color_background1)));
        orderRefresh.setOnRefreshListener(new XRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getOrderData(timeType, time, true, false);
            }

            @Override
            public boolean checkCanDoRefresh(View content, View header) {
                return orderRecycle.isCanRefresh();
            }
        });
        orderListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.getOrderData(timeType, time, false, false);
            }
        }, orderRecycle);
        orderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("orderNo", orderListAdapter.getItem(position).getOrderNo());
                ActivityManager.gotoActivity(mContext, intent);
            }
        });
        timeSortView.setOnModeChangeListener(new TimeSortView.OnModeChangeListener() {
            @Override
            public void onModeChange(Enum mode, Date date) {
                if (mode == TimeSortView.Mode.CURRENT_MONTH) {
                    timeType = 6;
                } else if (mode == TimeSortView.Mode.LAST_MONTH) {
                    timeType = 1;
                } else if (mode == TimeSortView.Mode.CURRENT_YEAR) {
                    timeType = 3;
                } else if (mode == TimeSortView.Mode.RECENT_YEAR) {
                    timeType = 4;
                } else if (mode == TimeSortView.Mode.ALL) {
                    timeType = 5;
                } else if (mode == TimeSortView.Mode.CUSTOM_MONTH) {
                    timeType = 1;
                } else if (mode == TimeSortView.Mode.CUSTOM_YEAR) {
                    timeType = 2;
                }
                time = date.getTime();
                presenter.getOrderData(timeType, time, true, true);
            }
        });
    }

    @Override
    protected void initData() {
        orderStatus = getArguments().getInt(ORDER_SATTUS);
        presenter.getOrderData(timeType, time, true, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_posted_order;
    }

    @Override
    protected String getSimpleNme() {
        return null;
    }

    @Override
    public void loadMore(List<OrderListRes.ListBean> data) {
        orderListAdapter.addData(data);
        orderListAdapter.notifyDataSetChanged();
        orderListAdapter.loadMoreComplete();
    }

    @Override
    public void refreshList(List<OrderListRes.ListBean> data) {
        orderListAdapter.setNewData(data);
        orderRefresh.refreshComplete();
    }

    @Override
    public void loadMoreEnd() {
        orderListAdapter.loadMoreEnd();
    }

    @Override
    public void showEmptyView() {
        orderListAdapter.getData().clear();
        //       showNetRecycleEmptyView(recycleView);
        orderRefresh.refreshComplete();
    }
}

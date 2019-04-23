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
import com.ybkj.syzs.deliver.module.order.presenter.WaitPostOrderPresenter;
import com.ybkj.syzs.deliver.module.order.view.IWaitPostOrderView;
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
public class WaitPostOrderFragment extends BaseMvpFragment<WaitPostOrderPresenter> implements IWaitPostOrderView {
    private static final String ORDER_SATTUS = "orderStatus";
    @BindView(R.id.order_recycle)
    XRecyclerView orderRecycle;
    @BindView(R.id.order_refresh)
    XRefreshLayout orderRefresh;

    //  private TimeSortView timeSortView;
//    @BindView(R.id.shop_goods_layout)
//    BackToTopLayout shopGoodsLayout;
    @BindView(R.id.timeSortView)
    TimeSortView timeSortView;
    private int orderStatus;

    private OrderListAdapter orderListAdapter;
    private int timeType = 6;
    private long time = 1;

    public static WaitPostOrderFragment getInstance(int orderStatus) {
        WaitPostOrderFragment fragment = new WaitPostOrderFragment();
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
        // timeSortView = headerView.findViewById(R.id.timeSortView);
        // orderListAdapter.addHeaderView(headerView);


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

        orderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_post) {
                    Intent intent = new Intent(mContext, GoodsPostActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", orderListAdapter.getItem(position));
                    intent.putExtras(bundle);
                    ActivityManager.gotoActivity(mContext, intent);
                }
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

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wait_post_order;
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

    @Override
    public void onResume() {
        super.onResume();
        presenter.getOrderData(timeType, time, true, true);
    }
}

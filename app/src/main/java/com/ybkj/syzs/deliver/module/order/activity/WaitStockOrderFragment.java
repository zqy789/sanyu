package com.ybkj.syzs.deliver.module.order.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpFragment;
import com.ybkj.syzs.deliver.bean.request.OutOfStock;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.module.order.presenter.WaitStockOrderPresenter;
import com.ybkj.syzs.deliver.module.order.view.IWaitStockOrderView;
import com.ybkj.syzs.deliver.ui.adapter.OrderListAdapter;
import com.ybkj.syzs.deliver.ui.view.TimeSortView;
import com.ybkj.syzs.deliver.ui.view.recyclerview.DividerItemDecoration;
import com.ybkj.syzs.deliver.ui.view.recyclerview.XRecyclerView;
import com.ybkj.syzs.deliver.ui.view.refreshlayout.XRefreshLayout;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 * Author dangchuancai
 * Created on 2019/8/9.
 * Email 15384030400@163.com
 */
public class WaitStockOrderFragment extends BaseMvpFragment<WaitStockOrderPresenter> implements IWaitStockOrderView {
    private static final String ORDER_SATTUS = "orderStatus";
    @BindView(R.id.order_recycle)
    XRecyclerView orderRecycle;
    @BindView(R.id.order_refresh)
    XRefreshLayout orderRefresh;
    @BindView(R.id.timeSortView)
    TimeSortView timeSortView;
    @BindView(R.id.btn_post)
    Button btnPost;
    private int orderStatus;

    private OrderListAdapter orderListAdapter;
    private int timeType = 7;
    private long time = new Date().getTime();
    HashMap<Integer,Boolean> map = new HashMap<Integer,Boolean>();

    String orderNoList;

    public static WaitStockOrderFragment getInstance(int orderStatus) {
        WaitStockOrderFragment fragment = new WaitStockOrderFragment();
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
        orderListAdapter = new OrderListAdapter(mContext,map);
        orderRecycle.setAdapter(orderListAdapter);

        View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_order_list, null);
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

        timeSortView.setOnModeChangeListener(new TimeSortView.OnModeChangeListener() {
            @Override
            public void onModeChange(Enum mode, Date date) {
                if (mode == TimeSortView.Mode.CURRENT_DAY){
                    timeType = 7;
                }else if (mode == TimeSortView.Mode.CURRENT_MONTH) {
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
    public void stockSuccess() {
        ToastUtil.showShort("出库成功");
        orderNoList = null;
        presenter.getOrderData(timeType, time, true, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wait_stock_order;
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
        addMap(data);
    }

    @Override
    public void refreshList(List<OrderListRes.ListBean> data) {
        orderListAdapter.setNewData(data);
        orderRefresh.refreshComplete();
        initMap(data);
    }

    @Override
    public void loadMoreEnd() {
        orderListAdapter.loadMoreEnd();
    }

    @Override
    public void showEmptyView() {
        orderListAdapter.getData().clear();
        showNetRecycleEmptyView(orderRecycle);
        orderRefresh.refreshComplete();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getOrderData(timeType, time, true, true);
    }

    private void initMap(List<OrderListRes.ListBean> data){
        map.clear();
        for (int i = 0;i<data.size();i++){
            map.put(i,false);
        }
    }
    private void addMap(List<OrderListRes.ListBean> data){
        for (int i = 0;i<data.size();i++){
            map.put(i,false);
        }
    }

    @OnClick({R.id.btn_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_post:
                presenter.outOfStock(getSelectList(),true);
                break;
        }
    }

    private OutOfStock getSelectList(){
        orderNoList =null;
        OutOfStock outOfStock = new OutOfStock();
        List<OrderListRes.ListBean> data = orderListAdapter.getData();
        for (int i = 0;i<map.size();i++){
            if(map.get(i)){
                if (orderNoList == null){
                    orderNoList = data.get(i).getOrderNo();
                }else{
                    orderNoList = orderNoList+","+data.get(i).getOrderNo();
                }
            }
        }
        outOfStock.setOrderNoList(orderNoList);
        return outOfStock;
    }
}

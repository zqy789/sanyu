package com.ybkj.syzs.deliver.module.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.bean.response.OrderSearchHistory;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.manager.HistoryManager;
import com.ybkj.syzs.deliver.module.order.presenter.OrderSearchPresenter;
import com.ybkj.syzs.deliver.module.order.view.IOrderSearchView;
import com.ybkj.syzs.deliver.ui.adapter.OrderListAdapter;
import com.ybkj.syzs.deliver.ui.adapter.OrderSearchHistoryAdapter;
import com.ybkj.syzs.deliver.ui.view.recyclerview.DividerItemDecoration;
import com.ybkj.syzs.deliver.ui.view.recyclerview.XRecyclerView;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class OrderSearchActivity extends BaseMvpActivity<OrderSearchPresenter> implements IOrderSearchView {

    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.image_search)
    ImageView imageSearch;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.image_clear)
    ImageView imageClear;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    @BindView(R.id.layout_history_clear)
    RelativeLayout layoutHistoryClear;
    @BindView(R.id.layout_history_header)
    RelativeLayout layoutHistoryHeader;
    @BindView(R.id.recycler_history)
    RecyclerView recyclerHistory;
    @BindView(R.id.layout_history)
    RelativeLayout layoutHistory;


    private OrderListAdapter searchAdapter;
    private OrderSearchHistoryAdapter historyAdapter;
    private String keyword;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_search;
    }

    @Override
    protected void initView() {
        editSearch.setHint("请输入关键字搜索订单");

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String content = editSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.showShort("订单号不能为空");
                        return true;
                    }
                    if (content.length() < 5) {
                        ToastUtil.showShort("请输入大于4位的订单号");
                        return true;
                    }
                    HistoryManager.addOrderSearchHistory(content);
                    presenter.getOrderData(content);
                    return true;
                }
                return false;
            }
        });
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                String content = editSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(content) && content.length() > 4) {
                    presenter.getOrderData(content);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(mContext)
                .setChildGravity(Gravity.LEFT)
                .setScrollingEnabled(false)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        recyclerHistory.setLayoutManager(chipsLayoutManager);
        historyAdapter = new OrderSearchHistoryAdapter(mContext);
        recyclerHistory.setAdapter(historyAdapter);

        searchAdapter = new OrderListAdapter(mContext);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, 8,
                ResourcesUtil.getColor(R.color.project_base_color_background1)));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(searchAdapter);


        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                editSearch.setText(historyAdapter.getItem(position));
                editSearch.setSelection(historyAdapter.getItem(position).length());
            }
        });
        searchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_post && searchAdapter.getItem(position).getOrderStatus() == 3) {
                    HistoryManager.addOrderSearchHistory(keyword);

                    Intent intent = new Intent(mContext, GoodsPostActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", searchAdapter.getItem(position));
                    intent.putExtras(bundle);
                    ActivityManager.gotoActivity(mContext, intent);
                }
            }
        });
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (searchAdapter.getItem(position).getOrderStatus() == 5) {
                    HistoryManager.addOrderSearchHistory(keyword);

                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra("orderNo", searchAdapter.getItem(position).getOrderNo());
                    ActivityManager.gotoActivity(mContext, intent);
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.setText("");
                layoutHistory.setVisibility(View.VISIBLE);
                OrderSearchHistory history = HistoryManager.getOrderSearchHistory();
                if (history != null) {
                    historyAdapter.setNewData(history.getKewords());
                }
            }
        });
        layoutHistoryClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryManager.clearOrderSearchHistory();
                List<String> strings = new ArrayList<>();
                historyAdapter.setNewData(strings);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void refreshList(List<OrderListRes.ListBean> data) {
        layoutHistory.setVisibility(View.GONE);
        keyword = editSearch.getText().toString().trim();
        searchAdapter.setNewData(data);
    }

}

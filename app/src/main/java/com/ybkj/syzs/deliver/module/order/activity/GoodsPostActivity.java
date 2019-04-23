package com.ybkj.syzs.deliver.module.order.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.module.order.presenter.GoodsPostPresenter;
import com.ybkj.syzs.deliver.module.order.view.IGoodsPostView;
import com.ybkj.syzs.deliver.ui.adapter.GoodsPostAdapter;
import com.ybkj.syzs.deliver.ui.view.recyclerview.XRecyclerView;
import com.ybkj.syzs.deliver.utils.DateUtil;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class GoodsPostActivity extends BaseMvpActivity<GoodsPostPresenter> implements IGoodsPostView {
    public static final int REQUEST_SCAN = 90;
    public static final int REQUEST_EXPRESS_SCAN = 91;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edit_express_no)
    EditText editExpressNo;
    @BindView(R.id.edit_express_name)
    EditText editExpressName;
    @BindView(R.id.order_recycle)
    XRecyclerView orderRecycle;
    @BindView(R.id.image_order_no_scan)
    ImageView imageOrderNoScan;
    @BindView(R.id.btn_post)
    Button btnPost;
    TextView tvOrderNo;
    TextView tvStatus;
    TextView tvAddTime;
    private OrderListRes.ListBean order;
    private GoodsPostAdapter goodsAdapter;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_post;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        order = (OrderListRes.ListBean) getIntent().getSerializableExtra("order");
        goodsAdapter = new GoodsPostAdapter(mContext);
        orderRecycle.setAdapter(goodsAdapter);
        goodsAdapter.setNewData(order.getGoods());
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.order_good_header, null);
        tvOrderNo = headerView.findViewById(R.id.tv_order_no);
        tvStatus = headerView.findViewById(R.id.tv_status);
        tvAddTime = headerView.findViewById(R.id.tv_add_time);
        goodsAdapter.addHeaderView(headerView);

        tvOrderNo.setText("订单号：" + order.getOrderNo());
        tvAddTime.setText("下单时间：" + DateUtil.longToTimeStr(order.getAddTime(), DateUtil.dateFormat2));


        goodsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_entry) {
                    toScanPublicCode(position);
                }
            }
        });
        imageOrderNoScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toScanExpress();
            }
        });
    }

    @Override
    public void postSuccess() {
        ToastUtil.showShort("发货成功");
        finish();
    }

    @OnClick({R.id.btn_back, R.id.btn_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_post:
                String expressNo = editExpressNo.getText().toString().trim();
                String expressName = editExpressName.getText().toString().trim();
                presenter.postOrder(expressName, expressNo, order.getOrderNo(), goodsAdapter.getData());
                break;
        }
    }

    @Override
    public boolean isImmersiveStatusBar() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ScanSecurityActivity.SCAN_SUCCESS) {
            OrderListRes.ListBean order = (OrderListRes.ListBean) data.getExtras().getSerializable("order");
            List<OrderListRes.ListBean.GoodsBean> goodsBeanList = order.getGoods();
            goodsAdapter.setNewData(goodsBeanList);
            this.order.setGoods(goodsBeanList);
        } else if (resultCode == ScanCodeActivity.EXPRESS_SUCCESS) {
            editExpressNo.setText(data.getStringExtra("expressNo"));
        }
    }

    @SuppressLint("CheckResult")
    private void toScanExpress() {
        int cameraPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            new RxPermissions(mContext).request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE)
                    .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    //true表示获取权限成功（android6.0以下默认为true）
                                    Intent intent = new Intent(mContext, ScanCodeActivity.class);
                                    startActivityForResult(intent, REQUEST_EXPRESS_SCAN);
                                } else {
                                    ToastUtil.showShort("获取相机权限失败");
                                }
                            },
                            Throwable::printStackTrace
                    );
        } else {
            Intent intent = new Intent(mContext, ScanCodeActivity.class);
            startActivityForResult(intent, REQUEST_EXPRESS_SCAN);
        }

    }

    @SuppressLint("CheckResult")
    private void toScanPublicCode(int position) {
        int cameraPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            new RxPermissions(mContext).request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE)
                    .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    Intent intent = new Intent(mContext, ScanSecurityActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("position", position);
                                    bundle.putSerializable("order", order);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, REQUEST_SCAN);
                                } else {
                                    ToastUtil.showShort("获取相机权限失败");
                                }
                            },
                            Throwable::printStackTrace
                    );
        } else {
            Intent intent = new Intent(mContext, ScanSecurityActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putSerializable("order", order);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_SCAN);
        }

    }

}

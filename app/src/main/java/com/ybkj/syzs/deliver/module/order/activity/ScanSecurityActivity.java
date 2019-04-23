package com.ybkj.syzs.deliver.module.order.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.module.order.presenter.ScanSecurityPresenter;
import com.ybkj.syzs.deliver.module.order.view.ScanSecurityView;
import com.ybkj.syzs.deliver.module.user.activity.PictureChoseActivity;
import com.ybkj.syzs.deliver.utils.FullScreenUtils;
import com.ybkj.syzs.deliver.utils.ImageLoadUtils;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 录入防伪码
 */
public class ScanSecurityActivity extends BaseMvpActivity<ScanSecurityPresenter> implements ScanSecurityView {
    public static final int SCAN_SUCCESS = 101;
    public static final int REQUEST_ALBUM = 102;
    //返回
    @BindView(R.id.scan_code_cancel)
    ImageView cancel;
    //商品图标
    @BindView(R.id.shop_logo_iv)
    ImageView shopLogoIv;
    //商品名称
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    //商品防伪码
    @BindView(R.id.code_tv)
    TextView codeTv;
    @BindView(R.id.orderNo_tv)
    TextView OrderNo;
    @BindView(R.id.tv_from_album)
    TextView tvFromAlbum;
    private Runnable runnable;
    private int scanPosition = 0;//当前扫描的集合位置
    private CaptureFragment captureFragment;
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

            //查询公码是否可用
            presenter.checkCodeUse(result);

        }

        @Override
        public void onAnalyzeFailed() {
            ToastUtil.showShort("无法识别此防伪码");
            resetScan();
        }
    };
    private Handler mHandler = new Handler();
    private List<OrderListRes.ListBean.GoodsBean> goodsList;//商品列表
    private OrderListRes.ListBean order;//订单

    /**
     * 解析二维码 （使用解析RGB编码数据的方式）
     *
     * @param barcode
     * @return
     */
    public static Result decodeBarcodeRGB(Bitmap barcode) {
        int width = barcode.getWidth();
        int height = barcode.getHeight();
        int[] data = new int[width * height];
        barcode.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(bitmap1);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        barcode.recycle();
        barcode = null;
        return result;
    }

    @Override
    protected void initData() {
        scanPosition = getIntent().getIntExtra("position", -1);
        order = (OrderListRes.ListBean) getIntent().getSerializableExtra("order");
        goodsList = order.getGoods();
        OrderListRes.ListBean.GoodsBean bean = goodsList.get(scanPosition);
        shopNameTv.setText(bean.getGoodsName());
        OrderNo.setText("订单号：" + order.getOrderNo());
        if (!TextUtils.isEmpty(bean.getGoodsPublicCode())) {
            codeTv.setText("防伪码：" + bean.getGoodsPublicCode());
        }
        ImageLoadUtils.loadUrlImage(mContext, bean.getGoodsImg(), shopLogoIv);

        tvFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPicture();
            }
        });
    }

    /**
     * 重置扫描
     */
    private void resetScan() {
        if (captureFragment != null && captureFragment.getHandler() != null) {
            captureFragment.getHandler().sendEmptyMessage(com.uuzuche.lib_zxing.R.id.restart_preview);
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_security_code;
    }

    @Override
    protected void initView() {
        FullScreenUtils.fullScreen(this);
        checkPermission();
    }

    /**
     * 检测必备权限
     */
    @SuppressLint("CheckResult")
    private void checkPermission() {
        new RxPermissions(this).request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE)
                .subscribe(aBoolean -> {
                            if (aBoolean) {
                                captureFragment = new CaptureFragment();
                                // 为二维码扫描界面设置定制化界面

                                CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_scan);

                                captureFragment.setAnalyzeCallback(analyzeCallback);
                                getSupportFragmentManager().beginTransaction().replace(R.id.scan_code_frame, captureFragment).commit();
                            } else {
                                finish();
                            }
                        },
                        Throwable::printStackTrace
                );
    }


    @OnClick({R.id.scan_code_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_code_cancel:
                mHandler.removeCallbacks(runnable);
                finish();
                break;
        }
    }


    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }


    @OnClick(R.id.btn1)
    public void onViewClicked() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        order.setGoods(goodsList);
        bundle.putSerializable("order", order);
        intent.putExtras(bundle);
        setResult(SCAN_SUCCESS, intent);
        finish();
    }

    /**
     * 没有使用此防伪码
     *
     * @param resultCode
     */
    @Override
    public void noUserCode(String resultCode) {
        for (OrderListRes.ListBean.GoodsBean goodsBean : goodsList) {
            if (resultCode.equals(goodsBean.getGoodsPublicCode())) {
                ToastUtil.showShort("请勿重复使用防伪码");
                resetScan();
                return;
            }
        }
        OrderListRes.ListBean.GoodsBean bean = goodsList.get(scanPosition);
        bean.setGoodsPublicCode(resultCode);
        codeTv.setText(resultCode);
        ToastUtil.showShort("录入成功");
        findNext();
    }

    /**
     * 重新扫描
     */
    @Override
    public void againScan() {
        resetScan();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }

    private void findNext() {
        int nextUnInputPosition = -1;//下一个未录入下标

        //先从当前位置往后遍历
        for (int i = scanPosition; i < goodsList.size(); i++) {
            OrderListRes.ListBean.GoodsBean goodsBean = goodsList.get(i);
            if (TextUtils.isEmpty(goodsBean.getGoodsPublicCode())) {
                nextUnInputPosition = i;
                break;
            }
        }
        //如果当前位置往后都没有没录入的.从第一条开始遍历
        if (nextUnInputPosition == -1) {
            for (int i = 0; i < goodsList.size(); i++) {
                OrderListRes.ListBean.GoodsBean goodsBean = goodsList.get(i);
                if (TextUtils.isEmpty(goodsBean.getGoodsPublicCode())) {
                    nextUnInputPosition = i;
                    break;
                }
            }
            //如果从第一条开始遍历完还是没有发现未录入的，弹出提示
            if (nextUnInputPosition == -1) {
                ToastUtil.showShort("当前所有商品已录入防伪码，可以点击录入完成去发货");
                scanPosition += 1;
                scanPosition = scanPosition >= goodsList.size() ? 0 : scanPosition;
            } else {
                scanPosition = nextUnInputPosition;
            }

        } else {//如果当前位置往后有没录入的，将其作为下条将要录入的目标
            scanPosition = nextUnInputPosition;
        }
        scanNext();
    }

    private void scanNext() {
        //按钮点击时间
        //3秒钟后自动扫描下一个
        long codeRequestTime = 1500;
        runnable = () -> {
            OrderListRes.ListBean.GoodsBean goodsBean = goodsList.get(scanPosition);
            shopNameTv.setText(goodsBean.getGoodsName());
            ImageLoadUtils.loadUrlImage(mContext, goodsBean.getGoodsImg(), shopLogoIv);
            codeTv.setText(goodsBean.getGoodsPublicCode());
            resetScan();
        };
        mHandler.postDelayed(runnable, codeRequestTime);
    }

    @SuppressLint("CheckResult")
    private void findPicture() {
        int storagePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            new RxPermissions(mContext).request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    Intent intent = new Intent(mContext, PictureChoseActivity.class);
                                    startActivityForResult(intent, REQUEST_ALBUM);
                                } else {
                                    ToastUtil.showShort("获取相机权限失败");
                                }
                            },
                            Throwable::printStackTrace
                    );
        } else {
            Intent intent = new Intent(mContext, PictureChoseActivity.class);
            startActivityForResult(intent, REQUEST_ALBUM);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PictureChoseActivity.SELECT_PICTURE_SUCCESS) {
            parsePhoto(data.getStringExtra("path"));
        }
    }

    private void parsePhoto(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        CodeUtils.analyzeBitmap(path, analyzeCallback);

    }
}

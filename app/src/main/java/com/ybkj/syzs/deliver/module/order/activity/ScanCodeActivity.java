package com.ybkj.syzs.deliver.module.order.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

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
import com.ybkj.syzs.deliver.base.BaseActivity;
import com.ybkj.syzs.deliver.utils.FullScreenUtils;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  二维码扫描界面
 * - @Time:  2018/8/27
 * - @Emaill:  380948730@qq.com
 */
public class ScanCodeActivity extends BaseActivity {
    public static final int EXPRESS_SUCCESS = 81;
    //返回
    @BindView(R.id.scan_code_cancel)
    ImageView cancel;
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent intent = new Intent();
            intent.putExtra("expressNo", result);
            setResult(EXPRESS_SUCCESS, intent);
            finish();
        }

        @Override
        public void onAnalyzeFailed() {
            ToastUtil.showShort("无法识别此快递单号");
            finish();
        }
    };
    //图片是否正在压缩
    private boolean isOtherCompressing;
    //按钮点击时间
    private long codeRequestTime;

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
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    protected void initView() {
        FullScreenUtils.fullScreen(this);
//        CaptureFragment captureFragment = new CaptureFragment();
//        // 为二维码扫描界面设置定制化界面
//
//        CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_scan);
//
//        captureFragment.setAnalyzeCallback(analyzeCallback);
//        getSupportFragmentManager().beginTransaction().replace(R.id.scan_code_frame, captureFragment).commit();

        checkPermission();
    }

    /**
     * 检测必备权限
     */
    @SuppressLint("CheckResult")
    private void checkPermission() {
        new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.VIBRATE)
                .subscribe(aBoolean -> {
                            if (aBoolean) {
                                CaptureFragment captureFragment = new CaptureFragment();
                                // 为二维码扫描界面设置定制化界面

                                CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_scan_number);

                                captureFragment.setAnalyzeCallback(analyzeCallback);
                                getSupportFragmentManager().beginTransaction().replace(R.id.scan_code_frame, captureFragment).commit();
                            } else {
                                finish();
                            }
                        },
                        Throwable::printStackTrace
                );
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.scan_code_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_code_cancel:
                finish();
                break;
        }
    }


}

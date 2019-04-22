package com.ybkj.syzs.deliver.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;


/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  通用提示语弹出框公共类
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public class TipDialog extends Dialog {
    private TextView titleTv;
    private TextView contentTv;
    private TextView cancelTv;
    private TextView confirmTv;

    private String titleStr;
    private String contentStr;
    private String confirmButtonStr;
    private String cancelButtonStr;

    private int confirmButtonRes = R.drawable.project_base_shape_green_round;

    private OnCancelButtonClickListener onCancelButtonClickListener;
    private OnConfirmButtonClickListener onConfirmButtonClickListener;
    private ViewGroup buttonLayout; //按钮布局
    private boolean isCancelButtonGone; //是否隐藏按钮
    private Button conformOneBtn; //单独确认按钮

    public TipDialog(@NonNull Context context) {
        super(context);
    }

    public void setConfirmButtonRes(int confirmButtonRes) {
        this.confirmButtonRes = confirmButtonRes;
        if (conformOneBtn != null) {
            conformOneBtn.setBackgroundResource(confirmButtonRes);
        }
    }

    /**
     * 取消监听
     *
     * @param onCancelButtonClickListener
     */
    public void setOnCancelButtonClickListener(OnCancelButtonClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
    }

    /**
     * 确认监听
     *
     * @param onConfirmButtonClickListener
     */
    public void setOnConfirmButtonClickListener(OnConfirmButtonClickListener onConfirmButtonClickListener) {
        this.onConfirmButtonClickListener = onConfirmButtonClickListener;
    }

    /**
     * 设置标题文字
     *
     * @param title
     */
    public void setTitleText(String title) {
        this.titleStr = title;
    }

    /**
     * 设置提示内容
     *
     * @param contentStr
     */
    public void setContentText(String contentStr) {
        this.contentStr = contentStr;
    }


    /**
     * 设置提交按钮文字
     *
     * @param confirmButtonStr
     */
    public void setConfirmButtonText(String confirmButtonStr) {
        this.confirmButtonStr = confirmButtonStr;
    }

    /**
     * 设置取消按钮文字
     *
     * @param cancelButtonStr
     */
    public void setCancelButtonText(String cancelButtonStr) {
        this.cancelButtonStr = cancelButtonStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setTitle(null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.project_base_dialog_tip);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
        //此处设置公共宽度
        setAlertDialogWidth((int) ResourcesUtil.getDimen(R.dimen.base_dimen_328));
    }

    private void initView() {
        cancelTv = (TextView) findViewById(R.id.negative_tv);
        buttonLayout = (ViewGroup) findViewById(R.id.button_btn);
        confirmTv = (TextView) findViewById(R.id.positive_tv);
        titleTv = (TextView) findViewById(R.id.title_tv);
        contentTv = (TextView) findViewById(R.id.content_tv);
        conformOneBtn = (Button) findViewById(R.id.positive_half_tv);

        conformOneBtn.setBackgroundResource(confirmButtonRes);
        if (isCancelButtonGone) {
            buttonLayout.setVisibility(View.GONE);
            conformOneBtn.setVisibility(View.VISIBLE);
        }
        cancelTv.setOnClickListener(v -> {
            if (onCancelButtonClickListener != null) {
                onCancelButtonClickListener.cancel(this, v);
            }
            dismiss();
        });

        confirmTv.setOnClickListener(v -> {
            if (onConfirmButtonClickListener != null) {
                onConfirmButtonClickListener.confirm(this, v);
            }
        });

        conformOneBtn.setOnClickListener(v -> {
            if (onConfirmButtonClickListener != null) {
                onConfirmButtonClickListener.confirm(this, v);
            }
        });

        if (!TextUtils.isEmpty(titleStr)) {
            titleTv.setText(titleStr);
        }
        if (!TextUtils.isEmpty(contentStr)) {
            contentTv.setVisibility(View.VISIBLE);
            contentTv.setText(contentStr);
        }
        if (!TextUtils.isEmpty(confirmButtonStr)) {
            confirmTv.setText(confirmButtonStr);
        }
        if (!TextUtils.isEmpty(cancelButtonStr)) {
            cancelTv.setText(cancelButtonStr);
        }
    }

    /**
     * 设置取消按钮是否隐藏
     *
     * @param isGone
     */
    public void setCancelButtonVisible(boolean isGone) {
        isCancelButtonGone = isGone;

    }


    /**
     * @param width 对话框的宽度
     */
    public void setAlertDialogWidth(int width) {
//        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
//        getWindow().setLayout(width, attrs.height);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        if (getWindow() != null) {
            lp.copyFrom(getWindow().getAttributes());
            lp.width = width;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
        }
    }


    public interface OnCancelButtonClickListener {
        void cancel(Dialog dialog, View view);
    }

    public interface OnConfirmButtonClickListener {
        void confirm(Dialog dialog, View view);
    }
}
package com.ybkj.syzs.deliver.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;


/**
 * Description 更新下载进度弹框
 * Author Ren Xingzhi
 * Created on 2019/1/24.
 * Email 15384030400@163.com
 */
public class MyVersionDialog extends Dialog {

    private ProgressBar progressBar;
    private TextView tvProgress;
    private TextView tvVersion;
    private TextView tvContent;

    private String version;
    private String content;

    private OnDownLoadClickListener onDownLoadClickListener;
    private boolean clickAble = true;
    private boolean cancelAble = true;

    public MyVersionDialog(@NonNull Activity context, boolean cancelAble) {
        super(context);
        this.cancelAble = cancelAble;
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
        setContentView(R.layout.project_base_dialog_version);
        setCanceledOnTouchOutside(cancelAble);
        setCancelable(cancelAble);
        initView();
        setAlertDialogWidth((int) ResourcesUtil.getDimen(R.dimen.base_dimen_315));
    }

    public void setOnDownLoadClickListener(OnDownLoadClickListener onDownLoadClickListener) {
        this.onDownLoadClickListener = onDownLoadClickListener;
    }

    private void initView() {
        progressBar = findViewById(R.id.progress);
        tvVersion = findViewById(R.id.tv_version);
        tvProgress = findViewById(R.id.tv_progress);
        tvContent = findViewById(R.id.tv_content);
        tvVersion.setText("V" + version);
        tvContent.setText(content);

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setClickable(false);
                progressBar.setProgress(0);
                if (onDownLoadClickListener != null) {
                    onDownLoadClickListener.onDownloadClick();
                }
            }
        });
    }

    /**
     * @param width 对话框的宽度
     */
    public void setAlertDialogWidth(int width) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        if (getWindow() != null) {
            lp.copyFrom(getWindow().getAttributes());
            lp.width = width;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
        }
    }

    public void updateProgress(int progress) {
        progressBar.setProgress(progress);
        tvProgress.setText(progress + "/100");
    }

    public void setVersion(String version) {
        this.version = version;
        if (tvVersion != null) {
            tvVersion.setText("V" + version);
        }
    }

    public void setContent(String content) {
        this.content = content;
        if (tvContent != null) {
            tvContent.setText(content);
        }
    }

    public void setButtonClickAble(boolean clickAble) {
        progressBar.setClickable(true);
    }

    public interface OnDownLoadClickListener {
        void onDownloadClick();
    }
}

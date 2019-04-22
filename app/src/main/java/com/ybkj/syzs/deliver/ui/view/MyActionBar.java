package com.ybkj.syzs.deliver.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;


public class MyActionBar extends FrameLayout {
    private final int DEFAULT_TITLE_COLOR = ResourcesUtil.getColor(R.color.auth_color_text_3);
    private final int DEFAULT_TEXT_COLOR = ResourcesUtil.getColor(R.color.auth_color_666);
    private final int DEFAULT_BACKGROUND_COLOR = ResourcesUtil.getColor(R.color.base_white);
    private TextView tvRight;
    private TextView tvLeft;
    private ImageButton btnLeft;
    private ImageButton btnRight;
    private TextView tvTitle;
    private View bottomLine;
    private RelativeLayout layoutContent;
    private Context mContext;
    private RelativeLayout layoutLeft;
    private RelativeLayout layoutRight;


    public MyActionBar(Context context) {
        this(context, null);
    }

    public MyActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyActionBar);
        int titleColor = a.getColor(R.styleable.MyActionBar_titleColor, DEFAULT_TITLE_COLOR);
        int backgroundColor = a.getColor(R.styleable.MyActionBar_actionBarColor, DEFAULT_BACKGROUND_COLOR);
        int leftTextColor = a.getColor(R.styleable.MyActionBar_leftTextColor, DEFAULT_TEXT_COLOR);
        int rightTextColor = a.getColor(R.styleable.MyActionBar_rightTextColor, DEFAULT_TEXT_COLOR);
        String title = a.getString(R.styleable.MyActionBar_title);
        String textLeft = a.getString(R.styleable.MyActionBar_textLeft);
        String textRight = a.getString(R.styleable.MyActionBar_textRight);
        int imageLeft = a.getResourceId(R.styleable.MyActionBar_imageLeft, 0);
        int imageRight = a.getResourceId(R.styleable.MyActionBar_imageRight, 0);
        boolean hideBottomLine = a.getBoolean(R.styleable.MyActionBar_hideBottomLine, false);
        boolean canBack = a.getBoolean(R.styleable.MyActionBar_canBack, true);

        LayoutInflater.from(mContext).inflate(R.layout.project_base_layout_my_action_bar, this);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setTextColor(titleColor);
        tvTitle.setText(title);

        btnLeft = findViewById(R.id.btn_left);
        if (imageLeft != 0) {
            btnLeft.setImageResource(imageLeft);
            btnLeft.setVisibility(VISIBLE);
        }
        if (canBack) {
            btnLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                }
            });
        } else {
            btnLeft.setVisibility(GONE);
        }

        btnRight = findViewById(R.id.btn_right);
        if (imageRight != 0) {
            btnRight.setImageResource(imageRight);
            btnRight.setVisibility(VISIBLE);
        }

        tvRight = findViewById(R.id.tv_right);
        tvRight.setTextColor(rightTextColor);
        tvRight.setText(textRight);

        tvLeft = findViewById(R.id.tv_left);
        tvLeft.setTextColor(leftTextColor);
        tvLeft.setText(textLeft);

        bottomLine = findViewById(R.id.bottom_line);
        bottomLine.setVisibility(hideBottomLine ? GONE : VISIBLE);

        layoutContent = findViewById(R.id.layout_content);
        layoutContent.setBackgroundColor(backgroundColor);

        layoutLeft = findViewById(R.id.layout_left);
        layoutRight = findViewById(R.id.layout_right);
        a.recycle();
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置标题文字颜色
     *
     * @param titleColor 颜色资源id
     */
    public void setTitleColor(int titleColor) {
        tvTitle.setTextColor(titleColor);
    }

    /**
     * 设置右侧文字
     *
     * @param rightText 右侧文字
     */
    public void setRightText(String rightText) {
        tvRight.setText(rightText);
    }

    /**
     * 设置右侧文字颜色
     *
     * @param resColor 颜色资源id
     */
    public void setRightTextColor(int resColor) {
        tvRight.setTextColor(resColor);
    }

    /**
     * 设置左侧文字
     *
     * @param leftText 左侧文字
     */
    public void setLeftText(String leftText) {
        tvLeft.setText(leftText);
    }

    /**
     * 设置左侧文字颜色
     *
     * @param resColor 颜色资源id
     */
    public void setLeftTextColor(int resColor) {
        tvLeft.setTextColor(resColor);
    }


    /**
     * 是否隐藏下方分割线
     *
     * @param hide 隐藏下方分割线
     */
    public void hideBottomLine(boolean hide) {
        bottomLine.setVisibility(hide ? GONE : VISIBLE);
    }

    /**
     * 设置左边按钮点击事件
     *
     * @param clickListener 点击事件监听
     */
    public void setOnLeftClickListener(OnClickListener clickListener) {
        if (clickListener != null) {
            layoutLeft.setOnClickListener(clickListener);
            if (btnLeft.getVisibility() == View.VISIBLE) {
                btnLeft.setOnClickListener(clickListener);
            }
        }
    }

    /**
     * 设置右边按钮点击事件
     *
     * @param clickListener 点击事件监听
     */
    public void setOnRightClickListener(OnClickListener clickListener) {
        if (clickListener != null) {
            layoutRight.setOnClickListener(clickListener);
            if (btnRight.getVisibility() == View.VISIBLE) {
                btnRight.setOnClickListener(clickListener);
            }
        }
    }

    public void disableBack() {
        btnLeft.setVisibility(GONE);
    }

    public void enableBack() {
        btnLeft.setVisibility(VISIBLE);
        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });
    }
}

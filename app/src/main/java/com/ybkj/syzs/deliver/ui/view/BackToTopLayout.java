package com.ybkj.syzs.deliver.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.ui.view.refreshlayout.XRefreshLayout;


/**
 * BackToTopLayout
 * <p> 返回到顶部装饰Layout，用于RecyclerView
 * <p>
 * 默认支持一个RecyclerView和XRefreshLayout + RecyclerView的两种情况，其它情况，可以通过设置yb_recyclerViewId
 * 来设置指定的RecyclerView
 * <p>
 * Created by davee 2019/4/17.
 * Copyright (c) 2019 davee. All rights reserved.
 */
public class BackToTopLayout extends FrameLayout implements View.OnClickListener {

    private ImageButton mFloatingActionButton;

    private int mSpecifiedId = 0;
    private RecyclerView mRecyclerView;

    private int mRecyclerViewHeight;

    /**
     * "返回顶部"按钮显示的临界位置 = mRecyclerViewHeight * mShowingFactor
     */
    private float mShowingFactor = 1.5f;

    public BackToTopLayout(@NonNull Context context) {
        this(context, null);
    }

    public BackToTopLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackToTopLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupViews(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BackToTopLayout);
        if (a != null) {
            if (a.hasValue(R.styleable.BackToTopLayout_yb_recyclerViewId)) {
                mSpecifiedId = a.getResourceId(R.styleable.BackToTopLayout_yb_recyclerViewId, mSpecifiedId);
            }

            if (a.hasValue(R.styleable.BackToTopLayout_yb_backToTopDrawable)) {
                final Drawable drawable = a.getDrawable(R.styleable.BackToTopLayout_yb_backToTopDrawable);
                if (drawable != null) {
                    mFloatingActionButton.setImageDrawable(drawable);
                }
            }

            if (a.hasValue(R.styleable.BackToTopLayout_yb_backToTopMarginEnd)) {
                final int margin = a.getDimensionPixelOffset(R.styleable.BackToTopLayout_yb_backToTopMarginEnd, 0);
                if (margin > 0) {
                    setBackToTopEndMargin(margin);
                }
            }

            if (a.hasValue(R.styleable.BackToTopLayout_yb_backToTopMarginBottom)) {
                final int margin = a.getDimensionPixelOffset(R.styleable.BackToTopLayout_yb_backToTopMarginBottom, 0);
                if (margin > 0) {
                    setBackToTopBottomMargin(margin);
                }
            }

            if (a.hasValue(R.styleable.BackToTopLayout_yb_backToTopFactor)) {
                final float factor = a.getFloat(R.styleable.BackToTopLayout_yb_backToTopFactor, 0);
                if (factor > 1) {
                    mShowingFactor = factor;
                }
            }

            a.recycle();
        }
    }


    private void setupViews(@NonNull Context context) {
        inflate(context, R.layout.project_base_layout_back_to_top, this);
        mFloatingActionButton = findViewById(R.id.project_base_back_to_top_btn);
        mFloatingActionButton.setVisibility(GONE);
        mFloatingActionButton.setOnClickListener(this);
    }

    private void findRecyclerView() {
        if (mSpecifiedId != 0) {
            mRecyclerView = findViewById(mSpecifiedId);
        } else {
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View child = getChildAt(i);
                if (child instanceof RecyclerView) {
                    mRecyclerView = (RecyclerView) child;
                    break;
                } else if (child instanceof XRefreshLayout) {
                    final XRefreshLayout refreshLayout = (XRefreshLayout) child;
                    if (refreshLayout.getChildCount() > 0) {
                        mRecyclerView = (RecyclerView) refreshLayout.getChildAt(0);
                    }
                }
            }
        }
        if (mRecyclerView != null) {
            applyRecyclerView();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.project_base_back_to_top_btn) {
            if (mRecyclerView != null) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        }
    }

    public void setBackToTopDrawable(Drawable drawable) {
        if (mFloatingActionButton != null) {
            mFloatingActionButton.setImageDrawable(drawable);
        }
    }

    public void setBackToTopEndMargin(int marginInPx) {
        if (mFloatingActionButton != null && mFloatingActionButton.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams params = (MarginLayoutParams) mFloatingActionButton.getLayoutParams();
            params.setMarginEnd(marginInPx);
            mFloatingActionButton.setLayoutParams(params);
        }
    }

    public void setBackToTopBottomMargin(int marginInPx) {
        if (mFloatingActionButton != null && mFloatingActionButton.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams params = (MarginLayoutParams) mFloatingActionButton.getLayoutParams();
            params.bottomMargin = marginInPx;
            mFloatingActionButton.setLayoutParams(params);
        }
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.applyRecyclerView();
    }

    private void applyRecyclerView() {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int mScrolledOffset = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrolledOffset += dy;

                if (mRecyclerViewHeight > 0 && mScrolledOffset > mRecyclerViewHeight * 1.5) {
                    mFloatingActionButton.setVisibility(VISIBLE);
                } else {
                    mFloatingActionButton.setVisibility(GONE);
                }
            }
        });
    }

    public void preventRecyclerView() {
        mRecyclerView = null;
        mRecyclerViewHeight = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mRecyclerView == null) {
            findRecyclerView();
        }
        if (mRecyclerView != null) {
            mFloatingActionButton.bringToFront();
            mRecyclerViewHeight = mRecyclerView.getMeasuredHeight();
        }
    }
}

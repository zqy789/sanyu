package com.ybkj.syzs.deliver.web;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;
import com.ybkj.syzs.deliver.web.presenter.WebViewPresenter;
import com.ybkj.syzs.deliver.web.view.IWebView;

import butterknife.BindView;
import butterknife.OnClick;

public class BaseWebviewActivity extends BaseMvpActivity<WebViewPresenter> implements IWebView {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_webview;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("title");
        if (title == null || title.equals("")) {
            tvTitle.setText("");
        } else {
            tvTitle.setText(title);
        }
        String url = getIntent().getStringExtra("url");
        if (url != null && (url.equals("") == false)) {
            loadUrl(url);
        }else{
            Toast.makeText(this,"Url为空",Toast.LENGTH_SHORT).show();
        }
        Boolean hasHead = getIntent().getBooleanExtra("hasHead",true);
        if(!hasHead){
            layoutHead.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public boolean isImmersiveStatusBar() {
        return true;
    }

    @Override
    public void loadOrderData(OrderDetailRes response) {

    }

    private void loadUrl(String url) {
        Log.e("webUrl--->", url);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setLoadsImagesAutomatically(true);

        webview.addJavascriptInterface(new WebMethods(this, webview), "android");
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY); // 隐藏滚动条
        webview.requestFocus();//触摸焦点起作用，比如输入框
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.loadUrl(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1006 && resultCode == 1007) {
            webview.reload();
        }
    }
}

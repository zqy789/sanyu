package com.ybkj.syzs.deliver.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.manager.UserDataManager;
import com.ybkj.syzs.deliver.module.MainActivity;
import com.ybkj.syzs.deliver.module.auth.activity.LoginActivity;
import com.ybkj.syzs.deliver.module.order.activity.GoodsPostActivity;
import com.ybkj.syzs.deliver.module.order.activity.OrderDetailActivity;
import com.ybkj.syzs.deliver.utils.LogUtil;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import java.util.prefs.Preferences;

public class WebMethods {
    private Activity mActivity;
    
    private Fragment mFragment;
    
    private WebView mWebView;
    
    public WebMethods(Activity context, WebView webView) {
        this.mActivity = context;
        this.mWebView = webView;
    }
    
    public WebMethods(Fragment fragment, WebView webView) {
        this.mActivity = fragment.getActivity();
        this.mWebView = webView;
        this.mFragment = fragment;
    }
    
    // 关闭当前页
    @JavascriptInterface
    public void goback() {
        if (mActivity != null) {
            ((Activity) mActivity).finish();
        }
    }
    
    /**
     * 弹出toast
     * @param str
     */
    @JavascriptInterface
    public void toast(String str) {
        Toast.makeText(mActivity,str,Toast.LENGTH_SHORT).show();
    }
    
    @JavascriptInterface
    // 打开登录页面
    public void openLogin() {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        //        int x = Integer.parseInt(type);
        //        intent.putExtra("type", x);
//        mActivity.startActivityForResult(intent, Constant.code_user_login_req);
        mActivity.startActivity(intent);
    }
    
    @JavascriptInterface
    // 弹出提示
    public void showToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }
    
    @JavascriptInterface
    // 判断是否登录
    public boolean isLogin() {
        String token = UserDataManager.getToken();
        if (!TextUtils.isEmpty(token)) {
            return true;
        }
        return false;
    }

    @JavascriptInterface
    // 获取Token
    public String getToken() {
        return UserDataManager.getToken();
    }
    @JavascriptInterface
    // 获取appChannel
    public String getAppChannel() {
        return 4+"";
    }

    @JavascriptInterface
    // 打开订单详情
    public void openOrderInfo(String orderNumber) {
        Intent intent = new Intent(mActivity, OrderDetailActivity.class);
        intent.putExtra("orderNo", orderNumber);
        ActivityManager.gotoActivity(mActivity, intent);
    }
    @JavascriptInterface
    // 打开发货页面
    public void openOrderPost(String orderNumber) {
//        Intent intent = new Intent(mActivity, GoodsPostActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("order", searchAdapter.getItem(position));
//        intent.putExtras(bundle);
//        ActivityManager.gotoActivity(mActivity, intent);
    }
}

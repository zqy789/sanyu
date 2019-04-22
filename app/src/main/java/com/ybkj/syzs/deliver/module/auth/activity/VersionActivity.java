package com.ybkj.syzs.deliver.module.auth.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.VersionRes;
import com.ybkj.syzs.deliver.module.auth.presenter.CheckVersionPresenter;
import com.ybkj.syzs.deliver.module.auth.view.CheckVersionView;
import com.ybkj.syzs.deliver.service.ApkDownInstallService;
import com.ybkj.syzs.deliver.utils.AppUpdateVersionCheckUtil;
import com.ybkj.syzs.deliver.utils.LogUtil;
import com.ybkj.syzs.deliver.utils.RxPermissionUtils;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 版本更新
 */

public class VersionActivity extends BaseMvpActivity<CheckVersionPresenter> implements CheckVersionView {


    @BindView(R.id.auth_version)
    TextView authVersion;
    @BindView(R.id.auth_old_version)
    TextView authOldVersion;
    @BindView(R.id.auth_update_content)
    TextView authUpdateContent;
    @BindView(R.id.auth_check)
    Button authCheck;
    @BindView(R.id.layout_new)
    LinearLayout layoutNew;

    private boolean canUpdate;

    private VersionRes.AppVersionBean versionBean;

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_version;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        authOldVersion.setText("当前版本：V" + AppUpdateVersionCheckUtil.getVersion());

    }

    @Override
    public void checkVersionSuccess(VersionRes versionRes) {
        versionBean = versionRes.getAppVersion();
        if (versionBean != null) {
            try {
                canUpdate = AppUpdateVersionCheckUtil.compareVersion(versionBean.getVersionNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!canUpdate) {
                authVersion.setText("当前已是最新版本");
                return;
            }
            //authVersion = res.getVersionNumber();
            authVersion.setText("最新版本：V" + versionBean.getVersionNumber());
            authUpdateContent.setVisibility(View.VISIBLE);
            authUpdateContent.setText(versionBean.getUpdateExplain());
            authCheck.setText("下载更新");
            layoutNew.setVisibility(View.VISIBLE);
        } else {
            toast("当前已是最新版本");
            layoutNew.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.auth_check)
    public void onViewClicked() {

        if (!canUpdate) {//检查更新
            presenter.getVersionInfo(true);
        } else {
            if (versionBean != null) {
                downLoadAPK(versionBean);
            }
            //update(presenter.getVersionData());
        }

    }

    private void downLoadAPK(VersionRes.AppVersionBean versionBean) {
        int storagePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            RxPermissionUtils.getInstance(mContext).setPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE).
                    setOnPermissionCallBack(new RxPermissionUtils.OnPermissionListener() {

                        @Override
                        public void onPermissionGranted(String name) {
                            LogUtil.i("onPermissionGranted name=" + name);
                        }

                        @Override
                        protected void onPermissionException(Throwable e) {
                            super.onPermissionException(e);
                            ToastUtil.showShort("权限申请失败" + e.getMessage());
                        }

                        @Override
                        protected void onAllPermissionFinish() {
                            Intent intent = new Intent(mContext, ApkDownInstallService.class);
                            intent.putExtra("apkUrl", versionBean.getAppUrl());
                            startService(intent);
                            toast("应用正在已转入后台下载，请稍后");
                        }
                    }).start();
        } else {
            Intent intent = new Intent(mContext, ApkDownInstallService.class);
            intent.putExtra("apkUrl", versionBean.getAppUrl());
            startService(intent);
            toast("应用正在已转入后台下载，请稍后");
        }

    }

}

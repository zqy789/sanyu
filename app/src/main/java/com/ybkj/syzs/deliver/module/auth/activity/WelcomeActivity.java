package com.ybkj.syzs.deliver.module.auth.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.ProgressMessage;
import com.ybkj.syzs.deliver.bean.response.LoginRes;
import com.ybkj.syzs.deliver.bean.response.VersionRes;
import com.ybkj.syzs.deliver.manager.ActivityManager;
import com.ybkj.syzs.deliver.manager.UserDataManager;
import com.ybkj.syzs.deliver.module.MainActivity;
import com.ybkj.syzs.deliver.module.auth.presenter.CheckVersionPresenter;
import com.ybkj.syzs.deliver.module.auth.view.CheckVersionView;
import com.ybkj.syzs.deliver.service.ApkDownInstallService;
import com.ybkj.syzs.deliver.ui.dialog.MyVersionDialog;
import com.ybkj.syzs.deliver.ui.dialog.TipDialog;
import com.ybkj.syzs.deliver.utils.AppUpdateVersionCheckUtil;
import com.ybkj.syzs.deliver.utils.LogUtil;
import com.ybkj.syzs.deliver.utils.RxPermissionUtils;
import com.ybkj.syzs.deliver.utils.StringUtil;
import com.ybkj.syzs.deliver.utils.SystemUtil;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * author : ywh
 * date : 2019/4/1 16:18
 * description :启动页
 */
public class WelcomeActivity extends BaseMvpActivity<CheckVersionPresenter> implements CheckVersionView {
    private Disposable subscribe;
    private boolean timeFinish = false;
    private boolean permissionFinish = false;
    private boolean isUpdate = false;

    private boolean isLogin = false;

    private boolean finalCanForceUpdate;

    private String newestVersion;
    private MyVersionDialog updateDialog;//是否更新询问弹出框

    @Override
    protected void injectPresenter() {
        getActivityComponent().inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.auth_activity_splash;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }


    @Override
    protected void initData() {
        setTimeCountDown(3);
        // checkPermission();
        presenter.getVersionInfo(false);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        timeFinish = true;
        // goMianActivity();
    }

    /**
     * 检测必备权限
     */
    @SuppressLint("CheckResult")
    private void checkPermission() {
        new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                            if (aBoolean) {
                                //true表示获取权限成功（android6.0以下默认为true）
                                permissionFinish = true;
                                goMianActivity();
                            } else {
                                TipDialog tipDialog = new TipDialog(mContext);
                                tipDialog.setTitleText("权限授予");
                                tipDialog.setContentText("为保障APP的正常运行，需要进行权限授予");
                                tipDialog.setConfirmButtonText("授予权限");
                                tipDialog.setCancelButtonText("退出APP");
                                tipDialog.setOnCancelButtonClickListener((dialog, view) -> {
                                    ActivityManager.exit();
                                });
                                tipDialog.setOnConfirmButtonClickListener((dialog, view) -> {
                                    dialog.dismiss();
                                    checkPermission();
                                });
                                tipDialog.show();
                            }
                        },
                        Throwable::printStackTrace
                );
    }


    /**
     * 跳转主页面
     * 根据token判断是否需要用户登录
     */
    private void goMianActivity() {
        LoginRes loginRes = UserDataManager.getLoginInfo();
        if (loginRes != null) {
            ActivityManager.gotoActivity(mContext, MainActivity.class);
            finish();
        } else {//角色信息和登录信息都没有，则跳转至登录页
            ActivityManager.gotoActivity(mContext, LoginActivity.class);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxPermissionUtils.destory();
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
            subscribe = null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initTitle() {

    }


    private void setTimeCountDown(final int splashTotalCountdownTime) {
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers
                .mainThread()).map(increaseTime -> splashTotalCountdownTime
                - increaseTime.intValue()).take(splashTotalCountdownTime + 1).subscribe(integer -> {

            if (integer == 0 && isLogin == false && !isUpdate) {
                timeFinish = true;
                goMianActivity();
            }
        });
    }


    @Override
    public void checkVersionSuccess(VersionRes versionRes) {
        VersionRes.AppVersionBean appUpdateRes = versionRes.getAppVersion();
        if (appUpdateRes == null) {
            isUpdate = false;
            goMianActivity();
            return;
        }
        String oldVersion = SystemUtil.getAppVersionName(mContext);
        newestVersion = appUpdateRes.getVersionNumber();
        //最新强制更新版本号
        String forceUpdateVersion = appUpdateRes.getForceUpdatingVersionNumber();
        boolean canUpdate = false;
        boolean canForceUpdate = false;//是否低于最新的强制更新版本号
        try {
            canUpdate = AppUpdateVersionCheckUtil.compareVersion(newestVersion);
            if (StringUtil.isNotNull(forceUpdateVersion)) {
                canForceUpdate = AppUpdateVersionCheckUtil.compareVersion(forceUpdateVersion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!canUpdate) {
            timeFinish = true;
            goMianActivity();
            return;
        }
        isUpdate = true;
        if (appUpdateRes.getType() == 2 || canForceUpdate) {// 1：可用更新，2：强制更新
            updateDialog = new MyVersionDialog(mContext, false);
        } else {
            updateDialog = new MyVersionDialog(mContext, true);
        }
        updateDialog.setContent(appUpdateRes.getUpdateExplain());
        updateDialog.setVersion(appUpdateRes.getVersionNumber());
        finalCanForceUpdate = canForceUpdate;
        updateDialog.setOnDownLoadClickListener(new MyVersionDialog.OnDownLoadClickListener() {
            @Override
            public void onDownloadClick() {
                RxPermissionUtils.getInstance(mContext).setPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE).
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
                                //intent.putExtra("intent_md5", appUpdateRes.getUpdateKey());
                                intent.putExtra("apkUrl", appUpdateRes.getAppUrl());
                                startService(intent);
                            }
                        }).start();
            }

        });
        updateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                updateDialog.dismiss();
                timeFinish = true;
                isUpdate = false;
                goMianActivity();
            }
        });
        updateDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMsg(ProgressMessage event) {
        String tag = event.getTag();
        String message = event.getMsg();
        int progress = event.getProgress();
        switch (message) {
            case ProgressMessage.SHOW:

                break;
            case ProgressMessage.UPDATE:
                if (updateDialog != null) {
                    updateDialog.updateProgress(progress);
                }
                break;
            case ProgressMessage.COMPLETE:
                if (updateDialog != null) {
                    updateDialog.setButtonClickAble(true);
                    updateDialog.dismiss();
                }
                break;
            case ProgressMessage.FAILED:
                ToastUtil.showShort("下载失败");
                if (!finalCanForceUpdate) {
                    updateDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!finalCanForceUpdate) {
            updateDialog.dismiss();
        }
    }
}

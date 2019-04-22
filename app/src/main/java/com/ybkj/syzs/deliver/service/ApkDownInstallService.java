package com.ybkj.syzs.deliver.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.bean.ProgressMessage;
import com.ybkj.syzs.deliver.utils.FileUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * APP下载安装服务
 */
public class ApkDownInstallService extends IntentService {

    private static final int BUFFER_SIZE = 10 * 1024; //缓存大小
    private static final String TAG = "ApkDownInstallService";

    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotifyManager;
    private Notification.Builder mBuilder;

    private String eventTag = "";

    public ApkDownInstallService() {
        super("ApkDownInstallService");
    }

    /**
     * 在onHandleIntent中下载apk文件
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        //初始化通知，用于显示下载进度
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(this);
        String appName = getString(getApplicationInfo().labelRes);
        mBuilder.setContentTitle(appName).setSmallIcon(R.mipmap.ic_launcher_app);

        eventTag = intent.getStringExtra("tag");
        EventBus.getDefault().post(new ProgressMessage(eventTag, ProgressMessage.SHOW, 0));
        String urlStr = intent.getStringExtra("apkUrl"); //从intent中取得apk下载路径
        Log.i("url", urlStr);
        InputStream in = null;
        FileOutputStream out = null;
        try {
            //建立下载连接
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(false);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.connect();

            //以文件流读取数据
            long byteTotal = urlConnection.getContentLength(); //取得文件长度
            long byteSum = 0;
            int byteRead = 0;
            in = urlConnection.getInputStream();
            File dir = FileUtil.getCacheDirectory(this); //取得应用缓存目录
            String apkName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());//取得apK文件名
            File apkFile = new File(dir, apkName);
            out = new FileOutputStream(apkFile);
            byte[] buffer = new byte[BUFFER_SIZE];

            int limit = 0;
            int oldProgress = 0;
            while ((byteRead = in.read(buffer)) != -1) {
                byteSum += byteRead;
                out.write(buffer, 0, byteRead);
                int progress = (int) (byteSum * 100L / byteTotal);
                // 如果进度与之前进度相等，则不更新，如果更新太频繁，则会造成界面卡顿
                if (progress != oldProgress) {
                    updateProgress(progress);
                }
                oldProgress = progress;
            }

            // 下载完成,调用installAPK开始安装文件
            Log.e(TAG, "download apk success");
            out.flush();
            urlConnection.disconnect();
            EventBus.getDefault().post(new ProgressMessage(eventTag, ProgressMessage.COMPLETE, 100));
            installAPk(apkFile);
            mNotifyManager.cancel(NOTIFICATION_ID);

        } catch (Exception e) {
            Log.e(TAG, "download apk file error");
            EventBus.getDefault().post(new ProgressMessage(eventTag, ProgressMessage.FAILED, 0));
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {

                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    /**
     * 实时更新下载进度条显示
     *
     * @param progress
     */
    private void updateProgress(int progress) {
        //"正在下载:" + progress + "%"
        mBuilder.setContentText("正在下载:" + progress + "%").setProgress(100, progress, false);
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingintent);
        mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());

        EventBus.getDefault().post(new ProgressMessage(eventTag, ProgressMessage.UPDATE, progress));
    }


    /**
     * 调用系统安装程序安装下载好的apk
     *
     * @param apkFile
     */
    private void installAPk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
        try {
            String[] command = {"chmod", "777", apkFile.toString()}; //777代表权限 rwxrwxrwx
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException ignored) {
        }
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(this, "com.ybkj.syzs.deliver" + ".fileProvider", apkFile);//通过FileProvider创建一个content类型的Uri
        } else {
            fileUri = Uri.fromFile(apkFile);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
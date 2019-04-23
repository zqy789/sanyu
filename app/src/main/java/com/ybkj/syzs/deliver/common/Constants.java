package com.ybkj.syzs.deliver.common;

import com.ybkj.syzs.deliver.BuildConfig;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  参数配置类
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */

public class Constants {
    public static final String BUGLY_APP_ID = "12b41ff733";
    public static final String TOKEN_KEY = "SYSM-Deliver-Access-Token";
    public static final String PROJECT = "demo";
    public static final String LOGIN_NAME_KEY = "youbu_login_account";

    public static final String PORT = "app/";
    public static final String DEFAULT_BASE_URL = BuildConfig.SERVER_ADDRESS;



    //intent请求公共key
    public static final String INTENT_PARAMETER_1 = "intent_parameter_1";
    public static final String INTENT_PARAMETER_2 = "intent_parameter_2";
    public static final String INTENT_PARAMETER_3 = "intent_parameter_3";
    public static final String INTENT_PARAMETER_4 = "intent_parameter_4";

    //连接超时时间
    public static final int connectionTime = 15;
    //默认每页加载条目数
    public static final int PAGE_NUM = 20;
    public static final String ALIBABA_APP_KEY = "256";
    public static final String ALIBABA_APP_SECRET = "ad5";
    public static final String ALIBABA_APP_RSASECRET = "KTY";
    //请求标记码
    public static final int REQUEST_CODE_1 = 101;
    public static final int REQUEST_CODE_2 = 102;
    public static final int REQUEST_CODE_3 = 103;
    public static final int REQUEST_CODE_4 = 104;
    //标记通用
    public static final int TAG_NORMAL_1 = 111;
    public static final int TAG_NORMAL_2 = 112;
    public static final int TAG_NORMAL_3 = 113;
    public static final String APPEALID = "appealId";
    public static int APK_VERSION_UPDATE_FORCE = 2; // 更新更新类型，1：可用更新，2：强制更新


}

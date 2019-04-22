package com.ybkj.syzs.deliver.utils;


import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * MD5操作
 *
 * @author gql
 */
public class MD5Util {
    private static final String TAG = "MD5Util";

    /**
     * 将原数据字符串逆时针左移3位，再将移动后的字符串进行MD5加密。
     *
     * @param paramStr 原字符串，长度必须大于等于3位。
     * @return 加密后的字符串，原字符串长度小于3位返回null。
     */
    public static String toMd5(String paramStr) {
        //原字符串为空或者字符长度小宇3位，返回null
        if (paramStr.isEmpty() || paramStr.length() < 3) {
            return null;
        }

        //逆时针左移3位
        String preStr = paramStr.substring(0, 3);
        String endStr = paramStr.substring(3);
        String newStr = endStr + preStr;

        //MD5加密
        String md5Str = MD5(newStr);

        LogUtil.i(md5Str);
        return md5Str;
    }

    /**
     * 执行MD5加密
     *
     * @param str 原字符串
     * @return 加密后的字符串
     */
    private static String MD5(String str) {
        try {
            byte[] btInput = str.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得散列后的值
            byte[] md = mdInst.digest();

            //抛掉前2位和后3位
            String md5Str1 = getMD5Str(md);
            md5Str1 = md5Str1.substring(2, 29);

            //第二次散列
            byte[] btInput2 = md5Str1.getBytes();
            MessageDigest mdInst2 = MessageDigest.getInstance("MD5");
            mdInst2.update(btInput2);
            byte[] md2 = mdInst2.digest();

            String md5Str2 = getMD5Str(md2);

            return md5Str2;
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * 将MD5散列后的值转化成字符串
     *
     * @param paramByte MD5散列后的数组
     * @return 转化后的字符串
     */
    private static String getMD5Str(byte[] paramByte) {
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < paramByte.length; i++) {
            if (Integer.toHexString(0xFF & paramByte[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & paramByte[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & paramByte[i]));
            }

        }
        return md5StrBuff.toString();
    }

    /**
     * 简单的md5散列
     *
     * @param paramStr 原字符串
     * @return 转化后的字符串
     */
    public static String md5Str(String paramStr) {
        String resultStr = "";

        try {
            byte[] btInput = paramStr.getBytes("UTF-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得散列后的值
            byte[] md = mdInst.digest();

            resultStr = getMD5Str(md);
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }

        return resultStr;
    }


    /**
     * 获取文件信息MD5值
     *
     * @param apkFile
     * @return
     */
    public static String getSignMd5Str(File apkFile) {
        try {
            // 获取一个文件的特征信息，签名信息。
            File file = new File(apkFile.getPath());
            // md5
            MessageDigest digest = MessageDigest.getInstance("md5");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            byte[] result = digest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                // System.out.println(str);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
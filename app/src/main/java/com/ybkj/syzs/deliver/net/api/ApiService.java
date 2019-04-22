package com.ybkj.syzs.deliver.net.api;

import com.ybkj.syzs.deliver.bean.BaseResponse;
import com.ybkj.syzs.deliver.bean.request.CheckCodeUseReq;
import com.ybkj.syzs.deliver.bean.request.GoodPostReq;
import com.ybkj.syzs.deliver.bean.request.LoginReq;
import com.ybkj.syzs.deliver.bean.request.ModifyPassReq;
import com.ybkj.syzs.deliver.bean.request.ModifyReq;
import com.ybkj.syzs.deliver.bean.request.OrderListReq;
import com.ybkj.syzs.deliver.bean.request.PhoneNumberReq;
import com.ybkj.syzs.deliver.bean.request.ResetPasswordReq;
import com.ybkj.syzs.deliver.bean.request.VerfyPhoneCodeReq;
import com.ybkj.syzs.deliver.bean.response.LoginRes;
import com.ybkj.syzs.deliver.bean.response.MineDataRes;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.bean.response.VersionRes;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  ApiService
 * - @Time:  2018/8/31
 * - @Emaill:  380948730@qq.com
 */
public interface ApiService {

    /**
     * 用户登录
     *
     * @param req 请求实体类
     * @return
     */
    @POST("/deliver/app/login")
    Observable<BaseResponse<LoginRes>> startLogin(@Body LoginReq req);

    /**
     * 退出登录登录
     *
     * @return
     */
    @POST("/deliver/app/logout")
    Observable<BaseResponse<LoginRes>> logout();

    /**
     * 找回密码
     *
     * @return
     */
    @POST("/deliver/app/forget")
    Observable<BaseResponse<Object>> forgetPass(@Body ResetPasswordReq req);

    /**
     * 修改默认密码
     *
     * @returna
     */
    @POST("userApp/resetPassword//updatePwd")
    Observable<BaseResponse<Object>> modifyNormalPwd(@Body ModifyPassReq modifyPassReq);

    /**
     * 修改密码
     *
     * @return
     */
    @POST("/deliver/personal/updatePassword")
    Observable<BaseResponse<MineDataRes>> modifyPsd(@Body ModifyReq req);

    /**
     * 获取验证码
     */
    @POST("sendCode/sendPhoneCode")
    Observable<BaseResponse<Object>> getVerificationCode(@Body PhoneNumberReq req);

    /**
     * 修改手机号码
     *
     * @param req
     * @return
     */
    @POST("/deliver/personal/updatePhone")
    Observable<BaseResponse<Object>> modifyPhone(@Body ModifyReq req);


    /**
     * 版本更新
     *
     * @return
     */
    @GET("/deliver/app/versions")
    Observable<BaseResponse<VersionRes>> getVersion();

    /**
     * 修改账号
     *
     * @param
     * @return
     */
    @POST("userApp/myInfo/modifyUserAccount")
    Observable<BaseResponse<Object>> modifyAccount(@Body ModifyReq req);

    /**
     * 验证手机号码
     *
     * @return
     */
    @POST("/deliver/personal/validatePhone")
    Observable<BaseResponse<Object>> verifyPhone(@Body VerfyPhoneCodeReq req);


    /**
     * 获取订单列表
     *
     * @param
     * @return
     */
    @POST("/deliver/order/list")
    Observable<BaseResponse<OrderListRes>> getOrderList(@Body OrderListReq req);


    /**
     * 验证公码可用性
     *
     * @param
     * @return
     */
    @POST("/deliver/order/checkCodeUse")
    Observable<BaseResponse<Object>> checkCodeUseAble(@Body CheckCodeUseReq req);

    /**
     * 商品发货
     *
     * @param
     * @return
     */
    @POST("/deliver/order/inputPublicCode")
    Observable<BaseResponse<Object>> postGoods(@Body GoodPostReq req);

    /**
     * 获取订单详情
     */
    @GET("/deliver/order/detail/{orderNo}")
    Observable<BaseResponse<OrderDetailRes>> getOrderDetail(@Path("orderNo") String orderNo);

}
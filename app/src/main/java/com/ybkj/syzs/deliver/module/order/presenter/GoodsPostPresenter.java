package com.ybkj.syzs.deliver.module.order.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.ybkj.syzs.deliver.base.BaseRxPresenter;
import com.ybkj.syzs.deliver.bean.request.GoodPostReq;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.common.Constants;
import com.ybkj.syzs.deliver.module.order.view.IGoodsPostView;
import com.ybkj.syzs.deliver.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class GoodsPostPresenter extends BaseRxPresenter<IGoodsPostView> {

    @Inject
    public GoodsPostPresenter(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Object response, int tag) {
        mView.postSuccess();
    }

    public void postOrder(String expressName, String expressNo, String orderNo, List<OrderListRes.ListBean.GoodsBean> goods) {

        if (TextUtils.isEmpty(expressNo)) {
            ToastUtil.showShort("请录入快递单号");
            return;
        }
        if (TextUtils.isEmpty(expressName)) {
            ToastUtil.showShort("请输入快递名称");
            return;
        }

        GoodPostReq req = new GoodPostReq();
        req.setExpressName(expressName);
        req.setExpressOrderNumber(expressNo);
        req.setOrderNo(orderNo);

        List<GoodPostReq.ListBean> goodsList = new ArrayList<>();
        for (OrderListRes.ListBean.GoodsBean good : goods) {
//            if (TextUtils.isEmpty(good.getGoodsPublicCode())) {
//                ToastUtil.showShort("还有商品未录入防伪码，请先将所有商品录入防伪码再进行发货");
//                return;
//            }
            if (!TextUtils.isEmpty(good.getGoodsPublicCode())) {
                GoodPostReq.ListBean postGood = new GoodPostReq.ListBean();
                postGood.setOrderGoodsNo(good.getOrderGoodsNo());
                postGood.setGoodsPublicCode(good.getGoodsPublicCode());
                goodsList.add(postGood);
            }
        }
        if(goodsList.size() == 0){
            ToastUtil.showShort("还未录入防伪码，请先录入防伪码再进行发货");
            return;
        }
        req.setList(goodsList);
        sendHttpRequest(apiService.postGoods(req), Constants.REQUEST_CODE_1);
    }
}

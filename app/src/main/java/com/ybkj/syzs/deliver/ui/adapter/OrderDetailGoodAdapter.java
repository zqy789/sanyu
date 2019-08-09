package com.ybkj.syzs.deliver.ui.adapter;

import android.content.Context;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.bean.response.OrderDetailRes;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseAdapter;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseViewHolder;
import com.ybkj.syzs.deliver.ui.view.RoundImageView;
import com.ybkj.syzs.deliver.utils.ImageLoadUtils;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class OrderDetailGoodAdapter extends XBaseAdapter<OrderDetailRes.OrderBean.ExpressBoListBean.GoodsBean> {
    public OrderDetailGoodAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {

        return R.layout.recycle_item_order_detail_goods;
    }

    @Override
    protected void convert(XBaseViewHolder helper, OrderDetailRes.OrderBean.ExpressBoListBean.GoodsBean item) {
        helper.setText(R.id.tv_title, item.getGoodsName());
        RoundImageView imageGoods = helper.getView(R.id.image_goods);
        ImageLoadUtils.loadUrlImage(mContext, item.getGoodsImg(), imageGoods);
        helper.setText(R.id.tv_code, "防伪码：" + item.getGoodsPublicCode());
    }
}

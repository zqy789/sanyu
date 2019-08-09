package com.ybkj.syzs.deliver.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.bean.response.OrderListRes;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseAdapter;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseViewHolder;
import com.ybkj.syzs.deliver.ui.view.RoundImageView;
import com.ybkj.syzs.deliver.utils.ImageLoadUtils;
import com.ybkj.syzs.deliver.utils.ResourcesUtil;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/19.
 * Email 15384030400@163.com
 */
public class GoodsPostAdapter extends XBaseAdapter<OrderListRes.ListBean.GoodsBean> {
    public GoodsPostAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.recycle_item_goods_post;
    }

    @Override
    protected void convert(XBaseViewHolder helper, OrderListRes.ListBean.GoodsBean item) {
        helper.setText(R.id.tv_title, item.getGoodsName());
        RoundImageView imageGoods = helper.getView(R.id.image_goods);
        ImageLoadUtils.loadUrlImage(mContext, item.getGoodsImg(), imageGoods);
        Button button = helper.getView(R.id.btn_entry);
        if (!TextUtils.isEmpty(item.getGoodsPublicCode())) {
            helper.setText(R.id.tv_code, "防伪码：" + item.getGoodsPublicCode());
            helper.setImageResource(R.id.img_order_check,R.mipmap.order_select);
            button.setText("重新录入防伪码");
            button.setBackgroundResource(R.drawable.shape_grey9_round_stroke);
            button.setTextColor(ResourcesUtil.getColor(R.color.auth_color_999));
        } else {
            helper.setText(R.id.tv_code, "");
            helper.setImageResource(R.id.img_order_check,R.mipmap.order_no_select);
            button.setText("录入防伪码");
            button.setBackgroundResource(R.drawable.shape_green_round_stroke);
            button.setTextColor(ResourcesUtil.getColor(R.color.project_base_color_green));
        }

        helper.addOnClickListener(R.id.btn_entry);
    }
}

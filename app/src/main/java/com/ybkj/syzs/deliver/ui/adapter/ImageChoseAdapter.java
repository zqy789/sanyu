package com.ybkj.syzs.deliver.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.bean.response.Image;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseAdapter;
import com.ybkj.syzs.deliver.ui.adapter.base.XBaseViewHolder;
import com.ybkj.syzs.deliver.utils.ImageLoadUtils;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/23.
 * Email 15384030400@163.com
 */
public class ImageChoseAdapter extends XBaseAdapter<Image> {
    public ImageChoseAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.recycle_item_image_chose;
    }

    @Override
    protected void convert(XBaseViewHolder helper, Image item) {
        ImageView ivImage = helper.getView(R.id.iv_image);
        ImageLoadUtils.loadLocalImage(mContext, item.getPath(), ivImage);
        Point point = new Point();
        WindowManager wm = ((Activity) mContext).getWindowManager();
        wm.getDefaultDisplay().getSize(point);
        int originalWidth = point.x;
        int width = (int) (originalWidth / 4);
        RelativeLayout.LayoutParams lp_pic = new RelativeLayout.LayoutParams(width, width);
        lp_pic.setMargins(1, 1, 1, 1);
        ivImage.setLayoutParams(lp_pic);
    }

}

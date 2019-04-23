package com.ybkj.syzs.deliver.module.user.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.base.BaseMvpActivity;
import com.ybkj.syzs.deliver.bean.response.Folder;
import com.ybkj.syzs.deliver.bean.response.Image;
import com.ybkj.syzs.deliver.ui.adapter.ImageChoseAdapter;
import com.ybkj.syzs.deliver.utils.ImageScanUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Description
 * Author Ren Xingzhi
 * Created on 2019/4/23.
 * Email 15384030400@163.com
 */
public class PictureChoseActivity extends BaseMvpActivity {
    public static final int SELECT_PICTURE_SUCCESS = 121;
    @BindView(R.id.recycler_image)
    RecyclerView recyclerImage;
    ImageChoseAdapter imageChoseAdapter;

    @Override
    protected void injectPresenter() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture_chose;
    }

    @Override
    protected void initView() {
        imageChoseAdapter = new ImageChoseAdapter(mContext);
        recyclerImage.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerImage.setAdapter(imageChoseAdapter);

        imageChoseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Image image = imageChoseAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("path", image.getPath());
                setResult(SELECT_PICTURE_SUCCESS, intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        ImageScanUtil.loadImageForSDCard(this, new ImageScanUtil.DataCallback() {
            @Override
            public void onSuccess(ArrayList<Folder> folders) {
                //folders是图片文件夹的列表，每个文件夹中都有若干张图片。
                imageChoseAdapter.setNewData(folders.get(0).getImages());
            }
        });
    }

}

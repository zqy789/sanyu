package com.ybkj.syzs.deliver.bean.response;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created on 2017/11/9.
 */

public class Folder {
    private String name;
    private ArrayList<Image> images;

    public Folder(String name) {
        this.name = name;
    }

    public Folder(String name, ArrayList<Image> images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        if (image != null && !TextUtils.isEmpty(image.getPath())) {
            if (images == null) {
                images = new ArrayList<>();
            }
            images.add(image);
        }
    }
}

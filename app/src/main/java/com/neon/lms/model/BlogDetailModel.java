package com.neon.lms.model;

import androidx.databinding.BaseObservable;


public class BlogDetailModel extends BaseObservable {

    private String title;
    private String des;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}

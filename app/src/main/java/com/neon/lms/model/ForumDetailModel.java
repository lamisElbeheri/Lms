package com.neon.lms.model;

import android.databinding.BaseObservable;


public class ForumDetailModel extends BaseObservable {

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

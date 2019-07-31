package com.neon.lms.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.neon.lms.BaseAppClass;
import com.neon.lms.util.Constants;
import com.neon.lms.util.Validation;


public class AbouUsModel extends BaseObservable {

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

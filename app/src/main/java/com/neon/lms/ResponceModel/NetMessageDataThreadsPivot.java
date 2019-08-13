package com.neon.lms.ResponceModel;

import android.databinding.BaseObservable;

public class NetMessageDataThreadsPivot extends BaseObservable {
    private String last_read;
    private int thread_id;
    private int user_id;

    public String getLast_read() {
        return this.last_read;
    }

    public void setLast_read(String last_read) {
        this.last_read = last_read;
    }

    public int getThread_id() {
        return this.thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

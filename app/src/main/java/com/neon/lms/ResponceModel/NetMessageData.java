package com.neon.lms.ResponceModel;

import java.util.ArrayList;

public class NetMessageData {
    private ArrayList<NetMessageDataThreads> threads;
    private String thread;
    private String status;

    public ArrayList<NetMessageDataThreads> getThreads() {
        return this.threads;
    }

    public void setThreads(ArrayList<NetMessageDataThreads> threads) {
        this.threads = threads;
    }

    public String getThread() {
        return this.thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

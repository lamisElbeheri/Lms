package com.neon.lms.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.neon.lms.ResponceModel.NetMessageDataThreadsMessages;
import com.neon.lms.ResponceModel.NetMessageDataThreadsMessagesSender;
import com.neon.lms.ResponceModel.NetMessageDataThreadsPivot;

import java.util.ArrayList;


public class MessageModel extends BaseObservable {


    public MessageModel() {
    }
    private NetMessageDataThreadsPivot pivot;
    private ArrayList<NetMessageDataThreadsMessages> messages;
    private String id;

    protected MessageModel(Parcel in) {
        id = in.readString();
    }


    public NetMessageDataThreadsPivot getPivot() {
        return this.pivot;
    }

    public void setPivot(NetMessageDataThreadsPivot pivot) {
        this.pivot = pivot;
    }

    public ArrayList<NetMessageDataThreadsMessages> getMessages() {
        return this.messages;
    }

    public void setMessages(ArrayList<NetMessageDataThreadsMessages> messages) {
        this.messages = messages;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
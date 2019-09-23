package com.neon.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;

import com.neon.lms.ResponceModel.NetMessageDataThreadsMessages;
import com.neon.lms.ResponceModel.NetMessageDataThreadsPivot;

import java.util.ArrayList;


public class MessageModel extends BaseObservable {
    private ArrayList<NetMessageDataThreadsMessages> messages;

    public MessageModel() {

    }

    public ArrayList<NetMessageDataThreadsMessages> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<NetMessageDataThreadsMessages> messages) {
        this.messages = messages;
    }

    private NetMessageDataThreadsPivot pivot;
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


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
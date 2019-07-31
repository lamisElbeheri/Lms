package com.neon.lms.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.neon.lms.ResponceModel.NetMessageDataThreadsMessagesSender;


public class MessageModel extends BaseObservable implements Parcelable {


    public MessageModel() {
    }
    private int thread_id;
    private NetMessageDataThreadsMessagesSender sender;
    private String created_at;
    private int id;
    private String body;
    private int sender_id;

    protected MessageModel(Parcel in) {
        thread_id = in.readInt();
        created_at = in.readString();
        id = in.readInt();
        body = in.readString();
        sender_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(thread_id);
        dest.writeString(created_at);
        dest.writeInt(id);
        dest.writeString(body);
        dest.writeInt(sender_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };

    public int getThread_id() {
        return this.thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public NetMessageDataThreadsMessagesSender getSender() {
        return this.sender;
    }

    public void setSender(NetMessageDataThreadsMessagesSender sender) {
        this.sender = sender;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getSender_id() {
        return this.sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }
}
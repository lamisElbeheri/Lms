package com.neon.lms.ResponceModel;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

public class NetMessageDataThreadsMessages extends BaseObservable implements Parcelable{
    private String thread_id;
    private NetMessageDataThreadsMessagesSender sender;
    private String created_at;
    private String id;
    private String body;
    private String sender_id;

    public NetMessageDataThreadsMessages (){

    }

    protected NetMessageDataThreadsMessages(Parcel in) {
        thread_id = in.readString();
        created_at = in.readString();
        id = in.readString();
        body = in.readString();
        sender_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thread_id);
        dest.writeString(created_at);
        dest.writeString(id);
        dest.writeString(body);
        dest.writeString(sender_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetMessageDataThreadsMessages> CREATOR = new Creator<NetMessageDataThreadsMessages>() {
        @Override
        public NetMessageDataThreadsMessages createFromParcel(Parcel in) {
            return new NetMessageDataThreadsMessages(in);
        }

        @Override
        public NetMessageDataThreadsMessages[] newArray(int size) {
            return new NetMessageDataThreadsMessages[size];
        }
    };

    public String getThread_id() {
        return this.thread_id;
    }

    public void setThread_id(String thread_id) {
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender_id() {
        return this.sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
}

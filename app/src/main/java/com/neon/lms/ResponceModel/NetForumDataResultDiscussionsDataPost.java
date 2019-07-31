package com.neon.lms.ResponceModel;

public class NetForumDataResultDiscussionsDataPost {
    private int chatter_discussion_id;
    private String updated_at;
    private int user_id;
    private int markdown;
    private String created_at;
    private int id;
    private String body;
    private int locked;
    private String deleted_at;

    public int getChatter_discussion_id() {
        return this.chatter_discussion_id;
    }

    public void setChatter_discussion_id(int chatter_discussion_id) {
        this.chatter_discussion_id = chatter_discussion_id;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMarkdown() {
        return this.markdown;
    }

    public void setMarkdown(int markdown) {
        this.markdown = markdown;
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

    public int getLocked() {
        return this.locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}

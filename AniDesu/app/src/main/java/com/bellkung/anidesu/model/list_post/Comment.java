package com.bellkung.anidesu.model.list_post;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Comment {
    private String uid;
    private String text;
    private String comment_date;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
}

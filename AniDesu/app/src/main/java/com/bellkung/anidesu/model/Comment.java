package com.bellkung.anidesu.model;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Comment {
    private String uid;
    private String comment_text;
    private String comment_date;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getComment_date() {
        return this.comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
}

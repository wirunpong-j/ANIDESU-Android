package com.bellkung.anidesu.model;

import com.bellkung.anidesu.model.list_post.Comment;
import com.bellkung.anidesu.model.list_post.Like;
import com.bellkung.anidesu.model.list_post.Unlike;

import java.util.ArrayList;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Posts {
    private String uid;
    private String status;
    private String post_date;

    private ArrayList<Comment> list_comment;
    private ArrayList<Like> list_like;
    private ArrayList<Unlike> list_unlike;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public ArrayList<Comment> getList_comment() {
        return list_comment;
    }

    public void setList_comment(ArrayList<Comment> list_comment) {
        this.list_comment = list_comment;
    }

    public ArrayList<Like> getList_like() {
        return list_like;
    }

    public void setList_like(ArrayList<Like> list_like) {
        this.list_like = list_like;
    }

    public ArrayList<Unlike> getList_unlike() {
        return list_unlike;
    }

    public void setList_unlike(ArrayList<Unlike> list_unlike) {
        this.list_unlike = list_unlike;
    }
}

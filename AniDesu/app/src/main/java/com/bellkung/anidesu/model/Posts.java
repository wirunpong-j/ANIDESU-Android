package com.bellkung.anidesu.model;

import android.util.Log;

import com.bellkung.anidesu.model.list_post.Comment;
import com.bellkung.anidesu.model.list_post.Like;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Posts {

    public interface PostsListener {
        void onPostsSuccess();
        void onPostsFailed(String errorText);
    }

    private String post_key;

    private String uid;
    private String status;
    private String post_date;

    private HashMap<String, Comment> allComment;
    private HashMap<String, Like> allLike;

    private PostsListener listener;

    public void createPostToDB() {

        DatabaseReference mPostRef = FirebaseDatabase.getInstance()
                .getReference("posts");

        String key = mPostRef.push().getKey();

        HashMap<String, Object> postValues = new HashMap<>();
        postValues.put("post_key", key);
        postValues.put("uid", this.uid);
        postValues.put("status", this.status);
        postValues.put("post_date", getCurrentDateTime());

        HashMap<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);

        mPostRef.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    if (listener != null) {
                        listener.onPostsSuccess();
                    }
                } else {
                    listener.onPostsFailed(databaseError.getMessage());
                }
            }
        });
    }

    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Date date = Calendar.getInstance().getTime();

        return dateFormat.format(date);
    }

    public String getPost_key() {
        return post_key;
    }

    public void setPost_key(String post_key) {
        this.post_key = post_key;
    }

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

    public HashMap<String, Comment> getAllComment() {
        return allComment;
    }

    public void setAllComment(HashMap<String, Comment> allComment) {
        this.allComment = allComment;
    }

    public HashMap<String, Like> getAllLike() {
        return allLike;
    }

    public void setAllLike(HashMap<String, Like> allLike) {
        this.allLike = allLike;
    }

    public void setListener(PostsListener listener) {
        this.listener = listener;
    }
}

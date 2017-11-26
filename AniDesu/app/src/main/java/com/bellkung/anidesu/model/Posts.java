package com.bellkung.anidesu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bellkung.anidesu.utils.FormatCustomManager;

import java.util.HashMap;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Posts implements Parcelable {

    private String post_key;
    private String uid;
    private String status;
    private String post_date;
    private int like_count;

    private HashMap<String, Boolean> allLike;

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
        return FormatCustomManager.parseOnFirebaseDateTime(post_date);
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public HashMap<String, Boolean> getAllLike() {
        return allLike;
    }

    public void setAllLike(HashMap<String, Boolean> allLike) {
        this.allLike = allLike;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.post_key);
        dest.writeString(this.uid);
        dest.writeString(this.status);
        dest.writeString(this.post_date);
        dest.writeInt(this.like_count);
        dest.writeSerializable(this.allLike);
    }

    public Posts() {
    }

    protected Posts(Parcel in) {
        this.post_key = in.readString();
        this.uid = in.readString();
        this.status = in.readString();
        this.post_date = in.readString();
        this.like_count = in.readInt();
        this.allLike = (HashMap<String, Boolean>) in.readSerializable();
    }

    public static final Creator<Posts> CREATOR = new Creator<Posts>() {
        @Override
        public Posts createFromParcel(Parcel source) {
            return new Posts(source);
        }

        @Override
        public Posts[] newArray(int size) {
            return new Posts[size];
        }
    };
}

package com.bellkung.anidesu.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Reviews implements Parcelable {

    private String uid;
    private String text;
    private String review_date;
    private String anime_id;
    private float rating;

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

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAnime_id() {
        return anime_id;
    }

    public void setAnime_id(String anime_id) {
        this.anime_id = anime_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.text);
        dest.writeString(this.review_date);
        dest.writeString(this.anime_id);
        dest.writeFloat(this.rating);
    }

    public Reviews() {
    }

    protected Reviews(Parcel in) {
        this.uid = in.readString();
        this.text = in.readString();
        this.review_date = in.readString();
        this.anime_id = in.readString();
        this.rating = in.readFloat();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel source) {
            return new Reviews(source);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}


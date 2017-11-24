package com.bellkung.anidesu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bellkung.anidesu.custom.FormatCustomManager;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Reviews implements Parcelable {

    public interface ReviewListener {
        void OnCreatedReview();
    }

    private ReviewListener reviewListener;

    private String uid;
    private String text;
    private String review_date;
    private float rating;

    public void createReview(String anime_id) {
        DatabaseReference mReviewRef = FirebaseDatabase.getInstance()
                .getReference("reviews");

        HashMap<String, Object> reviewValues = new HashMap<>();
        reviewValues.put("uid", this.uid);
        reviewValues.put("rating", this.rating);
        reviewValues.put("text", this.text);
        reviewValues.put("review_date", this.review_date);

        mReviewRef.child(anime_id)
                .child("/review")
                .push()
                .setValue(reviewValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    if (reviewListener != null) {
                        reviewListener.OnCreatedReview();
                    }
                } else {

                }
            }
        });

    }

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

    public void setReviewListener(ReviewListener reviewListener) {
        this.reviewListener = reviewListener;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.reviewListener, flags);
        dest.writeString(this.uid);
        dest.writeString(this.text);
        dest.writeString(this.review_date);
        dest.writeFloat(this.rating);
    }

    public Reviews() {
    }

    protected Reviews(Parcel in) {
        this.reviewListener = in.readParcelable(ReviewListener.class.getClassLoader());
        this.uid = in.readString();
        this.text = in.readString();
        this.review_date = in.readString();
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

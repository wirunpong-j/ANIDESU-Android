package com.bellkung.anidesu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.api.model.Series;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Reviews implements Parcelable, OnNetworkCallbackListener {

    public interface ReviewListener {
        void onCreatedReview();
    }
    private ReviewListener reviewListener;

    public interface FetchReviewDataListener {
        void onFetchedReviewCompleted(ArrayList<Reviews> allReview,
                                      ArrayList<AnotherUser> allReviewer,
                                      ArrayList<Series> allSeries);
        void onFetchedReviewDataFailed();
    }
    private FetchReviewDataListener fetchReviewListener;

    private String uid;
    private String text;
    private String review_date;
    private String anime_id;
    private float rating;

    private ArrayList<Reviews> allReview;
    private ArrayList<AnotherUser> allReviewer;
    private ArrayList<Series> allSeries;

    public void createReview() {
        DatabaseReference mReviewRef = FirebaseDatabase.getInstance()
                .getReference("series");

        HashMap<String, Object> reviewValues = new HashMap<>();
        reviewValues.put("rating", this.rating);
        reviewValues.put("text", this.text);
        reviewValues.put("review_date", this.review_date);
        reviewValues.put("anime_id", this.anime_id);

        mReviewRef.child(anime_id)
                .child("/review")
                .child(this.uid)
                .setValue(reviewValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    if (reviewListener != null) {
                        reviewListener.onCreatedReview();
                    }
                }
            }
        });

    }

    public void fetchAllReviewData() {
        DatabaseReference mReviewRef = FirebaseDatabase.getInstance().getReference("series");
        mReviewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allReview = new ArrayList<>();

                for (DataSnapshot parent: dataSnapshot.getChildren()) {
                    for (DataSnapshot child: parent.child("review").getChildren()) {
                        Reviews review = child.getValue(Reviews.class);
                        review.setUid(child.getKey());

                        allReview.add(review);
                    }
                }

                fetchReviewerData();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchReviewerData() {

        this.allReviewer = new ArrayList<>();

        for (final Reviews reviews: this.allReview) {
            DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users/" + reviews.getUid());
            mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    AnotherUser reviewer = dataSnapshot.child("/profile").getValue(AnotherUser.class);
                    reviewer.setUid(reviews.getUid());
                    allReviewer.add(reviewer);

                    if (allReview.size() == allReviewer.size()) {
                        fetchAnimeData();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void fetchAnimeData() {

        this.allSeries = new ArrayList<>();

        for (Reviews reviews: this.allReview) {
            new NetworkConnectionManager().fetchThisSeriesData(this, Integer.parseInt(reviews.getAnime_id()), ApiConfig.FETCH_ANIME_REVIEW);
        }
    }

    @Override
    public void onResponse(String action, Call call, Response response) {
        switch (action) {
            case ApiConfig.FETCH_ANIME_REVIEW:
                Series series = (Series) response.body();
                this.allSeries.add(series);

                if (this.allSeries.size() == this.allReview.size()) {
                    if (fetchReviewListener != null) {
                        fetchReviewListener.onFetchedReviewCompleted(this.allReview, this.allReviewer, this.allSeries);
                    }
                }
                break;
        }
    }

    @Override
    public void onBodyError(ResponseBody responseBodyError) {

    }

    @Override
    public void onBodyErrorIsNull() {

    }

    @Override
    public void onFailure(Throwable t) {

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

    public void setFetchReviewListener(FetchReviewDataListener fetchReviewListener) {
        this.fetchReviewListener = fetchReviewListener;
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
        dest.writeParcelable((Parcelable) this.reviewListener, flags);
        dest.writeParcelable((Parcelable) this.fetchReviewListener, flags);
        dest.writeString(this.uid);
        dest.writeString(this.text);
        dest.writeString(this.review_date);
        dest.writeString(this.anime_id);
        dest.writeFloat(this.rating);
    }

    public Reviews() {
    }

    protected Reviews(Parcel in) {
        this.reviewListener = in.readParcelable(ReviewListener.class.getClassLoader());
        this.fetchReviewListener = in.readParcelable(FetchReviewDataListener.class.getClassLoader());
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


package com.bellkung.anidesu.service;

import android.util.Log;

import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Reviews;
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
 * Created by BellKunG on 26/11/2017 AD.
 */

public class ReviewService implements OnNetworkCallbackListener {

    public interface CreateReviewListener {
        void onCreateReviewCompleted();
        void onCreateReviewFailed(String errorText);
    }
    private CreateReviewListener createReviewListener;

    public interface FetchAllReviewDataListener {
        void onFetchAllReviewCompleted(ArrayList<Reviews> allReview,
                                      ArrayList<AnotherUser> allReviewer,
                                      HashMap<String, Series> allSeries);
        void onFetchAllReviewDataFailed(String errorText);
    }
    private FetchAllReviewDataListener fetchAllReviewListener;

    public interface FetchAnimeReviewListener {
        void onFetchAnimeReviewCompleted(ArrayList<Reviews> allReview,
                                       ArrayList<AnotherUser> allReviewer);
        void onFetchAnimeReviewFailed(String errorText);
    }
    private FetchAnimeReviewListener fetchAnimeReviewListener;


    public void setCreateReviewListener(CreateReviewListener createReviewListener) {
        this.createReviewListener = createReviewListener;
    }

    public void setFetchAllReviewListener(FetchAllReviewDataListener fetchAllReviewListener) {
        this.fetchAllReviewListener = fetchAllReviewListener;
    }

    public void setFetchAnimeReviewListener(FetchAnimeReviewListener fetchAnimeReviewListener) {
        this.fetchAnimeReviewListener = fetchAnimeReviewListener;
    }

    private ArrayList<Reviews> allReview;
    private ArrayList<AnotherUser> allReviewer;
    private HashMap<String, Series> allSeries;

    private ArrayList<Reviews> allAnimeReview;
    private ArrayList<AnotherUser> allAnimeReviewer;

    private int itemCount = 0;

    public void createReview(Reviews review) {
        DatabaseReference mReviewRef = FirebaseDatabase.getInstance()
                .getReference("series");

        HashMap<String, Object> reviewValues = new HashMap<>();
        reviewValues.put("rating", review.getRating());
        reviewValues.put("text", review.getText());
        reviewValues.put("review_date", review.getReview_date());
        reviewValues.put("anime_id", review.getAnime_id());

        mReviewRef.child(review.getAnime_id())
                .child("/review")
                .child(review.getUid())
                .setValue(reviewValues, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            if (createReviewListener != null) {
                                createReviewListener.onCreateReviewCompleted();
                            }
                        } else {
                            createReviewListener.onCreateReviewFailed(databaseError.getMessage());
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

                fetchAllReviewerData();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchAllReviewerData() {

        this.allReviewer = new ArrayList<>();

        for (final Reviews reviews: this.allReview) {
            DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users/" + reviews.getUid());
            mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    AnotherUser reviewer = dataSnapshot.child("/profile").getValue(AnotherUser.class);
                    reviewer.setUid(reviews.getUid());
                    allReviewer.add(reviewer);

                    Log.i("QStatus", reviews.getAnime_id() + " : " + reviews.getText() + " : " + reviewer.getUid());


                    if (allReview.size() == allReviewer.size()) {

                        fetchAllAnimeData();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void fetchAllAnimeData() {

        this.allSeries = new HashMap<>();

        for (Reviews reviews: this.allReview) {
            new NetworkConnectionManager().fetchThisSeriesData(this, Integer.parseInt(reviews.getAnime_id()), ApiConfig.FETCH_ANIME_REVIEW);
        }
    }

    public void fetchAnimeReviewData(Series series) {

        DatabaseReference mReviewRef = FirebaseDatabase.getInstance()
                .getReference("series/" + series.getId() + "/review");
        mReviewRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allAnimeReview = new ArrayList<>();

                for (DataSnapshot parent: dataSnapshot.getChildren()) {
                    Reviews reviews = parent.getValue(Reviews.class);
                    reviews.setUid(parent.getKey());

                    allAnimeReview.add(reviews);
                }

                fetchAnimeReviewerData();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fetchAnimeReviewListener.onFetchAnimeReviewFailed(databaseError.getMessage());
            }
        });
    }

    private void fetchAnimeReviewerData() {

        this.allAnimeReviewer = new ArrayList<>();
        for (final Reviews reviews: this.allAnimeReview) {
            DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users/" + reviews.getUid());
            mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    AnotherUser reviewer = dataSnapshot.child("/profile").getValue(AnotherUser.class);
                    reviewer.setUid(reviews.getUid());
                    allAnimeReviewer.add(reviewer);

                    if (allAnimeReview.size() == allAnimeReviewer.size()) {
                        if (fetchAnimeReviewListener != null) {
                            fetchAnimeReviewListener.onFetchAnimeReviewCompleted(allAnimeReview, allAnimeReviewer);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    fetchAnimeReviewListener.onFetchAnimeReviewFailed(databaseError.getMessage());
                }
            });
        }
    }

    @Override
    public void onResponse(String action, Call call, Response response) {
        switch (action) {
            case ApiConfig.FETCH_ANIME_REVIEW:
                Series series = (Series) response.body();
                this.allSeries.put(String.valueOf(series.getId()), series);
                this.itemCount++;

                if (this.allReview.size() == this.itemCount) {
                    Log.i("QStatus", "All : " + this.allSeries.toString());
                    if (fetchAllReviewListener != null) {
                        this.itemCount = 0;
                        fetchAllReviewListener.onFetchAllReviewCompleted(this.allReview, this.allReviewer, this.allSeries);
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


}

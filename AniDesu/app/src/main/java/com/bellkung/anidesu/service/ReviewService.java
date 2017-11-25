package com.bellkung.anidesu.service;

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
                                      ArrayList<Series> allSeries);
        void onFetchAllReviewDataFailed(String errorText);
    }
    private FetchAllReviewDataListener fetchAllReviewListener;

    public void setCreateReviewListener(CreateReviewListener createReviewListener) {
        this.createReviewListener = createReviewListener;
    }

    public void setFetchReviewListener(FetchAllReviewDataListener fetchAllReviewListener) {
        this.fetchAllReviewListener = fetchAllReviewListener;
    }

    private ArrayList<Reviews> allReview;
    private ArrayList<AnotherUser> allReviewer;
    private ArrayList<Series> allSeries;

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
                    if (fetchAllReviewListener != null) {
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

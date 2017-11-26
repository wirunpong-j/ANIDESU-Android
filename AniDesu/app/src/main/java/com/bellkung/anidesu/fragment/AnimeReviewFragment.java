package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.view.AnimeReviewListAdapter;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Reviews;
import com.bellkung.anidesu.service.ReviewService;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeReviewFragment extends Fragment implements ReviewService.FetchAllReviewDataListener {

    private ArrayList<Reviews> allReviews;
    private ArrayList<AnotherUser> allReviewer;
    private HashMap<String, Series> allSeries;

    private final int REVIEW_ROW = 1;

    @BindView(R.id.review_recycleView) RecyclerView review_recycleView;
    @BindView(R.id.review_no_data_view) ConstraintLayout review_no_data_view;
    @BindView(R.id.statusLoadingView) ConstraintLayout statusLoadingView;

    public static AnimeReviewFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AnimeReviewFragment fragment = new AnimeReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AnimeReviewFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.allReviews = new ArrayList<>();
        this.allReviewer = new ArrayList<>();
        this.allSeries = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_review, container, false);

        ButterKnife.bind(this, view);

        showIndicatorView();

        ReviewService reviewService = new ReviewService();
        reviewService.setFetchAllReviewListener(this);
        reviewService.fetchAllReviewData();

        return view;
    }

    private void setupView() {

        if (this.allReviews.isEmpty()) {
            this.review_no_data_view.setVisibility(View.VISIBLE);
            this.review_recycleView.setVisibility(View.VISIBLE);

        } else {
            AnimeReviewListAdapter adapter = new AnimeReviewListAdapter(getActivity(), getContext());
            adapter.setAllReviews(this.allReviews);
            adapter.setAllReviewer(this.allReviewer);
            adapter.setAllSeries(this.allSeries);

            this.review_recycleView.setLayoutManager(new GridLayoutManager(getContext(), REVIEW_ROW));
            this.review_recycleView.setAdapter(adapter);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideIndicatorView();
            }
        }, 1000);
    }


    @Override
    public void onFetchAllReviewCompleted(ArrayList<Reviews> allReview, ArrayList<AnotherUser> allReviewer, HashMap<String, Series> allSeries) {
        this.allReviews = allReview;
        this.allReviewer = allReviewer;
        this.allSeries = allSeries;

        setupView();
    }

    @Override
    public void onFetchAllReviewDataFailed(String errorText) {
        setupView();
    }

    private void showIndicatorView() {
        this.statusLoadingView.setVisibility(View.VISIBLE);
        this.statusLoadingView.setClickable(false);
    }

    private void hideIndicatorView() {
        this.statusLoadingView.setVisibility(View.GONE);
        this.statusLoadingView.setClickable(true);
    }
}

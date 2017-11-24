package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.AnimeReviewListAdapter;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Reviews;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeReviewFragment extends Fragment implements Reviews.FetchReviewDataListener {

    private ArrayList<Reviews> allReviews;
    private ArrayList<AnotherUser> allReviewer;
    private ArrayList<Series> allSeries;

    private final int REVIEW_ROW = 1;

    @BindView(R.id.review_recycleView) RecyclerView review_recycleView;

    public static AnimeReviewFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AnimeReviewFragment fragment = new AnimeReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AnimeReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.allReviews = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_review, container, false);

        ButterKnife.bind(this, view);

        Reviews reviews = new Reviews();
        reviews.setFetchReviewListener(this);
        reviews.fetchAllReviewData();

        return view;
    }

    private void setupView() {
        AnimeReviewListAdapter adapter = new AnimeReviewListAdapter(getActivity(), getContext());
        adapter.setAllReviews(this.allReviews);
        adapter.setAllReviewer(this.allReviewer);
        adapter.setAllSeries(this.allSeries);

        this.review_recycleView.setLayoutManager(new GridLayoutManager(getContext(), REVIEW_ROW));
        this.review_recycleView.setAdapter(adapter);
    }


    @Override
    public void onFetchedReviewCompleted(ArrayList<Reviews> allReview, ArrayList<AnotherUser> allReviewer, ArrayList<Series> allSeries) {
        this.allReviews = allReview;
        this.allReviewer = allReviewer;
        this.allSeries = allSeries;

        setupView();
    }

    @Override
    public void onFetchedReviewDataFailed() {

    }
}

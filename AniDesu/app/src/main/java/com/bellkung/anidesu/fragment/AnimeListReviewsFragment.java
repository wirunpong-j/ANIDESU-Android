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
import com.bellkung.anidesu.adapter.view.AnimeReviewAdapter;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Reviews;
import com.bellkung.anidesu.service.ReviewService;
import com.bellkung.anidesu.utils.KeyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListReviewsFragment extends Fragment implements ReviewService.FetchAnimeReviewListener {

    @BindView(R.id.anime_list_review_recycleView) RecyclerView anime_list_review_recycleView;

    private final int REVIEW_ROW = 1;
    private Series series;
    private ArrayList<Reviews> allReview;
    private ArrayList<AnotherUser> allReviewer;

    public AnimeListReviewsFragment() {}

    public static AnimeListReviewsFragment newInstance(Series series) {
        
        Bundle args = new Bundle();
        args.putParcelable(KeyUtils.KEY_SERIES, series);
        
        AnimeListReviewsFragment fragment = new AnimeListReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.series = getArguments().getParcelable(KeyUtils.KEY_SERIES);
        this.allReview = new ArrayList<>();
        this.allReviewer = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_list_reviews, container, false);
        ButterKnife.bind(this, view);

        ReviewService reviewService = new ReviewService();
        reviewService.setFetchAnimeReviewListener(this);
        reviewService.fetchAnimeReviewData(this.series);

        return view;
    }

    private void setupView() {
        AnimeReviewAdapter adapter = new AnimeReviewAdapter(getActivity(), getContext());
        adapter.setAllReview(this.allReview);
        adapter.setAllReviewer(this.allReviewer);
        adapter.setSeries(this.series);

        this.anime_list_review_recycleView.setLayoutManager(new GridLayoutManager(getContext(), REVIEW_ROW));
        this.anime_list_review_recycleView.setAdapter(adapter);
    }

    @Override
    public void onFetchAnimeReviewCompleted(ArrayList<Reviews> allReview, ArrayList<AnotherUser> allReviewer) {
        this.allReview = allReview;
        this.allReviewer= allReviewer;

        setupView();
    }

    @Override
    public void onFetchAnimeReviewFailed(String errorText) {

    }
}

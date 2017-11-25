package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListReviewsFragment extends Fragment {

    @BindView(R.id.anime_list_review_recycleView) RecyclerView anime_list_review_recycleView;

    private final int REVIEW_ROW = 1;

    public AnimeListReviewsFragment() {}

    public static AnimeListReviewsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AnimeListReviewsFragment fragment = new AnimeListReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_list_reviews, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void setupView() {

    }

}

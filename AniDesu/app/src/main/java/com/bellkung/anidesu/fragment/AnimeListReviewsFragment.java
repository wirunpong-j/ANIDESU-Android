package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListReviewsFragment extends Fragment {


    public AnimeListReviewsFragment() {
        // Required empty public constructor
    }

    public static AnimeListReviewsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AnimeListReviewsFragment fragment = new AnimeListReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_list_reviews, container, false);
    }

}

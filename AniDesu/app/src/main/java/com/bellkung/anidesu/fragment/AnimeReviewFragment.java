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
public class AnimeReviewFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_review, container, false);
    }

}

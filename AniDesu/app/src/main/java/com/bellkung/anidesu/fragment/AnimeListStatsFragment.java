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
public class AnimeListStatsFragment extends Fragment {


    public AnimeListStatsFragment() {
        // Required empty public constructor
    }

    public static AnimeListStatsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AnimeListStatsFragment fragment = new AnimeListStatsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_list_stats, container, false);
    }

}

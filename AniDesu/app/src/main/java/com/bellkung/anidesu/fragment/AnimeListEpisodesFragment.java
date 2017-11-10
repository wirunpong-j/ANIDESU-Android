package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.utils.KeyUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListEpisodesFragment extends Fragment {

    private Series series;

    public AnimeListEpisodesFragment() {
        // Required empty public constructor
    }

    public static AnimeListEpisodesFragment newInstance(Series series) {
        
        Bundle args = new Bundle();
        args.putParcelable(KeyUtils.KEY_SERIES, series);
        
        AnimeListEpisodesFragment fragment = new AnimeListEpisodesFragment();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_list_episodes, container, false);
    }

}
package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.AnimeListAdapter;
import com.bellkung.anidesu.api.model.Series;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListFragment extends Fragment {

    private String season;
    private ArrayList<Series> allSeries;
    private final int ROW_ITEM = 2;

    @BindView(R.id.anime_list_recyclerView) RecyclerView anime_list_recyclerView;


    public static AnimeListFragment newInstance(String season, ArrayList<Series> series) {
        AnimeListFragment fragment = new AnimeListFragment();
        Bundle args = new Bundle();
        args.putString("season", season);
        args.putParcelableArrayList("allSeries", series);
        fragment.setArguments(args);
        return fragment;
    }

    public AnimeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.season = getArguments().getString("season");
        this.allSeries = getArguments().getParcelableArrayList("allSeries");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_anime_list, container, false);
        ButterKnife.bind(this, view);

        this.anime_list_recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), this.ROW_ITEM));
        AnimeListAdapter adapter = new AnimeListAdapter(getActivity());
        adapter.setData(this.allSeries);
        this.anime_list_recyclerView.setAdapter(adapter);

        return view;
    }

}

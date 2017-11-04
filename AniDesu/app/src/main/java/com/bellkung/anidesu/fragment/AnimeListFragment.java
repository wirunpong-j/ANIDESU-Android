package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellkung.anidesu.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListFragment extends Fragment {

    private String season;
    private int page;

//    @BindView(R.id.anime_model_image) ImageView anime_model_image;
//    @BindView(R.id.anime_model_name) TextView anime_model_name;
//    @BindView(R.id.anime_model_rating) TextView anime_model_rating;
//    @BindView(R.id.anime_model_type) TextView anime_model_type;
//    @BindView(R.id.anime_model_relation_type) TextView anime_model_relation_type;

    public static AnimeListFragment newInstance(String season, int page) {
        AnimeListFragment fragment = new AnimeListFragment();
        Bundle args = new Bundle();
        args.putString("season", season);
        args.putInt("page", page);
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
        this.page = getArguments().getInt("page");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_anime_list, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

}

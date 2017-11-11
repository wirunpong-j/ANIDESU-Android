package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.ExternalLinkAdapter;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.utils.KeyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListStatsFragment extends Fragment {

    @BindView(R.id.ext_link_recycleView) RecyclerView extLinkRecycleView;
    @BindView(R.id.completedTextView) TextView completedTextView;
    @BindView(R.id.droppedTextView) TextView droppedTextView;
    @BindView(R.id.planTextView) TextView planTextView;
    @BindView(R.id.currentTextView) TextView currentTextView;

    private Series series;

    private final int LINKS_ROW = 1;
    private final String PEOPLE = " People";

    public AnimeListStatsFragment() {
        // Required empty public constructor
    }

    public static AnimeListStatsFragment newInstance(Series series) {
        Bundle args = new Bundle();
        args.putParcelable(KeyUtils.KEY_SERIES, series);
        AnimeListStatsFragment fragment = new AnimeListStatsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.series = getArguments().getParcelable(KeyUtils.KEY_SERIES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_list_stats, container, false);
        ButterKnife.bind(this, view);
        setupView();
        return view;
    }

    private void setupView() {
        ExternalLinkAdapter adapter = new ExternalLinkAdapter(getActivity(), getContext());
        adapter.setAllExtLinks(this.series.getExternal_links());
        this.extLinkRecycleView.setAdapter(adapter);
        this.extLinkRecycleView.setLayoutManager(new GridLayoutManager(getContext(), LINKS_ROW));
        this.completedTextView.setText(String.valueOf(KeyUtils.NUM_DEFAULT) + PEOPLE);
        this.droppedTextView.setText(String.valueOf(KeyUtils.NUM_DEFAULT) + PEOPLE);
        this.planTextView.setText(String.valueOf(KeyUtils.NUM_DEFAULT) + PEOPLE);
        this.currentTextView.setText(String.valueOf(KeyUtils.NUM_DEFAULT) + PEOPLE);
    }

}

package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.view.SeriesExtraAdapter;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.utils.KeyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListExtrasFragment extends Fragment {

    private Series series;

    @BindView(R.id.characterRecycleView) RecyclerView characterRecycleView;
    @BindView(R.id.staffRecycleView) RecyclerView staffRecycleView;
    @BindView(R.id.character_no_data) ConstraintLayout character_no_data;
    @BindView(R.id.staff_no_data) ConstraintLayout staff_no_data;

    public AnimeListExtrasFragment() {}

    public static AnimeListExtrasFragment newInstance(Series series) {
        
        Bundle args = new Bundle();
        args.putParcelable(KeyUtils.KEY_SERIES, series);
        AnimeListExtrasFragment fragment = new AnimeListExtrasFragment();
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
        View view = inflater.inflate(R.layout.fragment_anime_list_extras, container, false);
        ButterKnife.bind(this, view);

        setupView();

        return view;
    }

    private void setupView() {
        if (this.series.getCharacters().isEmpty()) {
            this.character_no_data.setVisibility(View.VISIBLE);
            this.characterRecycleView.setVisibility(View.GONE);

        } else {
            SeriesExtraAdapter characterAdapter = new SeriesExtraAdapter(getActivity(), KeyUtils.GET_CHARACTERS);
            characterAdapter.setCharacters(this.series.getCharacters());
            this.characterRecycleView.setAdapter(characterAdapter);
            this.characterRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        }

        if (this.series.getStaff().isEmpty()) {
            this.staff_no_data.setVisibility(View.VISIBLE);
            this.staffRecycleView.setVisibility(View.GONE);

        } else {
            SeriesExtraAdapter staffAdapter = new SeriesExtraAdapter(getActivity(), KeyUtils.GET_STAFFS);
            staffAdapter.setStaffs(this.series.getStaff());
            this.staffRecycleView.setAdapter(staffAdapter);
            this.staffRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        }

    }

}

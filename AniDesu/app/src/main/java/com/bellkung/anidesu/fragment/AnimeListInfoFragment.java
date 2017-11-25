package com.bellkung.anidesu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.utils.FormatCustomManager;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListInfoFragment extends Fragment {

    private Series series;

    @BindView(R.id.animeCover) ImageView mAnimeCover;
    @BindView(R.id.animeNameTextView) TextView mAnimeNameTextView;
    @BindView(R.id.airingStatusTextView) TextView mAiringStatusTextView;
    @BindView(R.id.animeEngNameTextView) TextView mAnimeEngNameTextView;
    @BindView(R.id.animeTypeTextView) TextView mAnimeTypeTextView;
    @BindView(R.id.startDateTextView) TextView mStartDateTextView;
    @BindView(R.id.endDateTextView) TextView mEndDateTextView;
    @BindView(R.id.seasonTextView) TextView mSeasonTextView;
    @BindView(R.id.nextEpisodeTextView) TextView mNextEpisodeTextView;
    @BindView(R.id.animeTagTextView) TextView mAnimeTagTextView;
    @BindView(R.id.genreTextView) TextView mGenreTextView;
    @BindView(R.id.totalEPTextView) TextView mTotalEPTextView;
    @BindView(R.id.hashTagTextView) TextView mHashTagTextView;
    @BindView(R.id.animeOriginTextView) TextView mAnimeOriginTextView;
    @BindView(R.id.mainStudioTextView) TextView mMainStudioTextView;
    @BindView(R.id.descTextView) TextView mDescTextView;

    public AnimeListInfoFragment() {
    }

    public static AnimeListInfoFragment newInstance(Series series) {
        Bundle args = new Bundle();
        args.putParcelable(KeyUtils.KEY_SERIES, series);
        
        AnimeListInfoFragment fragment = new AnimeListInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_anime_list_info, container, false);
        ButterKnife.bind(this, view);
        setupUI();
        return view;
    }

    private void setupUI() {
        Glide.with(this).load(this.series.getImage_url_lge()).into(this.mAnimeCover);
        this.mAnimeNameTextView.setText(this.series.getTitle_romaji());
        this.mAiringStatusTextView.setText(this.series.getAiring_status());
        this.mAnimeEngNameTextView.setText(this.series.getTitle_english());
        this.mAnimeTypeTextView.setText(this.series.getType());
        this.mStartDateTextView.setText(String.valueOf(FormatCustomManager.parseToDate(this.series.getStart_date_fuzzy())));
        this.mEndDateTextView.setText(String.valueOf(FormatCustomManager.parseToDate(this.series.getEnd_date_fuzzy())));

        this.mSeasonTextView.setText("-");
        this.mNextEpisodeTextView.setText(this.series.getAiring().getNextEPandTime());
        this.mAnimeTagTextView.setText("-");
        this.mGenreTextView.setText(TextUtils.join(KeyUtils.DELIMITER, this.series.getGenres()));
        this.mTotalEPTextView.setText(String.valueOf(this.series.getTotal_episodes()));
        this.mHashTagTextView.setText(this.series.getHashtag());
        this.mAnimeOriginTextView.setText(this.series.getSource());
        this.mMainStudioTextView.setText(this.series.getStudio().get(KeyUtils.NUM_DEFAULT).getStudio_name());
        this.mDescTextView.setText(this.series.getDescription());
    }

}

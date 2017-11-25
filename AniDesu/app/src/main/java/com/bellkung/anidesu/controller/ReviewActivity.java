package com.bellkung.anidesu.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.utils.FormatCustomManager;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Reviews;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewActivity extends AppCompatActivity {

    private Series series;
    private AnotherUser reviewer;
    private Reviews reviews;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.c_reviewer_imageView) CircleImageView c_reviewer_imageView;
    @BindView(R.id.c_reviewer_name_textView) TextView c_reviewer_name_textView;
    @BindView(R.id.c_review_date) TextView c_review_date;
    @BindView(R.id.c_anime_name_textView) TextView c_anime_name_textView;
    @BindView(R.id.c_anime_detail_textView) TextView c_anime_detail_textView;
    @BindView(R.id.c_review_text_textView) TextView c_review_text_textView;
    @BindView(R.id.c_anime_imageView) ImageView c_anime_imageView;
    @BindView(R.id.c_review_ratingbar) RatingBar c_review_ratingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        this.series = getIntent().getParcelableExtra(KeyUtils.KEY_REVIEW_SERIES);
        this.reviewer = getIntent().getParcelableExtra(KeyUtils.KEY_REVIEW_REVIEWER);
        this.reviews = getIntent().getParcelableExtra(KeyUtils.KEY_REVIEW_REVIEW);

        ButterKnife.bind(this, this);

        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initialView();

    }

    private void initialView() {
        Glide.with(this).load(this.reviewer.getImage_url_profile()).into(this.c_reviewer_imageView);
        Glide.with(this).load(this.series.getImage_url_lge()).into(this.c_anime_imageView);

        this.c_reviewer_name_textView.setText(this.reviewer.getDisplay_name());
        this.c_review_date.setText(FormatCustomManager.parseOnFirebaseDateTime(this.reviews.getReview_date()));
        this.c_anime_name_textView.setText(this.series.getTitle_romaji());
        this.c_anime_detail_textView.setText(this.series.getDescription());
        this.c_review_text_textView.setText(this.reviews.getText());
        this.c_review_ratingbar.setRating(this.reviews.getRating());
    }

}

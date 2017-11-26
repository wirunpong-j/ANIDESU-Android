package com.bellkung.anidesu.adapter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.controller.ReviewActivity;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Reviews;
import com.bellkung.anidesu.utils.FormatCustomManager;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by BellKunG on 26/11/2017 AD.
 */

public class AnimeReviewAdapter extends RecyclerView.Adapter<AnimeReviewAdapter.Holder> {

    private ArrayList<Reviews> allReview;
    private ArrayList<AnotherUser> allReviewer;
    private Series series;
    private Activity mActivity;
    private Context mContext;

    public AnimeReviewAdapter(Activity mActivity, Context mContext) {
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anime_review, null);

        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Reviews review = this.allReview.get(position);
        AnotherUser reviewer = this.allReviewer.get(position);

        Glide.with(this.mActivity).load(reviewer.getImage_url_profile()).into(holder.a_reviewer_imageView);
        holder.a_reviwer_name_textView.setText(reviewer.getDisplay_name());
        holder.a_review_date.setText(FormatCustomManager.parseOnFirebaseDateTime(review.getReview_date()));
        holder.a_review_textView.setText(review.getText());
        holder.a_review_ratingbar.setRating(review.getRating());
    }

    @Override
    public int getItemCount() {
        return this.allReview.size();
    }

    public void setAllReview(ArrayList<Reviews> allReview) {
        this.allReview = allReview;
    }

    public void setAllReviewer(ArrayList<AnotherUser> allReviewer) {
        this.allReviewer = allReviewer;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.a_review_view) ConstraintLayout a_review_view;
        @BindView(R.id.a_reviewer_imageView) CircleImageView a_reviewer_imageView;
        @BindView(R.id.a_reviwer_name_textView) TextView a_reviwer_name_textView;
        @BindView(R.id.a_review_date) TextView a_review_date;
        @BindView(R.id.a_review_textView) TextView a_review_textView;
        @BindView(R.id.a_review_ratingbar) RatingBar a_review_ratingbar;

        public Holder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            a_review_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Reviews review = allReview.get(getAdapterPosition());
            AnotherUser reviewer = allReviewer.get(getAdapterPosition());

            Intent intent = new Intent(mContext, ReviewActivity.class);
            intent.putExtra(KeyUtils.KEY_REVIEW_SERIES, series);
            intent.putExtra(KeyUtils.KEY_REVIEW_REVIEW, review);
            intent.putExtra(KeyUtils.KEY_REVIEW_REVIEWER, reviewer);

            mContext.startActivity(intent);
        }
    }
}

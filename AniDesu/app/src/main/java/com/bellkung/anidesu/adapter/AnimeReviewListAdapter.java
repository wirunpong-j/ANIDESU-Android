package com.bellkung.anidesu.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Reviews;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by BellKunG on 24/11/2017 AD.
 */

public class AnimeReviewListAdapter extends RecyclerView.Adapter<AnimeReviewListAdapter.Holder>  {

    private ArrayList<Reviews> allReviews;
    private ArrayList<AnotherUser> allReviewer;
    private ArrayList<Series> allSeries;

    private Activity mActivity;
    private Context mContext;

    private final String WRITE_BY = "By : ";

    public AnimeReviewListAdapter(Activity activity, Context context) {
        this.mActivity = activity;
        this.mContext = context;
        this.allReviews = new ArrayList<>();
        this.allReviewer = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_review, null);

        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Reviews review = this.allReviews.get(position);
        AnotherUser reviewer = this.allReviewer.get(position);
        Series series = this.allSeries.get(position);

        View hover = LayoutInflater.from(mContext).inflate(R.layout.hover_review, null);
        holder.blurLayout.setHoverView(hover);
        holder.blurLayout.addChildAppearAnimator(hover, R.id.reviewer_imageView, Techniques.Landing);
        holder.blurLayout.addChildDisappearAnimator(hover, R.id.reviewer_imageView, Techniques.TakingOff);

        holder.blurLayout.addChildAppearAnimator(hover, R.id.reviewer_name_textView, Techniques.Landing);
        holder.blurLayout.addChildDisappearAnimator(hover, R.id.reviewer_name_textView, Techniques.TakingOff);

        holder.blurLayout.addChildAppearAnimator(hover, R.id.review_textView, Techniques.Landing);
        holder.blurLayout.addChildDisappearAnimator(hover, R.id.review_textView, Techniques.TakingOff);

        holder.blurLayout.addChildAppearAnimator(hover, R.id.readMoreBtn, Techniques.Landing);
        holder.blurLayout.addChildDisappearAnimator(hover, R.id.readMoreBtn, Techniques.TakingOff);

        holder.blurLayout.addChildAppearAnimator(hover, R.id.review_ratingbar, Techniques.Landing);
        holder.blurLayout.addChildDisappearAnimator(hover, R.id.review_ratingbar, Techniques.TakingOff);

        Glide.with(this.mContext).load(series.getImage_url_banner()).into(holder.review_imageView);
        CircleImageView reviewer_imageView = hover.findViewById(R.id.reviewer_imageView);
        TextView reviewer_name_textView = hover.findViewById(R.id.reviewer_name_textView);
        TextView review_textView = hover.findViewById(R.id.review_textView);
        RatingBar review_ratingbar = hover.findViewById(R.id.review_ratingbar);
        Button readMoreBtn = hover.findViewById(R.id.readMoreBtn);

        Glide.with(this.mContext).load(reviewer.getImage_url_profile()).into(reviewer_imageView);
        reviewer_name_textView.setText(WRITE_BY + reviewer.getDisplay_name());
        review_textView.setText(review.getText());
        review_ratingbar.setRating(review.getRating());

    }

    @Override
    public int getItemCount() {
        return this.allReviews.size();
    }

    public void setAllReviews(ArrayList<Reviews> allReviews) {
        this.allReviews = allReviews;
    }

    public void setAllReviewer(ArrayList<AnotherUser> allReviewer) {
        this.allReviewer = allReviewer;
    }

    public void setAllSeries(ArrayList<Series> allSeries) {
        this.allSeries = allSeries;
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.blurLayout) BlurLayout blurLayout;
        @BindView(R.id.review_imageView) ImageView review_imageView;

        public Holder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

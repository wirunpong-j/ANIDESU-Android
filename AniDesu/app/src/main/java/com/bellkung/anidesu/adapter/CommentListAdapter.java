package com.bellkung.anidesu.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.list_post.Comment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by BellKunG on 23/11/2017 AD.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.Holder> {

    private ArrayList<Comment> allComment;
    private ArrayList<AnotherUser> allCommentor;
    private Activity mActivity;
    private Context mContext;

    public CommentListAdapter(Activity mActivity, Context mContext) {
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comment, null);

        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Comment comment = this.allComment.get(position);
        AnotherUser aUser = this.allCommentor.get(position);

        Glide.with(this.mContext).load(aUser.getImage_url_profile()).into(holder.commentor_image);
        holder.comment_textView.setText(comment.getComment_text());
        holder.comment_date_textView.setText(comment.getComment_date());
    }

    @Override
    public int getItemCount() {
        return allComment.size();
    }

    public void setAllComment(ArrayList<Comment> allComment) {
        this.allComment = allComment;
    }

    public void setAllCommentor(ArrayList<AnotherUser> allCommentor) {
        this.allCommentor = allCommentor;
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.commentor_image2) CircleImageView commentor_image;
        @BindView(R.id.comment_textView) TextView comment_textView;
        @BindView(R.id.comment_date_textView) TextView comment_date_textView;

        public Holder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

package com.bellkung.anidesu.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.CommentListAdapter;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Posts;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.model.list_post.Comment;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentPostsActivity extends AppCompatActivity implements CommentListener {

    private Posts posts;
    private AnotherUser aUser;
    private ArrayList<Comment> allComment;
    private ArrayList<AnotherUser> allCommentor;
    private final int COMMENT_ROW = 1;

    private CommentListener commentListener;

    @BindView(R.id.commentToolbar) Toolbar commentToolbar;
    @BindView(R.id.c_post_profile_image) CircleImageView c_post_profile_image;
    @BindView(R.id.c_posts_display_name) TextView c_posts_display_name;
    @BindView(R.id.c_status_text) TextView c_status_text;
    @BindView(R.id.comment_editText) EditText comment_editText;
    @BindView(R.id.commentaryBtn) ImageButton commentaryBtn;
    @BindView(R.id.c_comment_profile_image) CircleImageView c_comment_profile_image;
    @BindView(R.id.comment_list_recycleView) RecyclerView comment_list_recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_posts);

        ButterKnife.bind(this, this);

        this.posts = getIntent().getParcelableExtra(KeyUtils.COMMENT_POST);
        this.aUser = getIntent().getParcelableExtra(KeyUtils.COMMENT_USER);

        this.setSupportActionBar(this.commentToolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.commentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.commentListener = this;
        fetchAllCommentData();

    }

    private void initialView() {
        Glide.with(this).load(this.aUser.getImage_url_profile()).into(this.c_post_profile_image);
        Glide.with(this).load(User.getInstance().getImage_url_profile()).into(this.c_comment_profile_image);
        this.c_posts_display_name.setText(this.aUser.getDisplay_name());
        this.c_status_text.setText(this.posts.getStatus());

        CommentListAdapter adapter = new CommentListAdapter(this, this);
        adapter.setAllComment(allComment);
        adapter.setAllCommentor(allCommentor);
        this.comment_list_recycleView.setLayoutManager(new GridLayoutManager(this, COMMENT_ROW));
        this.comment_list_recycleView.setAdapter(adapter);
    }

    private void fetchAllCommentData() {
        DatabaseReference mCommentRef = FirebaseDatabase.getInstance()
                .getReference("posts/" + this.posts.getPost_key() + "/comment");
        Query query = mCommentRef.orderByChild("comment_date");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allComment = new ArrayList<>();
                for (DataSnapshot parent: dataSnapshot.getChildren()) {
                    Comment comment = parent.getValue(Comment.class);
                    allComment.add(comment);
                }

                if (commentListener != null) {
                    commentListener.onFetchCommentData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onFetchCommentData() {
        DatabaseReference mCommentorRef = FirebaseDatabase.getInstance()
                .getReference("users/");
        mCommentorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allCommentor = new ArrayList<>();
                for (Comment comment: allComment) {
                    AnotherUser commentor = dataSnapshot.child(comment.getUid())
                            .child("profile")
                            .getValue(AnotherUser.class);
                    allCommentor.add(commentor);
                }
                if (commentListener != null) {
                    commentListener.onFetchCommentorData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onFetchCommentorData() {
        initialView();
    }
}

interface CommentListener {
    void onFetchCommentData();
    void onFetchCommentorData();
}

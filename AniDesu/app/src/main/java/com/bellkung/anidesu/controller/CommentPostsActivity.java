package com.bellkung.anidesu.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.view.CommentListAdapter;
import com.bellkung.anidesu.utils.FormatCustomManager;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.service.CommentService;
import com.bellkung.anidesu.model.Posts;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.model.Comment;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentPostsActivity extends AppCompatActivity implements CommentService.CommentListener {

    private Posts posts;
    private AnotherUser aUser;
    private ArrayList<Comment> allComment;
    private ArrayList<AnotherUser> allCommentor;
    private CommentService commentService;
    private final int COMMENT_ROW = 1;

    @BindView(R.id.commentToolbar) Toolbar commentToolbar;
    @BindView(R.id.c_post_profile_image) CircleImageView c_post_profile_image;
    @BindView(R.id.c_posts_display_name) TextView c_posts_display_name;
    @BindView(R.id.c_status_text) TextView c_status_text;
    @BindView(R.id.comment_editText) EditText comment_editText;
    @BindView(R.id.c_comment_profile_image) CircleImageView c_comment_profile_image;
    @BindView(R.id.comment_list_recycleView) RecyclerView comment_list_recycleView;
    @BindView(R.id.c_posts_date_time) TextView c_posts_date_time;

    @BindView(R.id.postLoadingView) ConstraintLayout postLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_posts);

        ButterKnife.bind(this, this);

        showIndicatorView();

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

        // Fetch all Comment and all commentor data
        this.commentService = new CommentService();
        this.commentService.setCommentListener(this);
        this.commentService.fetchAllCommentData(this.posts);

    }

    private void initialView() {
        Glide.with(getApplicationContext()).load(this.aUser.getImage_url_profile()).into(this.c_post_profile_image);
        Glide.with(getApplicationContext()).load(User.getInstance().getImage_url_profile()).into(this.c_comment_profile_image);
        this.c_posts_display_name.setText(this.aUser.getDisplay_name());
        this.c_status_text.setText(this.posts.getStatus());
        this.c_posts_date_time.setText(this.posts.getPost_date());

        CommentListAdapter adapter = new CommentListAdapter(this, this);
        adapter.setAllComment(allComment);
        adapter.setAllCommentor(allCommentor);
        this.comment_list_recycleView.setLayoutManager(new GridLayoutManager(this, COMMENT_ROW));
        this.comment_list_recycleView.setAdapter(adapter);

        hideIndicatorView();
    }

    @OnClick(R.id.commentaryBtn)
    public void commentIsSubmit() {
        String comment_text = String.valueOf(this.comment_editText.getText());
        Comment comment = new Comment();
        comment.setUid(User.getInstance().getUid());
        comment.setComment_text(comment_text);
        comment.setComment_date(FormatCustomManager.getCurrentDateTime());

        this.commentService.setCommentListener(this);
        this.commentService.saveComment(this.posts, comment);
    }

    @Override
    public void onFetchCommentDataCompleted(ArrayList<Comment> allComment, ArrayList<AnotherUser> allCommentor) {
        this.allComment = allComment;
        this.allCommentor = allCommentor;

        initialView();
    }

    @Override
    public void onAddCommentCompleted() {
        Toast.makeText(this, "Comment!!!", Toast.LENGTH_SHORT).show();

        this.comment_editText.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void showIndicatorView() {
        this.postLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideIndicatorView() {
        this.postLoadingView.setVisibility(View.GONE);
    }
}


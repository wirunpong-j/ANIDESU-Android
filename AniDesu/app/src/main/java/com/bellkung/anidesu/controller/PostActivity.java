package com.bellkung.anidesu.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.service.PostService;
import com.bellkung.anidesu.model.Posts;
import com.bellkung.anidesu.model.User;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostActivity extends AppCompatActivity implements PostService.CreatePostListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.post_profile_image) CircleImageView post_profile_image;
    @BindView(R.id.posts_display_name) TextView posts_display_name;
    @BindView(R.id.post_editText) EditText post_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

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
        Glide.with(getApplicationContext()).load(User.getInstance().getImage_url_profile()).into(this.post_profile_image);
        this.posts_display_name.setText(User.getInstance().getDisplay_name());

    }

    @OnClick(R.id.postBtn)
    public void onPostBtnPressed() {
        Posts posts = new Posts();
        posts.setUid(User.getInstance().getUid());
        posts.setStatus(String.valueOf(this.post_editText.getText()));

        PostService postService = new PostService();
        postService.setCreatePostListener(this);
        postService.createPost(posts);

    }

    @Override
    public void onCreatePostCompleted() {
        Toast.makeText(this, "Success!!!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCreatePostFailed(String errorText) {
        Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show();
    }
}

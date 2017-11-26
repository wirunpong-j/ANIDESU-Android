package com.bellkung.anidesu.service;

import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Posts;
import com.bellkung.anidesu.utils.FormatCustomManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by BellKunG on 25/11/2017 AD.
 */

public class PostService {

    public interface FetchPostListener {
        void onFetchAllPostCompleted(HashMap<String, Posts> allPost,
                                     HashMap<String, AnotherUser> allWriter,
                                     ArrayList<String> allKeySet);
        void onFetchAllPostFailed(String errorText);
    }
    private FetchPostListener fetchPostListener;

    public interface CreatePostListener {
        void onCreatePostCompleted();
        void onCreatePostFailed(String errorText);
    }
    private CreatePostListener createPostListener;

    public void setFetchPostListener(FetchPostListener fetchPostListener) {
        this.fetchPostListener = fetchPostListener;
    }

    public void setCreatePostListener(CreatePostListener createPostListener) {
        this.createPostListener = createPostListener;
    }

    private HashMap<String, Posts> allPost;
    private HashMap<String, AnotherUser> allWriter;
    private ArrayList<String> allKeySet;

    public void createPost(Posts posts) {
        DatabaseReference mPostRef = FirebaseDatabase.getInstance()
                .getReference("posts");

        String key = mPostRef.push().getKey();

        HashMap<String, Object> postValues = new HashMap<>();
        postValues.put("post_key", key);
        postValues.put("uid", posts.getUid());
        postValues.put("status", posts.getStatus());
        postValues.put("post_date", FormatCustomManager.getCurrentDateTime());
        postValues.put("like_count", 0);

        HashMap<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);

        mPostRef.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    if (createPostListener != null) {
                        createPostListener.onCreatePostCompleted();
                    }
                } else {
                    createPostListener.onCreatePostFailed(databaseError.getMessage());
                }
            }
        });
    }

    public void fetchAllPostData() {
        DatabaseReference mPostsRef = FirebaseDatabase.getInstance().getReference("posts");
        Query mPostsQuery = mPostsRef.orderByChild("post_date");
        mPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // clear data when call this method.
                allPost = new HashMap<>();
                allWriter = new HashMap<>();
                allKeySet = new ArrayList<>();

                for (DataSnapshot parent: dataSnapshot.getChildren()) {
                    final Posts post = parent.getValue(Posts.class);
                    allKeySet.add(parent.getKey());
                    allPost.put(parent.getKey(), post);

                    HashMap<String, Boolean> allLike = new HashMap<>();

                    for (DataSnapshot child: parent.child("/like").getChildren()) {
                        allLike.put(child.getKey(), (Boolean) child.getValue());
                    }
                    post.setAllLike(allLike);
                }

                if (fetchPostListener != null) {
                    fetchWriterData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (fetchPostListener != null) {
                    fetchPostListener.onFetchAllPostFailed(databaseError.getMessage());
                }
            }
        });
    }

    private void fetchWriterData() {
        Iterator it = this.allPost.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            final Posts post = (Posts) pair.getValue();

            DatabaseReference mPostsRef = FirebaseDatabase.getInstance()
                    .getReference("users/" + post.getUid());
            mPostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    AnotherUser aUser = dataSnapshot.child("/profile").getValue(AnotherUser.class);
                    aUser.setUid(dataSnapshot.getKey());

                    allWriter.put(post.getPost_key(), aUser);

                    if (allWriter.size() == allPost.size()) {
                        if (fetchPostListener != null) {
                            fetchPostListener.onFetchAllPostCompleted(allPost, allWriter, allKeySet);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if (fetchPostListener != null) {
                        fetchPostListener.onFetchAllPostFailed(databaseError.getMessage());
                    }
                }
            });
        }
    }
}

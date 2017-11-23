package com.bellkung.anidesu.model;

import com.bellkung.anidesu.model.list_post.Comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by BellKunG on 23/11/2017 AD.
 */

public class CommentService {

    public interface CommentListener {
        void onFetchCommentDataCompleted(ArrayList<Comment> allComment, ArrayList<AnotherUser> allCommentor);
    }
    private CommentListener commentListener;
    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
    }

    private ArrayList<Comment> allComment;
    private ArrayList<AnotherUser> allCommentor;

    public void fetchAllCommentData(Posts post) {
        DatabaseReference mCommentRef = FirebaseDatabase.getInstance()
                .getReference("posts/" + post.getPost_key() + "/comment");
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
                    fetchAllCommentorData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchAllCommentorData() {
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
                    commentListener.onFetchCommentDataCompleted(allComment, allCommentor);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

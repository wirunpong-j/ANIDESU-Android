package com.bellkung.anidesu.service;

import android.util.Log;

import com.bellkung.anidesu.model.AnotherUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by BellKunG on 25/11/2017 AD.
 */

public class AccountService {

    public interface CurrentAccountListener {
        void onCurrentAccount();
        void onFailed(String errorText);
    }
    private CurrentAccountListener currentAccountListener;

    public void setCurrentAccountListener(CurrentAccountListener currentAccountListener) {
        this.currentAccountListener = currentAccountListener;
    }

    public void isCurrentAccount(final AnotherUser aUser) {

        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference().child("users");
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean status = true;
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(aUser.getUid())) {
                        status = true;
                        break;
                    } else {
                        status = false;
                    }
                }

                if (currentAccountListener != null) {
                    if (status) {
                        currentAccountListener.onCurrentAccount();
                    } else {
                        registerNewAccount(aUser);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("QStatus : ", "isCurrentAccount Cancle");
            }
        });
    }

    private void registerNewAccount(final AnotherUser aUser) {
        DatabaseReference mUsersRef = FirebaseDatabase.getInstance()
                .getReference("users");
        mUsersRef.child(aUser.getUid()).child("profile")
                .setValue(aUser, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            currentAccountListener.onCurrentAccount();
                        } else {
                            currentAccountListener.onFailed(databaseError.getMessage());
                        }
                    }
                });
    }
}

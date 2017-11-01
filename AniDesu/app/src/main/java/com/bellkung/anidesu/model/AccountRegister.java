package com.bellkung.anidesu.model;

import android.util.Log;
import android.widget.Toast;

import com.bellkung.anidesu.controller.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by BellKunG on 1/11/2017 AD.
 */

public class AccountRegister {

    public interface AccountRegisterListener{
        void onCurrentAccount(boolean status);
    }
    public AccountRegisterListener listener;

    private String uid;
    private String display_name;
    private String email;
    private String about;
    private String image_url_profile;

    public AccountRegister(String uid) {
        this.uid = uid;
    }


    public boolean registerNewAccount() {
        boolean status = true;

        try {
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference mUsersRef = mRootRef.child("users");
            mUsersRef.child(this.uid).child("display_name").setValue(this.display_name);
            mUsersRef.child(this.uid).child("about").setValue(this.about);
            mUsersRef.child(this.uid).child("email").setValue(this.email);
            mUsersRef.child(this.uid).child("image_url_profile").setValue(this.image_url_profile);

        } catch (DatabaseException ex) {
            Log.i("Status : ", "registerNewAccount Failed");
            status = false;
        }

        return status;

    }

    public void isCurrentAccount() {
        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference().child("users");
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean status = true;
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(uid)) {
                        Log.i("Status : ", "isCurrentAccount YES");
                        status = true;
                        break;
                    } else {
                        Log.i("Status : ", "isCurrentAccount NO");
                        status = false;
                    }
                }

                if (listener != null) {
                    listener.onCurrentAccount(status);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Status : ", "isCurrentAccount Cancle");
            }
        });
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage_url_profile() {
        return image_url_profile;
    }

    public void setImage_url_profile(String image_url_profile) {
        this.image_url_profile = image_url_profile;
    }

    public void setListener(AccountRegisterListener listener) {
        this.listener = listener;
    }
}

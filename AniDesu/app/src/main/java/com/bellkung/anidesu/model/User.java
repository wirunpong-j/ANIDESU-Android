package com.bellkung.anidesu.model;

import android.util.Log;
import com.bellkung.anidesu.model.list_anime.Completed;
import com.bellkung.anidesu.model.list_anime.Dropped;
import com.bellkung.anidesu.model.list_anime.PlanToWatch;
import com.bellkung.anidesu.model.list_anime.Watching;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by BellKunG on 22/10/2017 AD.
 */

public class User {

    public interface UserDataListener {
        void onDataChanged();
    }

    private String uid;
    private String display_name;
    private String email;
    private String about;
    private String image_url_profile;

    private ArrayList<PlanToWatch> list_plan;
    private ArrayList<Watching> list_watching;
    private ArrayList<Completed> list_completed;
    private ArrayList<Dropped> list_dropped;

    public UserDataListener listener;

    public User(String uid) {
        this.uid = uid;
        fetchUserProfile();
    }

    public void fetchUserProfile() {
        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users/" + this.uid);
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
                display_name = String.valueOf(user.get("display_name"));
                about = String.valueOf(user.get("about"));
                email = String.valueOf(user.get("email"));
                image_url_profile = String.valueOf(user.get("image_url_profile"));

                if(listener != null) {
                    listener.onDataChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Status", "Get User Profile Failed");
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

    public ArrayList<PlanToWatch> getList_Plan() {
        return list_plan;
    }

    public void setList_Plan(ArrayList<PlanToWatch> list_Plan) {
        this.list_plan = list_Plan;
    }

    public ArrayList<Watching> getList_watching() {
        return list_watching;
    }

    public void setList_watching(ArrayList<Watching> list_watching) {
        this.list_watching = list_watching;
    }

    public ArrayList<Completed> getList_completed() {
        return list_completed;
    }

    public void setList_completed(ArrayList<Completed> list_completed) {
        this.list_completed = list_completed;
    }

    public ArrayList<Dropped> getList_dropped() {
        return list_dropped;
    }

    public void setList_dropped(ArrayList<Dropped> list_dropped) {
        this.list_dropped = list_dropped;
    }
}

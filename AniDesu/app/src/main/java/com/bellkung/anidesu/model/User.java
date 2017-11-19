package com.bellkung.anidesu.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.bellkung.anidesu.model.list_anime.Completed;
import com.bellkung.anidesu.model.list_anime.Dropped;
import com.bellkung.anidesu.model.list_anime.PlanToWatch;
import com.bellkung.anidesu.model.list_anime.Watching;
import com.bellkung.anidesu.utils.KeyUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by BellKunG on 22/10/2017 AD.
 */

public class User implements Parcelable {

    public interface UserDataListener {
        void onDataChanged();
    }

    public interface MyAnimeListListener {
        void onMyAnimeListChanged();
    }

    private static User user = null;

    private String uid;
    private String display_name;
    private String email;
    private String about;
    private String image_url_profile;

    private ArrayList<MyAnimeList> list_plan;
    private ArrayList<MyAnimeList> list_watching;
    private ArrayList<MyAnimeList> list_completed;
    private ArrayList<MyAnimeList> list_dropped;

    private UserDataListener listener;
    private MyAnimeListListener myAnimeListListener;

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public User() {
    }

    public void fetchUserProfile() {
        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users/" + this.uid + "/profile");
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

    public void fetchMyAnimeList() {
        DatabaseReference mAnimeListRef = FirebaseDatabase.getInstance().getReference("users/" + this.uid + "/list_anime");
        mAnimeListRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list_plan = new ArrayList<>();
                list_watching = new ArrayList<>();
                list_completed = new ArrayList<>();
                list_dropped = new ArrayList<>();

                for (DataSnapshot parent: dataSnapshot.getChildren()) {
                    for (DataSnapshot child: parent.getChildren()) {
                        MyAnimeList myAnimeList = child.getValue(MyAnimeList.class);
                        setMyAnimeFormDB(parent.getKey(), myAnimeList);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void setMyAnimeFormDB(String status, MyAnimeList myAnimeList) {
        switch (status) {
            case "plan_to_watch":
                this.list_plan.add(myAnimeList);
                break;

            case "watching":
                this.list_watching.add(myAnimeList);
                break;

            case "completed":
                this.list_completed.add(myAnimeList);
                break;

            case "dropped":
                this.list_dropped.add(myAnimeList);
                break;

        }

    }

    public void saveToMyAnimeList(String status, MyAnimeList myAnime) {

        DatabaseReference mUserRef = FirebaseDatabase.getInstance()
                .getReference("users/" + this.uid + "/list_anime/" + status);
        mUserRef.push().setValue(myAnime);

        if (this.myAnimeListListener != null) {
            this.myAnimeListListener.onMyAnimeListChanged();
        }

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

    public ArrayList<MyAnimeList> getList_plan() {
        return list_plan;
    }

    public void setList_plan(ArrayList<MyAnimeList> list_plan) {
        this.list_plan = list_plan;
    }

    public ArrayList<MyAnimeList> getList_watching() {
        return list_watching;
    }

    public void setList_watching(ArrayList<MyAnimeList> list_watching) {
        this.list_watching = list_watching;
    }

    public ArrayList<MyAnimeList> getList_completed() {
        return list_completed;
    }

    public void setList_completed(ArrayList<MyAnimeList> list_completed) {
        this.list_completed = list_completed;
    }

    public ArrayList<MyAnimeList> getList_dropped() {
        return list_dropped;
    }

    public void setList_dropped(ArrayList<MyAnimeList> list_dropped) {
        this.list_dropped = list_dropped;
    }

    public void setListener(UserDataListener listener) {
        this.listener = listener;
    }

    public void setMyAnimeListListener(MyAnimeListListener myAnimeListListener) {
        this.myAnimeListListener = myAnimeListListener;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.display_name);
        dest.writeString(this.email);
        dest.writeString(this.about);
        dest.writeString(this.image_url_profile);
        dest.writeList(this.list_plan);
        dest.writeList(this.list_watching);
        dest.writeList(this.list_completed);
        dest.writeList(this.list_dropped);
        dest.writeParcelable((Parcelable) this.listener, flags);
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.display_name = in.readString();
        this.email = in.readString();
        this.about = in.readString();
        this.image_url_profile = in.readString();
        this.list_plan = new ArrayList<MyAnimeList>();
        in.readList(this.list_plan, MyAnimeList.class.getClassLoader());
        this.list_watching = new ArrayList<MyAnimeList>();
        in.readList(this.list_watching, MyAnimeList.class.getClassLoader());
        this.list_completed = new ArrayList<MyAnimeList>();
        in.readList(this.list_completed, MyAnimeList.class.getClassLoader());
        this.list_dropped = new ArrayList<MyAnimeList>();
        in.readList(this.list_dropped, MyAnimeList.class.getClassLoader());
        this.listener = in.readParcelable(UserDataListener.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

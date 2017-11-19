package com.bellkung.anidesu.model;

import android.os.Parcel;
import android.os.Parcelable;
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

    private static User user = null;

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

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public User() {
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

                HashMap<String, Object> list_anime = (HashMap<String, Object>) user.get("list_anime");
                Iterator it = list_anime.entrySet().iterator();

                list_plan = new ArrayList<>();
                list_watching = new ArrayList<>();
                list_completed = new ArrayList<>();
                list_dropped = new ArrayList<>();

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    getMyAnimeFormDB(String.valueOf(pair.getKey()), (ArrayList<HashMap<String, Long>>) pair.getValue());
                }

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

    private void getMyAnimeFormDB(String status, ArrayList<HashMap<String, Long>> myAnimeListMap) {

        for (HashMap<String, Long> myAnimeList: myAnimeListMap) {
//            Log.i("Status", "anime_id : " + myAnimeList.get("anime_id").intValue());
//            Log.i("Status", "progress : " + myAnimeList.get("progress").intValue());
//            Log.i("Status", "score : " + myAnimeList.get("score").intValue());
            switch (status) {
                case "plan_to_watch":
                    PlanToWatch planToWatch = new PlanToWatch();
                    planToWatch.setAnime_id(myAnimeList.get("anime_id").intValue());
                    planToWatch.setProgress(myAnimeList.get("progress").intValue());
                    planToWatch.setScore(myAnimeList.get("score").intValue());

                    this.list_plan.add(planToWatch);
                    break;

                case "watching":
                    Watching watching = new Watching();
                    watching.setAnime_id(myAnimeList.get("anime_id").intValue());
                    watching.setProgress(myAnimeList.get("progress").intValue());
                    watching.setScore(myAnimeList.get("score").intValue());

                    this.list_watching.add(watching);
                    break;

                case "completed":
                    Completed completed = new Completed();
                    completed.setAnime_id(myAnimeList.get("anime_id").intValue());
                    completed.setProgress(myAnimeList.get("progress").intValue());
                    completed.setScore(myAnimeList.get("score").intValue());

                    this.list_completed.add(completed);

                    break;

                case "dropped":
                    Dropped dropped = new Dropped();
                    dropped.setAnime_id(myAnimeList.get("anime_id").intValue());
                    dropped.setProgress(myAnimeList.get("progress").intValue());
                    dropped.setScore(myAnimeList.get("score").intValue());

                    this.list_dropped.add(dropped);
                    break;

            }
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
        this.list_plan = new ArrayList<PlanToWatch>();
        in.readList(this.list_plan, PlanToWatch.class.getClassLoader());
        this.list_watching = new ArrayList<Watching>();
        in.readList(this.list_watching, Watching.class.getClassLoader());
        this.list_completed = new ArrayList<Completed>();
        in.readList(this.list_completed, Completed.class.getClassLoader());
        this.list_dropped = new ArrayList<Dropped>();
        in.readList(this.list_dropped, Dropped.class.getClassLoader());
        this.listener = in.readParcelable(UserDataListener.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
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

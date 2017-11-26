package com.bellkung.anidesu.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BellKunG on 22/11/2017 AD.
 */

public class AnotherUser implements Parcelable {

    private String uid;
    private String display_name;
    private String about;
    private String email;
    private String image_url_profile;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url_profile() {
        return image_url_profile;
    }

    public void setImage_url_profile(String image_url_profile) {
        this.image_url_profile = image_url_profile;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.display_name);
        dest.writeString(this.about);
        dest.writeString(this.email);
        dest.writeString(this.image_url_profile);
    }

    public AnotherUser() {
    }

    protected AnotherUser(Parcel in) {
        this.uid = in.readString();
        this.display_name = in.readString();
        this.about = in.readString();
        this.email = in.readString();
        this.image_url_profile = in.readString();
    }

    public static final Parcelable.Creator<AnotherUser> CREATOR = new Parcelable.Creator<AnotherUser>() {
        @Override
        public AnotherUser createFromParcel(Parcel source) {
            return new AnotherUser(source);
        }

        @Override
        public AnotherUser[] newArray(int size) {
            return new AnotherUser[size];
        }
    };
}

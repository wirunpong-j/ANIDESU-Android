package com.bellkung.anidesu.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bellkung.anidesu.utils.FormatCustomManager;

/**
 * Created by BellKunG on 10/11/2017 AD.
 */

public class StaffSmall implements Parcelable {

    private int id;
    private String name_first;
    private String name_last;
    private String language;
    private String image_url_lge;
    private String image_url_med;
    private String role;

    public int getId() {
        return id;
    }

    public String getName_first() {
        return FormatCustomManager.parseString(name_first);
    }

    public String getName_last() {
        return FormatCustomManager.parseString(name_last);
    }

    public String getLanguage() {
        return FormatCustomManager.parseString(language);
    }

    public String getImage_url_lge() {
        return FormatCustomManager.parseString(image_url_lge);
    }

    public String getImage_url_med() {
        return FormatCustomManager.parseString(image_url_med);
    }

    public String getRole() {
        return FormatCustomManager.parseString(role);
    }

    public String getFull_Name() {
        return this.name_last + " " + this.name_last;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name_first);
        dest.writeString(this.name_last);
        dest.writeString(this.language);
        dest.writeString(this.image_url_lge);
        dest.writeString(this.image_url_med);
        dest.writeString(this.role);
    }

    public StaffSmall() {
    }

    protected StaffSmall(Parcel in) {
        this.id = in.readInt();
        this.name_first = in.readString();
        this.name_last = in.readString();
        this.language = in.readString();
        this.image_url_lge = in.readString();
        this.image_url_med = in.readString();
        this.role = in.readString();
    }

    public static final Parcelable.Creator<StaffSmall> CREATOR = new Parcelable.Creator<StaffSmall>() {
        @Override
        public StaffSmall createFromParcel(Parcel source) {
            return new StaffSmall(source);
        }

        @Override
        public StaffSmall[] newArray(int size) {
            return new StaffSmall[size];
        }
    };
}

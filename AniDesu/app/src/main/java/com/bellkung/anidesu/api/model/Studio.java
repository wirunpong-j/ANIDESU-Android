package com.bellkung.anidesu.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bellkung.anidesu.utils.FormatCustomManager;

/**
 * Created by BellKunG on 10/11/2017 AD.
 */

public class Studio implements Parcelable {

    private int id;
    private String studio_name;
    private String studio_wiki;

    public int getId() {
        return id;
    }

    public String getStudio_name() {
        return FormatCustomManager.parseString(studio_name);
    }

    public String getStudio_wiki() {
        return FormatCustomManager.parseString(studio_wiki);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.studio_name);
        dest.writeString(this.studio_wiki);
    }

    public Studio() {
    }

    protected Studio(Parcel in) {
        this.id = in.readInt();
        this.studio_name = in.readString();
        this.studio_wiki = in.readString();
    }

    public static final Parcelable.Creator<Studio> CREATOR = new Parcelable.Creator<Studio>() {
        @Override
        public Studio createFromParcel(Parcel source) {
            return new Studio(source);
        }

        @Override
        public Studio[] newArray(int size) {
            return new Studio[size];
        }
    };
}

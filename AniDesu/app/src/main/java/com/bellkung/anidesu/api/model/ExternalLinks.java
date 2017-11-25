package com.bellkung.anidesu.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bellkung.anidesu.utils.FormatCustomManager;

/**
 * Created by BellKunG on 10/11/2017 AD.
 */

public class ExternalLinks implements Parcelable {
    private int id;
    private String url;
    private String site;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return FormatCustomManager.parseString(url);
    }

    public String getSite() {
        return FormatCustomManager.parseString(site);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.url);
        dest.writeString(this.site);
    }

    public ExternalLinks() {
    }

    protected ExternalLinks(Parcel in) {
        this.id = in.readInt();
        this.url = in.readString();
        this.site = in.readString();
    }

    public static final Parcelable.Creator<ExternalLinks> CREATOR = new Parcelable.Creator<ExternalLinks>() {
        @Override
        public ExternalLinks createFromParcel(Parcel source) {
            return new ExternalLinks(source);
        }

        @Override
        public ExternalLinks[] newArray(int size) {
            return new ExternalLinks[size];
        }
    };
}

package com.bellkung.anidesu.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.bellkung.anidesu.utils.KeyUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BellKunG on 4/11/2017 AD.
 */

public class Series implements Parcelable {

    private int id;
    private String series_type;
    private String title_romaji;
    private String title_english;
    private String title_japanese;
    private String type;
    private String start_date;
    private String end_date;
    private int start_date_fuzzy;
    private int end_date_fuzzy;
    private int season;
    private String description;
    private ArrayList<String> synonyms;
    private ArrayList<String> genres;
    private boolean adult;
    private double average_score;
    private int popularity;
    private boolean favourite;
    private String image_url_sml;
    private String image_url_med;
    private String image_url_lge;
    private String image_url_banner;
    private int updated_at;
    private int total_episodes;
    private int duration;
    private String airing_status;
    private String youtube_id;
    private String hashtag;
    private String source;
    private Airing airing;
    private ArrayList<CharactersSmall> characters;
    private ArrayList<StaffSmall> staff;
    private ArrayList<Studio> studio;
    private ArrayList<ExternalLinks> external_links;

    public int getId() {
        return id;
    }

    public String getSeries_type() {
        return series_type;
    }

    public String getTitle_romaji() {
        return title_romaji;
    }

    public String getTitle_english() {
        return title_english;
    }

    public String getTitle_japanese() {
        return title_japanese;
    }

    public String getType() {
        return type;
    }

    public String getStart_date() {
        if (this.start_date == null) {
            return KeyUtils.NULL_TEXT;
        }
        return start_date;
    }

    public String getEnd_date() {
        if (this.end_date == null) {
            return KeyUtils.NULL_TEXT;
        }
        return end_date;
    }

    public int getStart_date_fuzzy() {
        return start_date_fuzzy;
    }

    public int getEnd_date_fuzzy() {
        return end_date_fuzzy;
    }

    public int getSeason() {
        return season;
    }

    public String getDescription() {
        if (this.description == null) {
            return KeyUtils.NULL_TEXT;
        }
        return description;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public boolean isAdult() {
        return adult;
    }

    public double getAverage_score() {
        return average_score;
    }

    public int getPopularity() {
        return popularity;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public String getImage_url_sml() {
        return image_url_sml;
    }

    public String getImage_url_med() {
        return image_url_med;
    }

    public String getImage_url_lge() {
        return image_url_lge;
    }

    public String getImage_url_banner() {
        if (this.image_url_banner == null) {
            return this.image_url_lge;
        }
        return image_url_banner;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public int getTotal_episodes() {
        return total_episodes;
    }

    public int getDuration() {
        return duration;
    }

    public String getAiring_status() {
        if (this.airing_status == null) {
            return KeyUtils.NULL_TEXT;
        }
        return airing_status.toUpperCase();
    }

    public String getYoutube_id() {
        if (this.youtube_id == null) {
            return KeyUtils.NULL_TEXT;
        }
        return youtube_id;
    }

    public String getHashtag() {
        if (this.hashtag == null) {
            return KeyUtils.NULL_TEXT;
        }
        return hashtag;
    }

    public String getSource() {
        if (this.source == null) {
            return KeyUtils.NULL_TEXT;
        }
        return source;
    }

    public Airing getAiring() {
        if (this.airing == null) {
            return new Airing();
        }
        return airing;
    }

    public ArrayList<CharactersSmall> getCharacters() {
        if (characters.isEmpty()) {
            ArrayList<CharactersSmall> newCharacter = new ArrayList<>();
            newCharacter.add(new CharactersSmall());
            return newCharacter;
        }
        return characters;
    }

    public ArrayList<StaffSmall> getStaff() {
        if (staff.isEmpty()) {
            ArrayList<StaffSmall> newStaff = new ArrayList<>();
            newStaff.add(new StaffSmall());
            return newStaff;
        }
        return staff;
    }

    public ArrayList<ExternalLinks> getExternal_links() {
        if (external_links.isEmpty()) {
            ArrayList<ExternalLinks> externalLinks = new ArrayList<>();
            externalLinks.add(new ExternalLinks());
            return externalLinks;
        }
        return external_links;
    }

    public ArrayList<Studio> getStudio() {
        if (studio.isEmpty()) {
            ArrayList<Studio> newStudio = new ArrayList<>();
            newStudio.add(new Studio());
            return newStudio;
        }
        return studio;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.series_type);
        dest.writeString(this.title_romaji);
        dest.writeString(this.title_english);
        dest.writeString(this.title_japanese);
        dest.writeString(this.type);
        dest.writeString(this.start_date);
        dest.writeString(this.end_date);
        dest.writeInt(this.start_date_fuzzy);
        dest.writeInt(this.end_date_fuzzy);
        dest.writeInt(this.season);
        dest.writeString(this.description);
        dest.writeStringList(this.synonyms);
        dest.writeStringList(this.genres);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.average_score);
        dest.writeInt(this.popularity);
        dest.writeByte(this.favourite ? (byte) 1 : (byte) 0);
        dest.writeString(this.image_url_sml);
        dest.writeString(this.image_url_med);
        dest.writeString(this.image_url_lge);
        dest.writeString(this.image_url_banner);
        dest.writeInt(this.updated_at);
        dest.writeInt(this.total_episodes);
        dest.writeInt(this.duration);
        dest.writeString(this.airing_status);
        dest.writeString(this.youtube_id);
        dest.writeString(this.hashtag);
        dest.writeString(this.source);
        dest.writeParcelable(this.airing, flags);
        dest.writeTypedList(this.characters);
        dest.writeTypedList(this.staff);
        dest.writeTypedList(this.studio);
        dest.writeTypedList(this.external_links);
    }

    public Series() {
    }

    protected Series(Parcel in) {
        this.id = in.readInt();
        this.series_type = in.readString();
        this.title_romaji = in.readString();
        this.title_english = in.readString();
        this.title_japanese = in.readString();
        this.type = in.readString();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.start_date_fuzzy = in.readInt();
        this.end_date_fuzzy = in.readInt();
        this.season = in.readInt();
        this.description = in.readString();
        this.synonyms = in.createStringArrayList();
        this.genres = in.createStringArrayList();
        this.adult = in.readByte() != 0;
        this.average_score = in.readDouble();
        this.popularity = in.readInt();
        this.favourite = in.readByte() != 0;
        this.image_url_sml = in.readString();
        this.image_url_med = in.readString();
        this.image_url_lge = in.readString();
        this.image_url_banner = in.readString();
        this.updated_at = in.readInt();
        this.total_episodes = in.readInt();
        this.duration = in.readInt();
        this.airing_status = in.readString();
        this.youtube_id = in.readString();
        this.hashtag = in.readString();
        this.source = in.readString();
        this.airing = in.readParcelable(Airing.class.getClassLoader());
        this.characters = in.createTypedArrayList(CharactersSmall.CREATOR);
        this.staff = in.createTypedArrayList(StaffSmall.CREATOR);
        this.studio = in.createTypedArrayList(Studio.CREATOR);
        this.external_links = in.createTypedArrayList(ExternalLinks.CREATOR);
    }

    public static final Creator<Series> CREATOR = new Creator<Series>() {
        @Override
        public Series createFromParcel(Parcel source) {
            return new Series(source);
        }

        @Override
        public Series[] newArray(int size) {
            return new Series[size];
        }
    };
}
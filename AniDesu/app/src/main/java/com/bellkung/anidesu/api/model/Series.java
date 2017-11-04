package com.bellkung.anidesu.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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
    private ArrayList<Integer> score_distribution;
    private ArrayList<Integer> list_stats;

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
        return start_date;
    }

    public String getEnd_date() {
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
        return image_url_banner;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public ArrayList<Integer> getScore_distribution() {
        return score_distribution;
    }

    public ArrayList<Integer> getList_stats() {
        return list_stats;
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
        dest.writeList(this.score_distribution);
        dest.writeList(this.list_stats);
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
        this.score_distribution = new ArrayList<Integer>();
        in.readList(this.score_distribution, Integer.class.getClassLoader());
        this.list_stats = new ArrayList<Integer>();
        in.readList(this.list_stats, Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Series> CREATOR = new Parcelable.Creator<Series>() {
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

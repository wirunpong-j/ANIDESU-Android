package com.bellkung.anidesu.model;

import java.util.ArrayList;

/**
 * Created by BellKunG on 22/10/2017 AD.
 */

public class User {
    private int id;
    private String display_name;
    private int anime_time;
    private int manga_chap;
    private String about;
    private int list_order;
    private boolean adult_content;
    private boolean following;
    private String image_url_lge;
    private String image_url_med;
    private String image_url_banner;
    private String title_language;
    private int score_type;
    private ArrayList<String> custom_list_anime;
    private ArrayList<String> custom_list_manga;
    private boolean advanced_rating;
    private ArrayList<String> advanced_rating_names;
    private int notifications;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public int getAnime_time() {
        return anime_time;
    }

    public void setAnime_time(int anime_time) {
        this.anime_time = anime_time;
    }

    public int getManga_chap() {
        return manga_chap;
    }

    public void setManga_chap(int manga_chap) {
        this.manga_chap = manga_chap;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getList_order() {
        return list_order;
    }

    public void setList_order(int list_order) {
        this.list_order = list_order;
    }

    public boolean isAdult_content() {
        return adult_content;
    }

    public void setAdult_content(boolean adult_content) {
        this.adult_content = adult_content;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public String getImage_url_lge() {
        return image_url_lge;
    }

    public void setImage_url_lge(String image_url_lge) {
        this.image_url_lge = image_url_lge;
    }

    public String getImage_url_med() {
        return image_url_med;
    }

    public void setImage_url_med(String image_url_med) {
        this.image_url_med = image_url_med;
    }

    public String getImage_url_banner() {
        return image_url_banner;
    }

    public void setImage_url_banner(String image_url_banner) {
        this.image_url_banner = image_url_banner;
    }

    public String getTitle_language() {
        return title_language;
    }

    public void setTitle_language(String title_language) {
        this.title_language = title_language;
    }

    public int getScore_type() {
        return score_type;
    }

    public void setScore_type(int score_type) {
        this.score_type = score_type;
    }

    public ArrayList<String> getCustom_list_anime() {
        return custom_list_anime;
    }

    public void setCustom_list_anime(ArrayList<String> custom_list_anime) {
        this.custom_list_anime = custom_list_anime;
    }

    public ArrayList<String> getCustom_list_manga() {
        return custom_list_manga;
    }

    public void setCustom_list_manga(ArrayList<String> custom_list_manga) {
        this.custom_list_manga = custom_list_manga;
    }

    public boolean isAdvanced_rating() {
        return advanced_rating;
    }

    public void setAdvanced_rating(boolean advanced_rating) {
        this.advanced_rating = advanced_rating;
    }

    public ArrayList<String> getAdvanced_rating_names() {
        return advanced_rating_names;
    }

    public void setAdvanced_rating_names(ArrayList<String> advanced_rating_names) {
        this.advanced_rating_names = advanced_rating_names;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }
}

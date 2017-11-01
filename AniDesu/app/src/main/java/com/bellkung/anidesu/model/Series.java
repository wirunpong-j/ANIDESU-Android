package com.bellkung.anidesu.model;

import java.util.ArrayList;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Series {
    private String anime_id;
    private ArrayList<Reviews> list_reviews;

    public Series(String anime_id) {
        this.anime_id = anime_id;
    }

    public String getAnime_id() {
        return anime_id;
    }

    public void setAnime_id(String anime_id) {
        this.anime_id = anime_id;
    }

    public ArrayList<Reviews> getList_reviews() {
        return list_reviews;
    }

    public void setList_reviews(ArrayList<Reviews> list_reviews) {
        this.list_reviews = list_reviews;
    }
}

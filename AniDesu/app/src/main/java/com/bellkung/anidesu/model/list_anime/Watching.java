package com.bellkung.anidesu.model.list_anime;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Watching {

    private String anime_id;
    private int progress;

    public Watching(String anime_id) {
        this.anime_id = anime_id;
    }

    public String getAnime_id() {
        return anime_id;
    }

    public void setAnime_id(String anime_id) {
        this.anime_id = anime_id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

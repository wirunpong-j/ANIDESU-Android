package com.bellkung.anidesu.model.list_anime;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class PlanToWatch {
    private int anime_id;
    private int progress;
    private int score;

    public int getAnime_id() {
        return anime_id;
    }

    public void setAnime_id(int anime_id) {
        this.anime_id = anime_id;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

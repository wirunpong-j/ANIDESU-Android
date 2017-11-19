package com.bellkung.anidesu.model.list_anime;

/**
 * Created by BellKunG on 31/10/2017 AD.
 */

public class Dropped {
    private int anime_id;
    private int score;
    private int progress;
    private String note;

    public int getAnime_id() {
        return anime_id;
    }

    public void setAnime_id(int anime_id) {
        this.anime_id = anime_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

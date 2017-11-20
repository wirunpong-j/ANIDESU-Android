package com.bellkung.anidesu.utils;

import com.bellkung.anidesu.R;

/**
 * Created by BellKunG on 5/11/2017 AD.
 */

public interface KeyUtils {

    // AnimeList: fetchSeriesPages
    String ANIME_TYPE = "anime";
    int YEAR = 2017;
    boolean FULL_PAGE = true;
    boolean AIRING_DATA = true;
    String[] SEASON = {"WINTER", "SPRING", "SUMMER", "FALL"};
    String SEASON_TEXT = "SEASON";

    // AnimeList: fetchSeriesOverviews
    String[] OVERVIEWS = {"INFO", "STATS", "EPISODES", "EXTRAS", "REVIEWS"};

    // AnimeList - Extra
    String GET_CHARACTERS = "GET_CHARACTER";
    String GET_STAFFS = "GET_STAFFS";

    // Put Extra KEY
    String KEY_SERIES = "SERIES";

    // Null
    String NULL_TEXT = "N/A";

    // AnimeList: Add to my anime list.
    String SAVE_BTN = "Save";
    String CANCEL_BTN = "Cancel";
    String[] STATUS_ARRAY = {"Plan To Watch", "Watching", "Completed", "Dropped"};
    Integer[] SCORE_ARRAY = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    String STATUS_PLAN_TO_WATCH = "Plan To Watch";
    String STATUS_WATCHING = "Watching";
    String STATUS_COMPLETED = "Completed";
    String STATUS_DROPPED = "Dropped";
    String TAG_DIALOG_ADD = "ADD LIST DIALOG";
    String TAG_DIALOG_EDIT = "EDIT LIST DIALOG";
    String KEY_GET_MY_ANIME = "KEY_GET_MY_ANIME";

    // MyAnimeList : get my anime list
    String KEY_ANIME_STATUS = "KEY_ANIME_STATUS";
    String[] MY_ANIME_LIST_PATH = {"plan_to_watch", "watching", "completed", "dropped"};

    // BoomMenuButton
    int[] BMB_DRAWABLE = {R.drawable.ic_add, R.drawable.ic_share};
    int[] BMB_DRAWABLE_EDIT = {R.drawable.ic_edit, R.drawable.ic_share};
    String KEY_BMB_STATUS = "BMB_STATUS";
    String BMB_STATUS_ADD = "ADD";
    String BMB_STATUS_EDIT = "EDIT";
    String[] BMB_TEXT = {"ADD", "SHARE"};
    String[] BMB_EDIT_TEXT = {"EDIT", "SHARE"};
    int BMB_ADD = 0;
    int BMB_EDIT = 0;
    int BMB_SHARE = 1;

    // ETC
    String DELIMITER = ", ";
    int NUM_DEFAULT = 0;
    String EMPTY_STR = "";

}

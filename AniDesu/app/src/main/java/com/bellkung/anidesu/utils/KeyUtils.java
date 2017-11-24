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
    String[] OVERVIEWS = {"INFO", "STATS", "EXTRAS", "REVIEWS"};

    // AnimeList - Extra
    String GET_CHARACTERS = "GET_CHARACTER";
    String GET_STAFFS = "GET_STAFFS";

    // Put Extra KEY
    String KEY_SERIES = "SERIES";

    // AnimeList: Add to my anime list.
    String SAVE_BTN = "Save";
    String CANCEL_BTN = "Cancel";
    String[] STATUS_ARRAY = {"Plan To Watch", "Watching", "Completed", "Dropped"};
    Integer[] SCORE_ARRAY = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    String STATUS_PLAN_TO_WATCH = "plan_to_watch";
    String STATUS_WATCHING = "watching";
    String STATUS_COMPLETED = "completed";
    String STATUS_DROPPED = "dropped";
    String KEY_GET_MY_ANIME = "KEY_GET_MY_ANIME";

    // Status post and Comment
    String BMB_POST = "CREATE POST";
    String COMMENT_POST = "COMMENT_POST";
    String COMMENT_USER = "COMMENT_USER";

    // MyAnimeList : get my anime list
    String KEY_ANIME_STATUS = "KEY_ANIME_STATUS";
    String[] MY_ANIME_LIST_PATH = {"plan_to_watch", "watching", "completed", "dropped"};

    // BoomMenuButton
    int[] BMB_DRAWABLE = {R.drawable.ic_add, R.drawable.ic_star, R.drawable.ic_share};
    int[] BMB_DRAWABLE_EDIT = {R.drawable.ic_edit, R.drawable.ic_star, R.drawable.ic_share};
    String KEY_BMB_STATUS = "BMB_STATUS";
    String BMB_STATUS_ADD = "ADD";
    String BMB_STATUS_EDIT = "EDIT";
    String[] BMB_TEXT = {"ADD", "REVIEW", "SHARE"};
    String[] BMB_EDIT_TEXT = {"EDIT", "REVIEW", "SHARE"};
    int BMB_START = 0;
    int BMB_REVIEW = 1;
    int BMB_SHARE = 2;

    // ETC
    String DELIMITER = ", ";
    int NUM_DEFAULT = 0;
    String EMPTY_STR = "";

    // Null
    String NULL_TEXT = "N/A";

}

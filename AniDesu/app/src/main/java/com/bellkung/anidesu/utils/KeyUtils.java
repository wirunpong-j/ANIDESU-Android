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

    // MyAnimeList : get my anime list
    String STATUS = "STATUS";
    String USER = "USER";

    // BoomMenuButton
    int[] BMB_DRAWABLE = {R.drawable.ic_add, R.drawable.ic_share};
    String[] BMB_TEXT = {"ADD", "SHARE"};
    int BMB_ADD = 0;
    int BMB_SHARE = 1;
    String TAG_DIALOG = "ADD LIST DIALOG";

    // ETC
    String DELIMITER = ", ";
    int NUM_DEFAULT = 0;
    String EMPTY_STR = "";

}

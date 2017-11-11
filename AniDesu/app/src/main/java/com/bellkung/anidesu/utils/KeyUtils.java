package com.bellkung.anidesu.utils;

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

    // ETC
    String DELIMITER = ", ";
    int NUM_DEFAULT = 0;
}

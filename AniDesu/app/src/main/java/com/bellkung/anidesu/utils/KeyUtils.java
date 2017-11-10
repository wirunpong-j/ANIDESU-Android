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
    int ANIME_SEASON_ROW = 2, ANIME_LIST_PAGER = 4;

    // AnimeList: fetchSeriesOverviews
    String[] OVERVIEWS = {"INFO", "STATS", "EPISODES", "EXTRAS", "REVIEWS"};
    int ANIME_OVERVIEWS_PAGER = 5;

    // Put Extra KEY
    String KEY_SERIES = "SERIES";

    // Null
    String NULL_TEXT = "N/A";
    int NULL_INT = -1;

    // ETC
    String DELIMITER = ", ";
    int NUM_DEFAULT = 0;
    int LINKS_ROW = 1;
    String PEOPLE = " People";
}

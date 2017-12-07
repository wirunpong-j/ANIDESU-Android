package com.bellkung.anidesu;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bellkung.anidesu.controller.HomeActivity;
import com.bellkung.anidesu.controller.RecyclerViewItemCountAssertion;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by BellKunG on 8/12/2017 AD.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AnimeSearchActivityUITest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void homeActivityTest() throws InterruptedException {
        Thread.sleep(8000);
    }

    @Test
    public void searchAnimeWithBlank() throws InterruptedException {
        onView(withId(R.id.searchBar)).perform(click());
        onView(withId(R.id.mt_editText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.mt_editText)).perform(pressImeActionButton());
        onView(withId(R.id.no_data_text)).check(matches(withText("No Data to Display")));
        Thread.sleep(2000);
    }

    @Test
    public void searchAnimeWithMissingName() throws InterruptedException {
        onView(withId(R.id.searchBar)).perform(click());
        onView(withId(R.id.mt_editText)).perform(typeText("asdsadassdd"), closeSoftKeyboard());
        onView(withId(R.id.mt_editText)).perform(pressImeActionButton());
        onView(withId(R.id.no_data_text)).check(matches(withText("No Data to Display")));
        Thread.sleep(2000);
    }

    @Test
    public void searchAnimeWithSimilarName() throws InterruptedException {
        onView(withId(R.id.searchBar)).perform(click());
        onView(withId(R.id.mt_editText)).perform(typeText("dangan"), closeSoftKeyboard());
        onView(withId(R.id.mt_editText)).perform(pressImeActionButton());
        Thread.sleep(2000);
        onView(withId(R.id.anime_list_search_recycleView)).check(new RecyclerViewItemCountAssertion(5));
        Thread.sleep(2000);
    }
}

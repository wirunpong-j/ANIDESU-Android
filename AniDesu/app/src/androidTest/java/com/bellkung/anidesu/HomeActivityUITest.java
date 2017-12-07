package com.bellkung.anidesu;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.bellkung.anidesu.controller.HomeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by BellKunG on 7/12/2017 AD.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeActivityUITest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void homeActivityTest() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Test
    public void navigationBarPressed() throws InterruptedException {
        onView(withId(R.id.mt_nav)).perform(click());
    }

    @Test
    public void navHomePressed() {
        onNavigationItemSelected(1);
    }

    @Test
    public void navDiscoverAnimePressed() {
        onNavigationItemSelected(2);
    }

    @Test
    public void navMyAnimeListPressed() {
        onNavigationItemSelected(3);
    }

    @Test
    public void navAnimeReviewPressed() {
        onNavigationItemSelected(4);
    }

    @Test
    public void navLogoutPressed() throws InterruptedException {
        onNavigationItemSelected(7);
        Thread.sleep(1000);
    }

    private void onNavigationItemSelected(int navItem) {
        onView(allOf(withId(R.id.mt_nav),
                childAtPosition(allOf(withId(R.id.root), childAtPosition(withId(R.id.mt_container), 0)),
                        1), isDisplayed())).perform(click());
        onView(allOf(childAtPosition(allOf(withId(R.id.design_navigation_view), childAtPosition(withId(R.id.nav_view), 0)),
                navItem), isDisplayed())).perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}

package com.bellkung.anidesu;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.bellkung.anidesu.controller.HomeActivity;
import com.bellkung.anidesu.controller.RecyclerViewMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by BellKunG on 8/12/2017 AD.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AnimeReviewUITest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void homeActivityTest() throws InterruptedException {
        Thread.sleep(8000);
        onView(withId(R.id.mt_nav)).perform(click());
        onView(childAtPosition(withId(R.id.design_navigation_view), 4)).perform(click());
        Thread.sleep(3500);
    }

    @Test
    public void scrollView() {
        onView(withId(R.id.review_recycleView)).perform(swipeUp());
    }

    @Test
    public void showHoverViewOfReview() throws InterruptedException {
        onView(withRecyclerView(R.id.review_recycleView).atPositionOnView(0, R.id.review_imageView)).perform(click());
        Thread.sleep(1000);
    }

    @Test
    public void showReviewFormUser() throws InterruptedException {
        onView(withRecyclerView(R.id.review_recycleView).atPositionOnView(0, R.id.review_imageView)).perform(click());
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.review_recycleView).atPositionOnView(0, R.id.readMoreBtn)).perform(click());
        Thread.sleep(1000);
    }

    private static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
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

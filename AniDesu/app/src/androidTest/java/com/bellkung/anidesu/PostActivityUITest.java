package com.bellkung.anidesu;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.bellkung.anidesu.controller.HomeActivity;
import com.bellkung.anidesu.controller.PostActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by BellKunG on 7/12/2017 AD.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PostActivityUITest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void postActivityTest() throws InterruptedException {
        Thread.sleep(8000);
    }

    @Test
    public void postBoomMenuButtonPressed() throws InterruptedException {
        onView(allOf(withId(R.id.button), childAtPosition(allOf(withId(R.id.shadow), childAtPosition(withId(R.id.bmb_posts), 0)),
                                0), isDisplayed())).perform(click());
        Thread.sleep(1000);
        onView(allOf(withId(R.id.button),childAtPosition(allOf(withId(R.id.shadow), childAtPosition(withId(R.id.layout), 0)),
                                0), isDisplayed())).perform(click());
        Thread.sleep(1000);
    }

    @Test
    public void postStatus() throws InterruptedException {
        onView(allOf(withId(R.id.button), childAtPosition(allOf(withId(R.id.shadow), childAtPosition(withId(R.id.bmb_posts), 0)),
                0), isDisplayed())).perform(click());
        Thread.sleep(1000);

        onView(allOf(withId(R.id.button), childAtPosition(allOf(withId(R.id.shadow), childAtPosition(withId(R.id.layout), 0)),
                0), isDisplayed())).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.post_editText)).perform(typeText("AniDesu"));
        onView(withId(R.id.postBtn)).perform(click());

        Thread.sleep(1000);
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

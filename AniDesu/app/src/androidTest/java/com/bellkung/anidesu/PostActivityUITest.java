package com.bellkung.anidesu;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bellkung.anidesu.controller.HomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
        boomMenuButtonPressed();
    }

    @Test
    public void postStatus() throws InterruptedException {
        boomMenuButtonPressed();

        onView(withId(R.id.post_editText)).perform(typeText("AniDesu"));
        onView(withId(R.id.postBtn)).perform(click());

        Thread.sleep(1000);
    }

    private void boomMenuButtonPressed() throws InterruptedException {
        onView(withId(R.id.bmb_posts)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.layout)).perform(click());
        Thread.sleep(1000);
    }

}

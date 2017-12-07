package com.bellkung.anidesu;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bellkung.anidesu.controller.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by BellKunG on 7/12/2017 AD.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void mainActivityTest() throws InterruptedException {
        Thread.sleep(3000);
    }

    @Test
    public void loginBtnPressed() throws InterruptedException {
        onView(withId(R.id.login_button)).perform(click());
        Thread.sleep(3000);
    }

}

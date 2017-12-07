package com.bellkung.anidesu;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bellkung.anidesu.controller.HomeActivity;
import com.bellkung.anidesu.controller.RecyclerViewMatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by BellKunG on 8/12/2017 AD.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CommentPostActivityUITest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void commentActivityTest() throws InterruptedException {
        Thread.sleep(8000);
    }

    @Test
    public void commentBtnPressed() {
        onView(withRecyclerView(R.id.posts_recycleView).atPositionOnView(0, R.id.commentBtn)).perform(click());
    }

    @Test
    public void commentThisPost() throws InterruptedException {
        onView(withRecyclerView(R.id.posts_recycleView).atPositionOnView(0, R.id.commentBtn)).perform(click());
        onView(withId(R.id.comment_editText)).perform(typeText("What the heck!!!"));
        onView(withId(R.id.commentaryBtn)).perform(click());
        Thread.sleep(1000);

        onView(withRecyclerView(R.id.comment_list_recycleView).atPositionOnView(
                withRecyclerView(R.id.comment_list_recycleView).getRecyclerViewCount(), R.id.comment_textView))
                .check(matches(withText("What the heck!!!")));
        Thread.sleep(1000);
    }

    private static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}

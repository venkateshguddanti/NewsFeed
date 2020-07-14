package com.venkat.newsfeed.ui.main

import android.widget.FrameLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.venkat.newsfeed.R
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FactsFragmentTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)
    private lateinit var mainActivity : MainActivity
    private lateinit var  container : FrameLayout

    @Before
    fun setUp() {
        mainActivity = activityTestRule.activity
        container = mainActivity.container
    }
    @Test
    fun test_main_activity()
    {
        Assert.assertNotNull(mainActivity)
    }
    @Test
    fun test_progressbar()
    {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }
    @Test
    fun test_perform_swipe_to_refresh()
    {
        onView(withId(R.id.refresh)).perform(swipeDown())
    }
    @Test
    fun test_recycler_view_items()
    {
        onView(withId(R.id.factRowsList)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
    }

}
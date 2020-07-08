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
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)
    private lateinit var mainActivity : MainActivity
    private val factsFragment = FactsFragment()
    private lateinit var  container : FrameLayout

    @Before
    fun setUp() {
         mainActivity = activityTestRule.activity
        container = mainActivity.container
    }

    @Test
    fun testLaunchFragment()
    {
        mainActivity.supportFragmentManager.beginTransaction().add(container.id,factsFragment)

    }
    @Test
    fun test_perform_swipe_to_refresh()
    {
        mainActivity.supportFragmentManager.beginTransaction().add(container.id,factsFragment)
        onView(withId(R.id.refresh)).perform(swipeDown())
    }
    @Test
    fun test_recycler_view_items()
    {
        mainActivity.supportFragmentManager.beginTransaction().add(container.id,factsFragment)
        onView(withId(R.id.factRowsList)).check(matches(isDisplayed()))
    }
    @After
    fun tearDown() {

    }
}
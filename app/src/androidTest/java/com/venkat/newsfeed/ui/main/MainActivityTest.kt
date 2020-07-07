package com.venkat.newsfeed.ui.main
import androidx.test.espresso.Espresso.onView
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
    lateinit var mainActivity : MainActivity
    val factsFragment = FactsFragment()

    @Before
    fun setUp() {
         mainActivity = activityTestRule.activity
    }

    @Test
    fun testLaunchFragment()
    {
        val container = mainActivity.container
        mainActivity.supportFragmentManager.beginTransaction().add(container.id,factsFragment)

    }

    @Test
    fun testFragment()
    {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

    }
    @After
    fun tearDown() {

    }
}
package com.venkat.newsfeed.ui.main

import android.widget.FrameLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)
    private lateinit var mainActivity: MainActivity
    private val factsFragment = FactsFragment()
    private lateinit var container: FrameLayout

    @Before
    fun setUp() {
        mainActivity = activityTestRule.activity
        container = mainActivity.container
    }

    @Test
    fun testLaunchFragment() {
        mainActivity.supportFragmentManager.beginTransaction().add(container.id, factsFragment)

    }

    @After
    fun tearDown() {
    }
}
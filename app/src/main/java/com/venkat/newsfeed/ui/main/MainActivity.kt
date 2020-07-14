package com.venkat.newsfeed.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.venkat.newsfeed.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment =
            supportFragmentManager.findFragmentByTag(FactsFragment::class.java.simpleName)
        if (fragment == null) supportFragmentManager.beginTransaction().replace(
            R.id.container,
            FactsFragment(),
            FactsFragment::class.java.simpleName
        ).commit()
    }
}

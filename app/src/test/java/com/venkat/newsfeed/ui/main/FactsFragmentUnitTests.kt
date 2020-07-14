package com.venkat.newsfeed.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.venkat.newsfeed.ui.main.adapter.FactsAdapter
import org.junit.After
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FactsFragmentUnitTests {

    @Mock
    private lateinit var factsAdapter: FactsAdapter

    @Mock
    private lateinit var recyclerView: RecyclerView

    @Before
    fun setUp() {
    }

    @Test
    fun recycler_item_count() {
        assertNotNull(recyclerView)
        assertTrue(factsAdapter.itemCount == 0)
        assertFalse(factsAdapter.itemCount == 1)
    }

    @Test
    fun recyclerview_displays_data() {
        `when`(factsAdapter.hasStableIds()).thenReturn(true)
    }

    @After
    fun tearDown() {
    }
}
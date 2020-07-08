package com.venkat.newsfeed.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.venkat.newsfeed.data.api.ApiHelper
import com.venkat.newsfeed.data.model.Facts
import com.venkat.newsfeed.data.model.Rows
import com.venkat.newsfeed.db.DbHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    //It is needed to test code with LiveData. If we do not use this, we will get the RuntimeException related to Looper in Android.
    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    private val testDispatcher = TestCoroutineRule()
    @Mock
    private lateinit var apiHelper: ApiHelper
    @Mock
    private lateinit var dbHelper: DbHelper


    @Before
    fun setUp() {

    }
    @Test
    fun givenServerResponse_success_facts_should_return_data() {
        val facts = mock(Facts::class.java)
        testDispatcher.runBlockingTest {
            `when`(apiHelper.getFacts()).thenReturn(facts)
            Assert.assertEquals(apiHelper.getFacts(),facts)
            verify(apiHelper).getFacts()
        }
    }
    @Test
    fun givenDbRequest_execute_should_return_data() {
        val newsFacts = emptyList<Rows>()
        testDispatcher.runBlockingTest {
            `when`(dbHelper.getAllFacts()).thenReturn(newsFacts)
             Assert.assertEquals(dbHelper.getAllFacts(),newsFacts)
             verify(dbHelper).getAllFacts()
        }
    }

    @After
    fun tearDown() {

    }

}
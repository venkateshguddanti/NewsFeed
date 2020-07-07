package com.venkat.newsfeed.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.venkat.newsfeed.data.model.Facts
import com.venkat.newsfeed.data.model.Resource
import com.venkat.newsfeed.data.repository.MainRepository
import com.venkat.newsfeed.db.NewsFact
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
    private lateinit var mainRepository: MainRepository
    @Mock
    private lateinit var apiObserver: Observer<Resource<Facts>>

    @Before
    fun setUp() {

    }
    @Test
    fun givenServerResponse_success_facts_should_return_data() {
        val facts = mock(Facts::class.java)
        testDispatcher.runBlockingTest {
            `when`(mainRepository.getFacts()).thenReturn(facts)
            Assert.assertEquals(mainRepository.getFacts(),facts)
            verify(mainRepository).getFacts()
        }
    }
    @Test
    fun givenDbRequest_execute_should_return_data() {
        val newsFacts = emptyList<NewsFact>()
        testDispatcher.runBlockingTest {
            `when`(mainRepository.getAllFacts()).thenReturn(newsFacts)
             Assert.assertEquals(mainRepository.getAllFacts(),newsFacts)
             verify(mainRepository).getAllFacts()
        }
    }

    @After
    fun tearDown() {

    }

}
package com.venkat.newsfeed.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.venkat.newsfeed.data.model.Facts
import com.venkat.newsfeed.data.model.Resource
import com.venkat.newsfeed.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    //It is needed to test code with LiveData. If we do not use this, we will get the RuntimeException related to Looper in Android.
    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    @Mock
    private lateinit var mainRepository: MainRepository
    @Mock
    private lateinit var apiObserver: Observer<Resource<Facts>>

    @Before
    fun setUp() {
       Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun givenServerResponse_200_facts_should_return_success() {
        val facts = Facts("title", arrayListOf())
        testDispatcher.runBlockingTest {
            doReturn(facts)
                .`when`(mainRepository)
                .getFacts()
            val viewModel = MainViewModel(mainRepository)
            viewModel.facts.observeForever(apiObserver)
             verify(mainRepository).getFacts()
            verify(apiObserver).onChanged(
                Resource.success(facts)
            )
            viewModel.facts.removeObserver(apiObserver)
        }
    }
    @Test
    fun givenServerResponse_other_than_200_facts_should_return_errorMsg(){
        testDispatcher.runBlockingTest {
            val errorMsg = "Network Error!"
            doThrow(RuntimeException(errorMsg))
                .`when`(mainRepository)
                .getFacts()
            val viewModel = MainViewModel(mainRepository)
            viewModel.facts.observeForever(apiObserver)
            verify(mainRepository).getFacts()
            verify(apiObserver).onChanged(
                Resource.error(null,errorMsg)
            )
            viewModel.facts.removeObserver(apiObserver)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}
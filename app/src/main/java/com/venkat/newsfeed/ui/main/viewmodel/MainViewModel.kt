package com.venkat.newsfeed.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkat.newsfeed.data.model.Facts
import com.venkat.newsfeed.data.model.Resource
import com.venkat.newsfeed.data.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    var facts = MutableLiveData<Resource<Facts>>()

    init {
        getFacts()
    }

    fun getFacts() {
        viewModelScope.launch {
            repository.getFacts(facts)

        }
    }

}
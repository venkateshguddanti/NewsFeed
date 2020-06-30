package com.venkat.newsfeed.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.venkat.newsfeed.data.model.Resource
import com.venkat.newsfeed.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val repository: MainRepository): ViewModel() {


    fun getFacts() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getFacts()))
        }catch (exception : Exception)
        {
            emit(Resource.error(data = null,message = exception.message ?: "Network ERROR!!"))
        }
    }

}
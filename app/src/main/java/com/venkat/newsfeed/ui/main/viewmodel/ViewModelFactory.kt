package com.venkat.newsfeed.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.venkat.newsfeed.data.api.ApiHelper
import com.venkat.newsfeed.data.repository.MainRepository
import com.venkat.newsfeed.db.DbHelper
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper,private val dbHelper: DbHelper):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java))
        {
            return MainViewModel(MainRepository(apiHelper,dbHelper)) as T
        }
        throw IllegalArgumentException("Class not implemented")
    }
}
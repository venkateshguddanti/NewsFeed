package com.venkat.newsfeed.data.repository

import com.venkat.newsfeed.data.api.ApiHelper
import com.venkat.newsfeed.db.DbHelper
import com.venkat.newsfeed.db.NewsFact

open class MainRepository(private val apiHelper: ApiHelper,private val dbHelper: DbHelper) {

    suspend fun getFacts() = apiHelper.getFacts()
    suspend fun getAllFacts() = dbHelper.getAllFacts()
    suspend fun insertFacts(facts : List<NewsFact>) {
        dbHelper.deleteAll()
        dbHelper.insertFacts(facts)
    }
}
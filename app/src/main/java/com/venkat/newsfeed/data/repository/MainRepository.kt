package com.venkat.newsfeed.data.repository

import com.venkat.newsfeed.data.api.ApiHelper
import com.venkat.newsfeed.db.DbHelper
import com.venkat.newsfeed.db.NewsFact

class MainRepository(private val apiHelper: ApiHelper,private val dbHelper: DbHelper) {

    suspend fun getFacts() = apiHelper.getFacts()
    suspend fun getFactsFromDb(category : String? ) = dbHelper.getFacts(category)
    suspend fun getAllFacts() = dbHelper.getAllFacts()
    suspend fun insertFacts(facts : List<NewsFact>) {
        dbHelper.deleteAll()
        dbHelper.insertFacts(facts)
    }
}
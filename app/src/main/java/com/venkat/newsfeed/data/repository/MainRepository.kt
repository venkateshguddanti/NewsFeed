package com.venkat.newsfeed.data.repository

import com.venkat.newsfeed.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getFacts() = apiHelper.getFacts()
}
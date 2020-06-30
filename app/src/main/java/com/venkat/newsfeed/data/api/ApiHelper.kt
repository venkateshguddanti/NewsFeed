package com.venkat.newsfeed.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getFacts() = apiService.getFacts()
}
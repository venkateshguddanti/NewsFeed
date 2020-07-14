package com.venkat.newsfeed.data.api

import com.venkat.newsfeed.data.model.Facts
import retrofit2.http.GET

interface ApiService {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    suspend fun getFacts(): Facts
}
package com.venkat.newsfeed.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FactDao{
    @Query("SELECT * FROM news_facts WHERE _category LIKE :category")
    fun getFacts(category : String?): List<NewsFact>

    @Insert
    fun insertAll(facts: List<NewsFact>)

    @Query("SELECT * FROM news_facts")
    fun getAll(): List<NewsFact>

    @Query("DELETE FROM news_facts")
    fun deleteFacts()

}
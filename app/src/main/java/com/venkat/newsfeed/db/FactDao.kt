package com.venkat.newsfeed.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.venkat.newsfeed.data.model.Rows

@Dao
interface FactDao {
    @Query("SELECT * FROM fact_rows WHERE category LIKE :category")
    fun getFacts(category: String?): List<Rows>
    @Insert
    fun insertAll(facts: List<Rows>)
    @Query("SELECT * FROM fact_rows")
    fun getAll(): List<Rows>
    @Query("DELETE FROM fact_rows")
    fun deleteFacts()
}
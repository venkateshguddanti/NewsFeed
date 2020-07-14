package com.venkat.newsfeed.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.venkat.newsfeed.data.model.Rows

@Database(entities = [Rows::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun factDao(): FactDao
}
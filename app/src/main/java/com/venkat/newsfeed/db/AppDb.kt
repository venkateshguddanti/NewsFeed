package com.venkat.newsfeed.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NewsFact::class),version = 1)
abstract class AppDb:RoomDatabase(){
    abstract fun factDao() :FactDao
}
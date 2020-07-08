package com.venkat.newsfeed.db

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: AppDb? = null

    fun getInstance(context: Context): AppDb {
        if (INSTANCE == null) {
            synchronized(AppDb::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context,
            AppDb::class.java,
            "fact_rows.db"
        ).build()

}
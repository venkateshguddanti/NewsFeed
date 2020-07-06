package com.venkat.newsfeed.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsFact(
    @PrimaryKey var category: String,
    @ColumnInfo(name = "_title") var title: String?,
    @ColumnInfo(name = "_des") var description: String?,
    @ColumnInfo(name = "_url") var imageUrl: String?
)
package com.venkat.newsfeed.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_facts")
class NewsFact(
    @ColumnInfo(name = "_category") val category: String?,
    @ColumnInfo(name = "_title") val title: String?,
    @ColumnInfo(name = "_des") val description: String?,
    @ColumnInfo(name = "_url") val imageUrl: String?
)
{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
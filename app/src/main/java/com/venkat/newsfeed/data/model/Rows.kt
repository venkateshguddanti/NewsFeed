package com.venkat.newsfeed.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fact_rows")
data class Rows(
    @ColumnInfo(name = "category") val category: String?,

    @ColumnInfo(name = "title")val title:String?,

    @ColumnInfo(name = "description")val description :String?,

    @ColumnInfo(name = "imageHref")val imageHref:String?)
{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}

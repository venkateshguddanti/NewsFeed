package com.venkat.newsfeed.utils

import com.venkat.newsfeed.data.model.Facts
import com.venkat.newsfeed.data.model.Rows
import com.venkat.newsfeed.db.NewsFact

object Util {

    fun getFactsFromDb(facts : List<NewsFact>) : Facts
    {
        val rows = arrayListOf<Rows>()
        var title : String?=null;
        for(row in facts)
        {
            title = row.category
            val fact = Rows(row.title,row.description,row.imageUrl)
            rows.add(fact)
        }
        val facts = Facts(title,rows)
        return facts
    }
}
package com.venkat.newsfeed.db

import com.venkat.newsfeed.data.model.Rows

class DbHelper(private val factDao: FactDao) {
    fun getAllFacts() = factDao.getAll()
    fun insertFacts(facts: List<Rows>) = factDao.insertAll(facts)
    fun deleteAll() = factDao.deleteFacts()
}
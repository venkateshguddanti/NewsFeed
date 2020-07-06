package com.venkat.newsfeed.db

class DbHelper(private val factDao: FactDao)
{
    suspend fun getFacts(category : String?) = factDao.getFacts(category)
    suspend fun getAllFacts() = factDao.getAll()
    suspend fun insertFacts(facts : List<NewsFact>) = factDao.insertAll(facts)

}
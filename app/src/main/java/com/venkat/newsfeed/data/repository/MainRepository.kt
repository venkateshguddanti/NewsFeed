package com.venkat.newsfeed.data.repository
import androidx.lifecycle.MutableLiveData
import com.venkat.newsfeed.data.api.ApiHelper
import com.venkat.newsfeed.data.model.Facts
import com.venkat.newsfeed.data.model.Resource
import com.venkat.newsfeed.data.model.Rows
import com.venkat.newsfeed.db.DbHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainRepository(private val apiHelper: ApiHelper,private val dbHelper: DbHelper) {

      suspend fun getFacts(factsLiveData: MutableLiveData<Resource<Facts>>)
      {
          factsLiveData.postValue(Resource.loading(data = null))
          try {
              val facts = apiHelper.getFacts()
              updateDb(facts,factsLiveData)
              factsLiveData.postValue(Resource.success(data = facts))

          }catch (exception : Exception)
          {
              factsLiveData.postValue(
                  Resource.error(
                      data = null,
                      message = exception.message ?: "Network ERROR!!"
                  )
              )
          }
      }
    private suspend fun updateDb(
        facts: Facts,
        factsLiveData: MutableLiveData<Resource<Facts>>
    ) {
        val factTitle = facts.title
        var factsFromApi =apiHelper.getFacts().rows as List<Rows>
        val factsToInsertInDb = mutableListOf<Rows>()
        factsFromApi = factsFromApi.filter { fact : Rows ->fact.title != null }
        for(fact in factsFromApi)
        {
            val fact = Rows(factTitle,
                fact.title,
                fact.description,
                fact.imageHref)
            factsToInsertInDb.add(fact)
        }
        GlobalScope.launch {
            try {
                dbHelper.deleteAll()
                dbHelper.insertFacts(factsToInsertInDb)
            } catch (e: Exception) {
                factsLiveData.postValue(Resource.error(data = null,message = e.message ?: "Database ERROR!!"))
            }
        }

    }

}
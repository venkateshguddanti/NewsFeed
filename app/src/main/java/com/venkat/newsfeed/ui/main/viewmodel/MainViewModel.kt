package com.venkat.newsfeed.ui.main.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.venkat.newsfeed.data.model.Resource
import com.venkat.newsfeed.data.model.Rows
import com.venkat.newsfeed.data.repository.MainRepository
import com.venkat.newsfeed.db.NewsFact
import com.venkat.newsfeed.utils.Util
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val repository: MainRepository): ViewModel() {


    val factsFromDb =  liveData(Dispatchers.IO) {
        val factsFromDB = repository.getAllFacts()
        if (!factsFromDB.isEmpty()) {
            emit(Resource.success(data = Util.getFactsFromDb(factsFromDB)))
        }
    }
    fun getFacts()  = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val facts = repository.getFacts()
            val factTitle = facts.title

            try {
                var factsFromApi =repository.getFacts().rows as List<Rows>
                val factsToInsertInDb = mutableListOf<NewsFact>()
                factsFromApi = factsFromApi.filter { fact : Rows->fact.title != null }
                for(fact in factsFromApi)
                {
                    val fact = NewsFact(factTitle,
                        fact?.title,
                        fact?.description,
                        fact?.imageHref)
                    factsToInsertInDb.add(fact)
                }
                repository.insertFacts(factsToInsertInDb)
            } catch (e: Exception) {
                emit(Resource.error(data = null,message = e.message ?: "Database ERROR!!"))
            }
            emit(Resource.success(data = facts))

        }catch (exception : Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Network ERROR!!"))

        }
    }

}
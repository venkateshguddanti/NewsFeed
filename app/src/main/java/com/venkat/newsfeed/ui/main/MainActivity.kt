package com.venkat.newsfeed.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.venkat.newsfeed.R
import com.venkat.newsfeed.data.api.ApiHelper
import com.venkat.newsfeed.data.api.RetrofitBuilder
import com.venkat.newsfeed.data.model.Rows
import com.venkat.newsfeed.ui.main.adapter.FactsAdapter
import com.venkat.newsfeed.ui.main.viewmodel.MainViewModel
import com.venkat.newsfeed.ui.main.viewmodel.ViewModelFactory
import com.venkat.newsfeed.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter: FactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        setupUi()
        setUpObservers()
    }

    private fun setUpObservers() {

        viewModel.getFacts().observe(this, Observer {
            it?.let {resources->
                when(resources.status)
                {
                    Status.SUCCESS->{
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resources.data?.let { facts->retrieveList(facts.rows)
                        supportActionBar?.title=facts.title}
                    }
                    Status.ERROR->{
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING->{
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(facts: List<Rows>){
        adapter.apply {
            addRows(facts.filter { fact:Rows->fact.title != null })
            notifyDataSetChanged()
        }

    }

    private fun setupUi() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FactsAdapter(arrayListOf())
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation))
        recyclerView.adapter = adapter
    }

    private fun setUpViewModel() {

        viewModel = ViewModelProvider(this,ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(MainViewModel::class.java)
    }
}

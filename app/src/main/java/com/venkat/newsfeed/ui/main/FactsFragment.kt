package com.venkat.newsfeed.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_facts.recyclerView
import kotlinx.android.synthetic.main.fragment_facts.refresh
import kotlinx.android.synthetic.main.fragment_facts.progressBar


class FactsFragment : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter: FactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    override fun onStart() {
        super.onStart()
        setUpViewModel()
        setUpObservers()
    }

    private fun setUpObservers() {

        viewModel.facts.observe(viewLifecycleOwner, Observer {
            it?.let {resources->
                when(resources.status)
                {
                    Status.SUCCESS->{
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resources.data?.let { facts->retrieveList(facts.rows)
                            (activity as MainActivity).supportActionBar?.title=facts.title}
                    }
                    Status.ERROR->{
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
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
            addRows(facts.filter { fact: Rows ->fact.title != null })
            notifyDataSetChanged()
        }
        refresh.isRefreshing=false
    }

    private fun setupUi() {
        refresh.setOnRefreshListener {
            progressBar.visibility = View.GONE
            setUpObservers()
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = FactsAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        recyclerView.adapter = adapter
    }

    private fun setUpViewModel() {

        viewModel = ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

}
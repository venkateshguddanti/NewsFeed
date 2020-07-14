package com.venkat.newsfeed.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.venkat.newsfeed.R
import com.venkat.newsfeed.data.api.ApiHelper
import com.venkat.newsfeed.data.api.RetrofitBuilder
import com.venkat.newsfeed.data.model.Facts
import com.venkat.newsfeed.data.model.Rows
import com.venkat.newsfeed.db.DatabaseBuilder
import com.venkat.newsfeed.db.DbHelper
import com.venkat.newsfeed.ui.main.adapter.FactsAdapter
import com.venkat.newsfeed.ui.main.viewmodel.MainViewModel
import com.venkat.newsfeed.ui.main.viewmodel.ViewModelFactory
import com.venkat.newsfeed.utils.Status
import kotlinx.android.synthetic.main.fragment_facts.factRowsList
import kotlinx.android.synthetic.main.fragment_facts.refresh
import kotlinx.android.synthetic.main.fragment_facts.progressBar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FactsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FactsAdapter
    private var networkCheck: Boolean = false

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
        updateFromDb()
        setUpViewModel()
        setUpObservers()
    }

    private fun updateFromDb() {
        val dbHelper =
            DbHelper(DatabaseBuilder.getInstance(activity!!.applicationContext).factDao())
        GlobalScope.launch {
            val rows = dbHelper.getAllFacts()
            if (rows.isNotEmpty()) {
                val facts = Facts(rows[0].category, dbHelper.getAllFacts() as ArrayList<Rows>)
                activity?.runOnUiThread {
                    (activity as MainActivity).supportActionBar?.title = facts.title
                }
                retrieveList(facts.rows)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setUpNetworkListener()
    }

    private fun setUpNetworkListener() {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            it.registerDefaultNetworkCallback(@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    //take action when network connection is gained
                    activity?.runOnUiThread {
                        if (networkCheck) viewModel.getFacts()
                    }
                }

                override fun onLost(network: Network) {
                    //take action when network connection is lost
                    networkCheck = true
                }
            })
        }
    }

    private fun setUpObservers() {
        viewModel.facts.observe(viewLifecycleOwner, Observer {
            it?.let { resources ->
                when (resources.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resources.data?.let { facts ->
                            if (resources.data.rows.size == 0) {
                                Toast.makeText(activity, R.string.no_data_found, Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                retrieveList(facts.rows)
                                (activity as MainActivity).supportActionBar?.title = facts.title
                            }
                        }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        refresh.isRefreshing = false
                        Toast.makeText(
                            activity,
                            getString(R.string.no_network_found),
                            Toast.LENGTH_LONG
                        ).show()
                        networkCheck = true
                    }
                    Status.LOADING -> {
                        if (!refresh.isRefreshing) progressBar.visibility = View.VISIBLE else Unit
                    }
                }
            }
        })
    }

    private fun retrieveList(facts: List<Rows>) {
        adapter.apply {
            addRows(facts.filter { fact: Rows -> fact.title != null })
            notifyDataSetChanged()
        }
        refresh.isRefreshing = false
    }

    private fun setupUi() {
        refresh.setOnRefreshListener {
            progressBar.visibility = View.GONE
            viewModel.getFacts()
        }
        factRowsList.layoutManager = LinearLayoutManager(activity)
        adapter = FactsAdapter(arrayListOf())
        factRowsList.addItemDecoration(
            DividerItemDecoration(
                factRowsList.context,
                (factRowsList.layoutManager as LinearLayoutManager).orientation
            )
        )
        factRowsList.adapter = adapter
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelper(RetrofitBuilder.apiService),
                DbHelper(DatabaseBuilder.getInstance(activity!!.applicationContext).factDao())
            )
        ).get(MainViewModel::class.java)
    }
}
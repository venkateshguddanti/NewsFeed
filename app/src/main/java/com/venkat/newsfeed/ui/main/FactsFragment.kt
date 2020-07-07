package com.venkat.newsfeed.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.os.Handler
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


class FactsFragment : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter: FactsAdapter
    private var networkCheck : Boolean = false

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
        setUpViewModel()
        if(savedInstanceState == null)
        {
            setUpObservers()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(FactsFragment::class.java.simpleName,FactsFragment::class.java.simpleName)
    }
    override fun onStart() {
        super.onStart()
        updateFromDb()
        setUpNetworkListener()
    }

    private fun setUpNetworkListener() {

        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager?.let {
            it.registerDefaultNetworkCallback(@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    //take action when network connection is gained
                    activity?.runOnUiThread {
                        Handler().postDelayed({
                            if(networkCheck) setUpObservers()
                            networkCheck = true
                        },1000)
                       }

                }
                override fun onLost(network: Network?) {
                    //take action when network connection is lost
                }
            })
        }
    }
    private fun updateFromDb() {
        viewModel.factsFromDb.observe(viewLifecycleOwner, Observer {
            it?.let {resource ->
                when(resource.status)
                {
                    Status.SUCCESS->{
                        factRowsList.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        retrieveList(resource.data!!.rows)
                    }
                }

            }
        })
    }
    private fun setUpObservers() {

        viewModel.getFacts().observe(viewLifecycleOwner, Observer {
            it?.let {resources->
                when(resources.status)
                {
                    Status.SUCCESS->{
                        factRowsList.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE

                        resources.data?.let {
                                facts->
                            if(resources.data.rows.size == 0)
                            {
                                Toast.makeText(activity, R.string.no_data_found, Toast.LENGTH_LONG).show()
                            }else {
                                retrieveList(facts.rows)
                                (activity as MainActivity).supportActionBar?.title = facts.title
                            }
                        }
                    }
                    Status.ERROR->{
                        factRowsList.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        refresh.isRefreshing =false

                    }
                    Status.LOADING->{
                        if(!refresh.isRefreshing)progressBar.visibility = View.VISIBLE
                        factRowsList.visibility = View.GONE
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
        factRowsList.layoutManager = LinearLayoutManager(activity)
        adapter = FactsAdapter(arrayListOf())
        factRowsList.addItemDecoration(
            DividerItemDecoration(factRowsList.context,
            (factRowsList.layoutManager as LinearLayoutManager).orientation)
        )
        factRowsList.adapter = adapter
    }

    private fun setUpViewModel() {


        viewModel = ViewModelProvider(this,
            ViewModelFactory(
                ApiHelper(RetrofitBuilder.apiService),
            DbHelper(DatabaseBuilder.getInstance(activity!!.applicationContext).factDao())
            )
        ).get(MainViewModel::class.java)
    }
}
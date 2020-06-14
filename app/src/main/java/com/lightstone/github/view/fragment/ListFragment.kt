package com.lightstone.github.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.lightstone.github.R
import com.lightstone.github.model.network.GithubApiService
import com.lightstone.github.model.network.datasource.UserDataSource
import com.lightstone.github.model.network.datasource.UserDataSourceImpl
import com.lightstone.github.model.network.interceptor.ConnectivityInterceptor
import com.lightstone.github.model.network.interceptor.ConnectivityInterceptorImpl
import com.lightstone.github.model.response.UserItem
import com.lightstone.github.view.adapter.UserListAdapter
import com.lightstone.github.viewmodel.viewmodelfactory.ListViewModelFactory
import com.lightstone.github.viewmodel.viewmodels.ListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ListFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory : ListViewModelFactory by instance()

    private lateinit var viewModel : ListViewModel

    private val userListAdapter = UserListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.visibility = View.VISIBLE

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListViewModel::class.java)

        usersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }

        refreshLayout.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            bindUi()
            refreshLayout.isRefreshing = false
        }

        bindUi()
    }

    private fun bindUi() = launch {
        val userList = viewModel.usersList.await()
        userList.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            userListAdapter.updateUsers(it)
            progressBar.visibility = View.GONE
            usersList.visibility = View.VISIBLE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if(it) {
                progressBar.visibility = View.GONE
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
            }
        })
    }
}

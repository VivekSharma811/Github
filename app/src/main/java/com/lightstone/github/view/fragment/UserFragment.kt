package com.lightstone.github.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.lightstone.github.R
import com.lightstone.github.databinding.FragmentUserBinding
import com.lightstone.github.model.db.UserDatabase
import com.lightstone.github.model.db.dao.UserDao
import com.lightstone.github.model.network.GithubApiService
import com.lightstone.github.model.network.datasource.GithubRepositoryDataSource
import com.lightstone.github.model.network.datasource.GithubRepositoryDataSourceImpl
import com.lightstone.github.model.network.interceptor.ConnectivityInterceptor
import com.lightstone.github.model.network.interceptor.ConnectivityInterceptorImpl
import com.lightstone.github.model.response.GithubRepository
import com.lightstone.github.util.getProgressDrawable
import com.lightstone.github.util.loadImage
import com.lightstone.github.view.adapter.RepoListAdapter
import com.lightstone.github.viewmodel.viewmodelfactory.UserViewModelFactory
import com.lightstone.github.viewmodel.viewmodels.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.refreshLayout
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class UserFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory : UserViewModelFactory by instance()
    private val repoListAdapter = RepoListAdapter(arrayListOf())

    private lateinit var viewModel : UserViewModel

    private var userUuid = 0

    private lateinit var dataBinding : FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(UserViewModel::class.java)

        arguments?.let {
            userUuid = UserFragmentArgs.fromBundle(it).userUuid
        }

        repoList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoListAdapter
        }

        refreshLayout.setOnRefreshListener {
            bindUi()
            refreshLayout.isRefreshing = false
        }

        bindUi()
    }

    private fun bindUi() = launch {
        viewModel.getUser(userUuid)

        viewModel.avatar.observe(viewLifecycleOwner, Observer {avatar ->
            context?.let { imageView.loadImage(avatar, getProgressDrawable(it)) }
        })

        viewModel.username.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        viewModel.repoList.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            repoListAdapter.updateUsers(it)
            //temptext.text = it.toString()
        })
    }

//    private fun bindUi() = launch {
//        dataBinding.user?.let {
//            context?.let {
//                githubApiService = GithubApiService.invoke(ConnectivityInterceptorImpl(it))
//            }
//
//            val datasource = GithubRepositoryDataSourceImpl(githubApiService)
//
//            datasource.repoList.observe(viewLifecycleOwner, Observer {
//                temptext.text = it.toString()
//            })
//
//            datasource.fetchRepository()
//
//        }
//    }
//
//    private fun showImage() = launch {
//        val user = UserDatabase(requireContext()!!.applicationContext).userDao().getUser(userUuid)
//        user.observe(viewLifecycleOwner, Observer {
//            dataBinding.user = it
//        })
//        bindUi()
//    }

}

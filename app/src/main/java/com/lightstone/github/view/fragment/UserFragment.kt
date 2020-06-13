package com.lightstone.github.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.lightstone.github.R
import com.lightstone.github.databinding.FragmentUserBinding
import com.lightstone.github.model.db.UserDatabase
import com.lightstone.github.model.db.dao.UserDao
import com.lightstone.github.model.network.GithubApiService
import com.lightstone.github.model.network.interceptor.ConnectivityInterceptor
import com.lightstone.github.model.network.interceptor.ConnectivityInterceptorImpl
import com.lightstone.github.model.response.GithubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class UserFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var githubApiService: GithubApiService

    private var userUuid = 0

    private lateinit var dataBinding : FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        return dataBinding.root
        //return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            userUuid = UserFragmentArgs.fromBundle(it).userUuid
        }
        showImage()
    }

    private fun bindUi() = launch {
        dataBinding.user?.let {
            context?.let {
                githubApiService = GithubApiService.invoke(ConnectivityInterceptorImpl(it))
            }
            CompositeDisposable().add(
                githubApiService.getRepository(it.login)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<GithubRepository>>() {
                        override fun onSuccess(t: List<GithubRepository>) {
                            temptext.text = t.toString()
                        }

                        override fun onError(e: Throwable) {
                            temptext.text = e.localizedMessage
                        }

                    })
            )
        }
    }

    private fun showImage() = launch {
        val user = UserDatabase(requireContext()!!.applicationContext).userDao().getUser(userUuid)
//        val user = userDao.getUser(userUuid)
        user.observe(viewLifecycleOwner, Observer {
            dataBinding.user = it
        })
        bindUi()
    }

}

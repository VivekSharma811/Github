package com.lightstone.github.model.network.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lightstone.github.model.network.GithubApiService
import com.lightstone.github.model.response.UserItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*

class UserDataSourceImpl(
    private val githubApiService: GithubApiService
) : UserDataSource {

    private val _userList = MutableLiveData<List<UserItem>>()

    override val userList: LiveData<List<UserItem>>
        get() = _userList

    override suspend fun fetchUser() {
        CompositeDisposable().add(
            githubApiService.getUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<UserItem>>() {
                    override fun onSuccess(t: List<UserItem>) {
                        t?.let {
                            _userList.postValue(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Error", e.toString())
                    }

                })
        )
    }
}
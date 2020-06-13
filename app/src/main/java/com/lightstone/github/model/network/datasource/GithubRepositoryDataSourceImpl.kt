package com.lightstone.github.model.network.datasource

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lightstone.github.model.network.GithubApiService
import com.lightstone.github.model.response.GithubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class GithubRepositoryDataSourceImpl(
    private val githubApiService: GithubApiService
) : GithubRepositoryDataSource {

    private val _repoList = MutableLiveData<List<GithubRepository>>()

    override val repoList: LiveData<List<GithubRepository>>
        get() = _repoList

    override suspend fun fetchRepository(username : String) {
        CompositeDisposable().add(
            githubApiService.getRepository(username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<GithubRepository>>() {
                    override fun onSuccess(t: List<GithubRepository>) {
                        t?.let {
                            _repoList.postValue(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Error", e.localizedMessage)
                    }

                })
        )
    }
}
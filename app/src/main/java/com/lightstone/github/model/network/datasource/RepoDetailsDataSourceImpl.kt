package com.lightstone.github.model.network.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lightstone.github.model.network.GithubApiService
import com.lightstone.github.model.response.RepoDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RepoDetailsDataSourceImpl(
    private val githubApiService: GithubApiService
) : RepoDetailsDataSource {

    private val _repoDetails = MutableLiveData<RepoDetails>()

    override val repoDetails: LiveData<RepoDetails>
        get() = _repoDetails

    private val _error = MutableLiveData<Boolean>()

    override val error: LiveData<Boolean>
        get() = _error

    override suspend fun fetchRepoDetails(username: String, reponame: String) {
        CompositeDisposable().add(
            githubApiService.getRepoDetails(username, reponame)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RepoDetails>() {
                    override fun onSuccess(t: RepoDetails) {
                        _repoDetails.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Error", e.toString())
                        _error.postValue(true)
                    }
                })
        )
    }
}
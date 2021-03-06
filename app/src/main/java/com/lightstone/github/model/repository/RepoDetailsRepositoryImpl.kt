package com.lightstone.github.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lightstone.github.model.network.datasource.RepoDetailsDataSource
import com.lightstone.github.model.response.RepoDetails

class RepoDetailsRepositoryImpl(
    private val repoDetailsDataSource: RepoDetailsDataSource
) : RepoDetailsRepository {

    private val _repo = MutableLiveData<RepoDetails>()

    override val repo: LiveData<RepoDetails>
        get() = _repo

    private val _error = MutableLiveData<Boolean>()

    override val error: LiveData<Boolean>
        get() = _error

    init {
        repoDetailsDataSource.repoDetails.observeForever {
            _repo.postValue(it)
        }

        repoDetailsDataSource.error.observeForever {
            _error.postValue(it)
        }
    }

    override suspend fun getRepoDetails(username: String, reponame: String) {
        initFetchFromApi(username, reponame)
    }

    private suspend fun initFetchFromApi(username: String, reponame: String) {
        if(true)
            repoDetailsDataSource.fetchRepoDetails(username, reponame)
    }
}
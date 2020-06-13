package com.lightstone.github.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lightstone.github.model.db.dao.RepoDao
import com.lightstone.github.model.network.datasource.GithubRepositoryDataSource
import com.lightstone.github.model.response.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepoRepositoryImpl(
    private val repoDao: RepoDao,
    private val repositoryDataSource: GithubRepositoryDataSource
) : RepoRepository {

    private val _repo = MutableLiveData<List<GithubRepository>>()

    override val repo : LiveData<List<GithubRepository>>
        get() = _repo

    init {
        repositoryDataSource.repoList.observeForever {
            _repo.postValue(it)
            //persistFetchedData(it)
        }
    }

    override suspend fun getRepoList(username : String): LiveData<List<GithubRepository>> {
        return withContext(Dispatchers.IO) {
            initFetchFromApi(username)
            return@withContext repoDao.getAllRepos()
        }
    }

    private fun persistFetchedData(repoList : List<GithubRepository>) {
        GlobalScope.launch(Dispatchers.IO) {
            repoDao.insert(*repoList.toTypedArray())
        }
    }

    private suspend fun initFetchFromApi(username: String) {
        if(true) {
            repositoryDataSource.fetchRepository(username)
        }
    }
}
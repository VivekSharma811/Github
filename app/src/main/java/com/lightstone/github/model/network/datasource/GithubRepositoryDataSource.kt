package com.lightstone.github.model.network.datasource

import androidx.lifecycle.LiveData
import com.lightstone.github.model.response.GithubRepository

interface GithubRepositoryDataSource {

    val repoList : LiveData<List<GithubRepository>>

    val error : LiveData<Boolean>

    suspend fun fetchRepository(username : String)

}
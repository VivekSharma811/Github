package com.lightstone.github.model.network.datasource

import androidx.lifecycle.LiveData
import com.lightstone.github.model.response.RepoDetails

interface RepoDetailsDataSource {

    val repoDetails : LiveData<RepoDetails>

    suspend fun fetchRepoDetails(username : String, reponame : String)

}
package com.lightstone.github.model.repository

import androidx.lifecycle.LiveData
import com.lightstone.github.model.response.RepoDetails

interface RepoDetailsRepository {

    val repo : LiveData<RepoDetails>

    suspend fun getRepoDetails(username : String, reponame : String)

}
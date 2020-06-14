package com.lightstone.github.model.repository

import androidx.lifecycle.LiveData
import com.lightstone.github.model.response.GithubRepository

interface RepoRepository {

    val repo : LiveData<List<GithubRepository>>

    val error : LiveData<Boolean>

    suspend fun getRepoList(username : String)

}
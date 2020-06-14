package com.lightstone.github.viewmodel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lightstone.github.model.repository.RepoDetailsRepository
import com.lightstone.github.model.response.RepoDetails

class DetailsViewModel(
    private val repoDetailsRepository: RepoDetailsRepository
) : ViewModel() {

    private val _error = MutableLiveData<Boolean>()

    val error : LiveData<Boolean>
        get() = _error

    private val _repoDetails = MutableLiveData<RepoDetails>()

    val repoDetails : LiveData<RepoDetails>
        get() = _repoDetails

    init {
        repoDetailsRepository.repo.observeForever {
            _repoDetails.postValue(it)
        }
        repoDetailsRepository.error.observeForever {
            _error.postValue(it)
        }
    }

    suspend fun getRepo(username : String, reponame : String) {
        repoDetailsRepository.getRepoDetails(username, reponame)
    }

}
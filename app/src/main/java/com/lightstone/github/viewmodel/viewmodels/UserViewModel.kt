package com.lightstone.github.viewmodel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lightstone.github.model.repository.RepoRepository
import com.lightstone.github.model.repository.UserRepository
import com.lightstone.github.model.response.GithubRepository
import kotlinx.coroutines.*

class UserViewModel(
    private val repoRepository: RepoRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _repoList = MutableLiveData<List<GithubRepository>>()
    val repoList : LiveData<List<GithubRepository>>
        get() = _repoList

    private val _username = MutableLiveData<String>()

    val username : LiveData<String>
        get() = _username

    private val _avatar = MutableLiveData<String>()

    val avatar : LiveData<String>
        get() = _avatar

    suspend fun getUser(userId : Int) {
        userRepository.getUser(userId).observeForever {
            _avatar.postValue(it.avatar_url)
            _username.postValue(it.login)
        }
    }

    init {
        username.observeForever {
            getRepo(it)
        }
        repoRepository.repo.observeForever {
            _repoList.postValue(it)
        }
    }

    fun getRepo(username : String) {
        GlobalScope.launch(Dispatchers.Main) {
            repoRepository.getRepoList(username)}
    }
}
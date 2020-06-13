package com.lightstone.github.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lightstone.github.model.repository.RepoRepository
import com.lightstone.github.model.repository.UserRepository
import com.lightstone.github.viewmodel.viewmodels.UserViewModel

class UserViewModelFactory(
    private val repoRepository: RepoRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(repoRepository, userRepository) as T
    }

}
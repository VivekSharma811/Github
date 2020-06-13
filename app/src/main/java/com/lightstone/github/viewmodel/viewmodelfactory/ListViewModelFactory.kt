package com.lightstone.github.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lightstone.github.model.repository.UserRepository
import com.lightstone.github.viewmodel.viewmodels.ListViewModel

class ListViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(userRepository) as T
    }

}
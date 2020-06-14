package com.lightstone.github.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.lightstone.github.model.repository.RepoDetailsRepository
import com.lightstone.github.viewmodel.viewmodels.DetailsViewModel

class DetailsViewModelFactory(
    private val repoDetailsRepository: RepoDetailsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repoDetailsRepository) as T
    }

}
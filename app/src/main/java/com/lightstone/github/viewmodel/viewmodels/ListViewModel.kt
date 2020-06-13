package com.lightstone.github.viewmodel.viewmodels

import androidx.lifecycle.ViewModel
import com.lightstone.github.model.repository.UserRepository
import com.lightstone.github.util.lazyDeferred

class ListViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val usersList by lazyDeferred { userRepository.getUserList() }

}
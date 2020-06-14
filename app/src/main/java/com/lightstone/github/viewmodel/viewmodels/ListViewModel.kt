package com.lightstone.github.viewmodel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lightstone.github.model.repository.UserRepository
import com.lightstone.github.util.lazyDeferred

class ListViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _error = MutableLiveData<Boolean>()

    val error : LiveData<Boolean>
        get() = _error

    init {
        userRepository.error.observeForever {
            _error.postValue(it)
        }
    }

    val usersList by lazyDeferred { userRepository.getUserList() }

}
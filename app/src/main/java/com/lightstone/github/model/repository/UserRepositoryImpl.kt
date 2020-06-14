package com.lightstone.github.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lightstone.github.model.db.dao.UserDao
import com.lightstone.github.model.network.datasource.UserDataSource
import com.lightstone.github.model.response.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userDataSource: UserDataSource
) : UserRepository {

    init {
        userDataSource.userList.observeForever {
            persistFetchedData(it)
        }
        userDataSource.error.observeForever {
            _error.postValue(it)
        }
    }

    private val _error = MutableLiveData<Boolean>()
    override val error: LiveData<Boolean>
        get() = _error

    override suspend fun getUserList(): LiveData<List<UserItem>> {
        return withContext(Dispatchers.IO) {
            initFetchFromApi()
            return@withContext userDao.getAllUsers()
        }
    }

    override suspend fun getUser(userId: Int): LiveData<UserItem> {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUser(userId)
        }
    }

    private fun persistFetchedData(usersList : List<UserItem>) {
        GlobalScope.launch(Dispatchers.IO) {
            userDao.insert(*usersList.toTypedArray())
        }
    }

    private suspend fun initFetchFromApi() {
        if(true) {
            userDataSource.fetchUser()
        }
    }
}
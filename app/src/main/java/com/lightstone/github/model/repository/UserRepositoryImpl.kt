package com.lightstone.github.model.repository

import androidx.lifecycle.LiveData
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
    }

    override suspend fun getUserList(): LiveData<List<UserItem>> {
        return withContext(Dispatchers.IO) {
            initFetchFromApi()
            return@withContext userDao.getAllUsers()
        }
    }

    private fun persistFetchedData(usersList : List<UserItem>) {
        GlobalScope.launch(Dispatchers.IO) {
            userDao.insert(usersList)
        }
    }

    private suspend fun initFetchFromApi() {
        if(true) {
            userDataSource.fetchUser()
        }
    }
}
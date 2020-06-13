package com.lightstone.github.model.network.datasource

import androidx.lifecycle.LiveData
import com.lightstone.github.model.response.UserItem

interface UserDataSource {

    val userList : LiveData<List<UserItem>>

    suspend fun fetchUser()

}
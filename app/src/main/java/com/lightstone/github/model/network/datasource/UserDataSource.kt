package com.lightstone.github.model.network.datasource

import androidx.lifecycle.LiveData
import com.lightstone.github.model.response.UserItem

interface UserDataSource {

    val userList : LiveData<List<UserItem>>

    val error : LiveData<Boolean>

    suspend fun fetchUser()

}
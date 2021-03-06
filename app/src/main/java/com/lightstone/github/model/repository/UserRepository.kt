package com.lightstone.github.model.repository

import androidx.lifecycle.LiveData
import com.lightstone.github.model.response.UserItem

interface UserRepository {

    val error : LiveData<Boolean>

    suspend fun getUserList() : LiveData<List<UserItem>>

    suspend fun getUser(userId : Int) : LiveData<UserItem>
}
package com.lightstone.github.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lightstone.github.model.response.UserItem

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userList: List<UserItem>)

    @Query("SELECT * FROM github_user")
    fun getAllUsers() : LiveData<List<UserItem>>

    @Query("SELECT * FROM github_user WHERE uuid= :userId")
    fun getUser(userId : Int) : LiveData<UserItem>

    @Query("DELETE FROM github_user")
    fun deleteAll()

}
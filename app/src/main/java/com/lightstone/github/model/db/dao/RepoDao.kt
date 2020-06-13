package com.lightstone.github.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lightstone.github.model.response.GithubRepository

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repo: GithubRepository)

    @Query("SELECT * FROM repository")
    fun getAllRepos() : LiveData<List<GithubRepository>>

    @Query("SELECT * FROM repository WHERE uuid= :repoId")
    fun getRepo(repoId : Int) : LiveData<GithubRepository>

    @Query("DELETE FROM repository")
    fun deleteAll()

}
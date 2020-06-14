package com.lightstone.github.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository")
data class GithubRepository(
    val name : String,
    val watchers : Int,
    val full_name : String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}
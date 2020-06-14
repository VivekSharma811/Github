package com.lightstone.github.model.response

data class RepoDetails(
    val name : String,
    val html_url : String,
    val created_at : String,
    val updated_at : String,
    val watchers : Int,
    val language : String,
    val forks : String,
    val open_issues : Int,
    val size : Int,
    val subscribers_count : Int
)
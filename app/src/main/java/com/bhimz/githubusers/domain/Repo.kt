package com.bhimz.githubusers.domain

data class Repo(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
)

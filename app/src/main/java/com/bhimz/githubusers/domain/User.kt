package com.bhimz.githubusers.domain

data class User(
    val id: Long,
    val username: String,
    val profileLink: String,
    val name: String?,
    val email: String?,
    val avatarUrl: String?,
)